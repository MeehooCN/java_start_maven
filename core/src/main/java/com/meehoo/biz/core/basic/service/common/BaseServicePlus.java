package com.meehoo.biz.core.basic.service.common;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StreamUtil;
import com.meehoo.biz.core.basic.annotation.SetBySystem;
import com.meehoo.biz.core.basic.domain.parent.AddressEntity;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.enumeration.CommonMethod;
import com.meehoo.biz.core.basic.enumeration.FieldType;
import com.meehoo.biz.core.basic.ro.bos.AddressEntityRO;
import com.meehoo.biz.core.basic.service.BaseService;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.util.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by CZ on 2018/1/10.
 */
@Component
public class BaseServicePlus extends BaseService implements IBaseService {
    @Autowired
    private ICommonService commonService;

    public BaseServicePlus() {

    }

    //更新前业务逻辑，子类可重写
    protected <RO> void beforeCreateOrUpdate(RO ro) throws Exception {
    }

    //更新后业务逻辑，子类可重写
    protected <RO> void afterCreateOrUpdate(Object domain, RO ro) throws Exception {
    }

    //通用新增修改方法
    @Override
    @Transactional
    public <D, RO> D createOrUpdate(Class<D> dClass, RO ro) throws Exception {
        Class roClass = ro.getClass();
        Object id = ReflectUtil.getIdProperty(roClass, ro);
        // 有id查询，无id new一个
        D domain = BaseUtil.objectNotNull(id) ? this.queryById(dClass, id) : dClass.newInstance();
        if (domain == null) {
            throw new RuntimeException("id为:" + id.toString() + "的" + dClass.getSimpleName() + "信息不存在");
        }
        // 前置操作
        beforeCreateOrUpdate(ro);
        // 保存操作
        createOrUpdate(dClass, roClass, domain, ro);
        // 后置操作
        afterCreateOrUpdate(domain, ro);
        return domain;
    }

    private void createOrUpdate(Class<?> dClass, Class<?> roClass, Object domain, Object ro) throws Exception {
        boolean isCreate = !BaseUtil.objectNotNull(ReflectUtil.getIdProperty(dClass, domain));

        // 根据注释给参数分类，有缓存
        Map<FieldType, List<Field>> fieldTypeMap = ReflectUtil.divideDomainFields(dClass);
        List<Field> numberFieldList = fieldTypeMap.get(FieldType.NUMBER); // SerialNumber 编号
        List<Field> operatorFieldList = fieldTypeMap.get(FieldType.OPERATOR); // Operator 操作员
        List<Field> sysFieldList = fieldTypeMap.get(FieldType.SETBYSYS); // SetBySystem 创建时间等后台初始化的普通字段
        List<Field> columnFieldList = fieldTypeMap.get(FieldType.COLUMN); // Column
        List<Field> foreignFieldList = fieldTypeMap.get(FieldType.FOREIGN); // ManyToOne OneToOne
        List<Field> collectionFieldList = fieldTypeMap.get(FieldType.COLLECTION); // OneToMany ManyToOne

        // 设置编号，从SerialNum表中类名取得
        setNumberField(numberFieldList, roClass, dClass, ro, domain);

        // 从baseRO 的 CurrentUserId 得到 User 并 setUser
        if (BaseUtil.collectionNotNull(operatorFieldList)) {
            setOperatorField(roClass, dClass, ro, domain);
        }

        // 根据字段是否注解 @SetBySystem，新建还是更新，为其赋值 new Date
        setSysFields(sysFieldList, domain, isCreate);
        //写入普通字段，用 @Column 注释，从RO中取得同名参数
        setColumnFields(columnFieldList, roClass, ro, domain);
        //写入外键字段，newInstance，并设置id
        setForeignFields(foreignFieldList, roClass, ro, domain);

        //对带了省市区字段的域模型进行赋值
        if ((domain instanceof AddressEntity) && (ro instanceof AddressEntityRO)) {
            commonService.setProvinceCityAreaAndAddress((AddressEntity) domain, (AddressEntityRO) ro);
        }

        this.saveOrUpdate(domain);
        // 写入集合字段，对旧集合增删改查
        setCollectionFields(collectionFieldList, roClass, dClass, ro, domain);
    }

    private void setNumberField(List<Field> numberFieldList, Class<?> roClass, Class<?> dClass, Object ro, Object domain) throws Exception {
        if (BaseUtil.collectionNotNull(numberFieldList)) {
            Field numberField = numberFieldList.get(0);
            Field roNumberField = ReflectUtil.mapDomainFieldToROField(FieldType.COLUMN, numberField, roClass);
            Object number = null;
            if (roNumberField != null) {
                number = ReflectUtil.getFieldValue(roNumberField, ro);
            }
            if (!BaseUtil.objectNotNull(number)) {
                number = commonService.getBizObjectSerialNumber(dClass.getSimpleName());
            }
            ReflectUtil.setField(numberField, domain, number);
        }
    }

    private void setOperatorField(Class<?> roClass, Class<?> dClass, Object ro, Object domain) throws Exception {
        Object currUserId = ReflectUtil.getCommonProperty(roClass, ro, CommonMethod.GET_CURRUSERID);
        if (BaseUtil.objectNotNull(currUserId)) {
            User operator = this.queryById(User.class, currUserId);
            ReflectUtil.setCommProperty(dClass, domain, operator, CommonMethod.SET_OPERATORANDDATE);
        }
    }

    private void setSysFields(List<Field> sysFieldList, Object domain, boolean isCreate) throws Exception {
        if (BaseUtil.collectionNotNull(sysFieldList)) {
            for (Field field : sysFieldList) {
                SetBySystem setBySystem = field.getAnnotation(SetBySystem.class);
                if (setBySystem.createOnly() && setBySystem.updateOnly()) {
                    throw new RuntimeException("@SetBySystem注解的createOnly与updateOnly属性不能同时为true");
                }
                boolean isNeedSet = isCreate ? !setBySystem.updateOnly() : !setBySystem.createOnly();
                if (isNeedSet) {
                    Class fieldType = field.getType();
                    if (fieldType == Date.class) {
                        ReflectUtil.setField(field, domain, new Date());
                    }
                }
            }
        }
    }

    //一般字段赋值
    private void setColumnFields(List<Field> fieldList, Class<?> roClass, Object ro, Object domain) throws Exception {
        if (BaseUtil.collectionNotNull(fieldList)) {
            for (Field field : fieldList) {
                Field roField = ReflectUtil.mapDomainFieldToROField(FieldType.COLUMN, field, roClass);
                if (roField != null) {
                    Object value = ReflectUtil.adaptROFieldValue(field, roField, ro);
                    if(value!=null){
                        try {
                            ReflectUtil.setField(field, domain, value);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    //外键字段赋值
    private void setForeignFields(List<Field> fieldList, Class<?> roClass, Object ro, Object domain) throws Exception {
        if (BaseUtil.collectionNotNull(fieldList)) {
            for (Field field : fieldList) {
                Field roField = ReflectUtil.mapDomainFieldToROField(FieldType.FOREIGN, field, roClass);
                if (roField != null) {
                    Class foreignFieldType = field.getType();//外键类型
                    Object roFieldValue = ReflectUtil.getFieldValue(roField, ro);
                    if (BaseUtil.objectNotNull(roFieldValue)) {
                        if (roField.getName().endsWith(ReflectUtil.SUFFIX_RO)) {
                            Class roFieldType = roField.getType();
                            Object IdOfROField = ReflectUtil.getIdProperty(roFieldType, roFieldValue);
                            Object foreignFieldValue = BaseUtil.objectNotNull(IdOfROField) ? this.queryById(roFieldType, IdOfROField) : foreignFieldType.newInstance();
                            if (foreignFieldValue == null) {
                                throw new RuntimeException("id为:" + IdOfROField.toString() + "的" + foreignFieldType.getSimpleName() + "信息不存在");
                            }
                            createOrUpdate(foreignFieldType, roFieldType, foreignFieldValue, roFieldValue);
                            ReflectUtil.setField(field, domain, foreignFieldValue);
                        } else {
                            Object foreignFieldInstance = foreignFieldType.newInstance();
                            ReflectUtil.setIdProperty(foreignFieldType, foreignFieldInstance, roFieldValue);
                            ReflectUtil.setField(field, domain, foreignFieldInstance);
                        }
                    }
                }
            }
        }
    }

    //针对明细对象外键字段赋值
    private void setForeignFields(List<Field> fieldList, Class<?> manyToOneClass, Class<?> entryROClass, Object manyToOne, Object entryRO, Object entry) throws Exception {
        if (BaseUtil.collectionNotNull(fieldList)) {
            for (Field field : fieldList) {
                Object foreignFieldInstance = null;
                Class foreignFieldType = field.getType();
                if (foreignFieldType == manyToOneClass) {
                    foreignFieldInstance = manyToOne;
                } else {
                    Field entryROField = ReflectUtil.mapDomainFieldToROField(FieldType.FOREIGN, field, entryROClass);
                    if (entryROField != null) {
                        Object foreignKeyValue = ReflectUtil.getFieldValue(entryROField, entryRO);
                        if (BaseUtil.objectNotNull(foreignKeyValue)) {
                            foreignFieldInstance = foreignFieldType.newInstance();
                            ReflectUtil.setIdProperty(foreignFieldType, foreignFieldInstance, foreignKeyValue);
                        }
                    }
                }
                if (foreignFieldInstance != null) {
                    ReflectUtil.setField(field, entry, foreignFieldInstance);
                }
            }
        }
    }

    //集合字段
    private void setCollectionFields(List<Field> fieldList, Class<?> roClass, Class<?> dClass, Object ro, Object domain) throws Exception {
        if (BaseUtil.collectionNotNull(fieldList)) {
            for (Field field : fieldList) {
                Field roField = ReflectUtil.mapDomainFieldToROField(FieldType.COLLECTION, field, roClass);
                if (roField != null) {

                    List entryROList = (List) ReflectUtil.getFieldValue(roField, ro);
                    List needSaveEntryList = new ArrayList();
                    List oldEntryList = (List) ReflectUtil.getFieldValue(field, domain);

                    if (BaseUtil.collectionNotNull(entryROList)) {

                        Class entryClass = ReflectUtil.getActualTypes(field.getGenericType())[0];
                        Class entryROClass = ReflectUtil.getActualTypes(roField.getGenericType())[0];

                        Map<FieldType, List<Field>> fieldTypeMap = ReflectUtil.divideDomainFields(entryClass);
                        List<Field> numberFieldList = fieldTypeMap.get(FieldType.NUMBER);
                        List<Field> operatorFieldList = fieldTypeMap.get(FieldType.OPERATOR);
                        List<Field> sysFieldList = fieldTypeMap.get(FieldType.SETBYSYS);
                        List<Field> columnFieldList = fieldTypeMap.get(FieldType.COLUMN);
                        List<Field> foreignFieldList = fieldTypeMap.get(FieldType.FOREIGN);
                        List<Field> collectionFieldList = fieldTypeMap.get(FieldType.COLLECTION);

                        Map<Object, Object> oldEntryMap = ReflectUtil.convertDomainListToMap(oldEntryList, entryClass);
                        for (Object entryRO : entryROList) {
                            boolean isCreate = true;
                            Object entryId = ReflectUtil.getIdProperty(entryROClass, entryRO);
                            Object entry;
                            if (BaseUtil.objectNotNull(entryId)) {
                                entry = oldEntryMap.get(entryId);
                                isCreate = false;
                            } else {
                                entry = entryClass.newInstance();
                            }
                            if (entry == null) {
                                throw new NullPointerException("Id为:" + entryId.toString() + "的" + entryClass.getName() + "信息不存在");
                            }
                            if (isCreate) {
                                setNumberField(numberFieldList, entryROClass, entryClass, entryRO, entry);
                            }
                            if (BaseUtil.collectionNotNull(operatorFieldList)) {
                                setOperatorField(entryROClass, entryClass, entryRO, entry);
                            }
                            setSysFields(sysFieldList, entry, isCreate);

                            setColumnFields(columnFieldList, entryROClass, entryRO, entry);

                            setForeignFields(foreignFieldList, dClass, entryROClass, domain, entryRO, entry);

                            if (BaseUtil.collectionNotNull(collectionFieldList)) {
                                this.saveOrUpdate(entry);
                                setCollectionFields(collectionFieldList, entryROClass, entryClass, entryRO, entry);
                            }
                            needSaveEntryList.add(entry);
                        }
                    }

                    //合并新旧明细，即包含增删改
                    this.mergeEntryList(oldEntryList, needSaveEntryList);
                    ReflectUtil.setField(field, domain, needSaveEntryList);
                }
            }
        }
    }

    public   <T> void mergeEntryList(List<T> oldEntryList, List<T> needSaveEntryList) throws Exception {

        if (BaseUtil.collectionNotNull(needSaveEntryList)) {
            batchSave((List) StreamUtil.filter(needSaveEntryList, entry -> !BaseUtil.objectNotNull(ReflectUtil.getIdProperty(entry))));
            batchUpdate(needSaveEntryList);
        }
        if (BaseUtil.collectionNotNull(oldEntryList)) {
            Class tClass = oldEntryList.get(0).getClass();
            Map<Object, T> needSaveEntryMap = ReflectUtil.convertDomainListToMap(needSaveEntryList, tClass);
            List<T> needDeleteEntryList = oldEntryList.parallelStream().filter(entry ->
                    !BaseUtil.objectNotNull(needSaveEntryMap.get(ReflectUtil.getIdProperty(tClass, entry)))
            ).collect(Collectors.toList());
            batchDelete(needDeleteEntryList);
        }

    }
}