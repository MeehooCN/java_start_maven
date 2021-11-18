package com.meehoo.biz.mobile.basic;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.core.basic.domain.BaseEntity;
import com.meehoo.biz.core.basic.domain.IdEntity;
import com.meehoo.biz.core.basic.param.*;
import com.meehoo.biz.core.basic.ro.bos.ChangeStatusRO;
import com.meehoo.biz.core.basic.service.IBaseService;
import com.meehoo.biz.core.basic.service.common.ICommonService;
import com.meehoo.biz.core.basic.vo.IdEntityVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/31.
 */
public abstract class BaseController<D extends IdEntity, V extends IdEntityVO> {

    @Autowired
    @Qualifier("baseServicePlus")
    protected IBaseService baseService;

    @Autowired
    protected ICommonService commonService;

    protected Class<D> clazzD;

    protected Class<V> clazzV;

    @SuppressWarnings("unchecked")
    public BaseController() {
        Class c = this.getClass();
        Type  t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();

            this.clazzD = (Class<D>) p[0];
            this.clazzV = (Class<V>) p[1];
        }
    }

    private Set<String> domainFields;

    private boolean containField(Field field){
        if (domainFields==null){
            synchronized (this){
                if (domainFields==null){
                    Field[] fields = clazzD.getDeclaredFields();
                    domainFields = new HashSet<>(fields.length+3);
                    for (Field f : fields) {
                        domainFields.add(f.getName());
                    }
                    if (clazzD.isAssignableFrom(BaseEntity.class)){
                        domainFields.add("code");
                        domainFields.add("name");
                        domainFields.add("isDelete");
                    }
                }
            }
        }
        return domainFields.contains(field.getName());
    }

    protected HttpResult<List<V>> mappingAndList(Object param) throws Exception{
        Class<?> paramClass = param.getClass();
        Field[] paramFields = paramClass.getDeclaredFields();
        SearchConditionBuilder builder = new SearchConditionBuilder();

        for (Field paramField : paramFields) {
            paramField.get(param);
        }
        return list(builder.toList());
    }

    protected HttpResult<PageResult<V>> page(List<SearchCondition> searchConditionList, PageCriteria pageCriteria ){
        if (BaseEntity.class.isAssignableFrom(clazzD)){
            searchConditionList.add(new SearchCondition("enable","=","1"));
        }
        return pageWithDisable(searchConditionList,pageCriteria);
    }

    protected HttpResult<PageResult<V>> pageWithDisable(List<SearchCondition> searchConditionList, PageCriteria pageCriteria){
        if (BaseEntity.class.isAssignableFrom(clazzD)){
            searchConditionList.add(new SearchCondition("isDelete","=","0"));
        }
        PageResult<V> pageResult = baseService.listPage(clazzD, clazzV, pageCriteria, searchConditionList);
        return HttpResult.success(pageResult);
    }

    protected HttpResult<List<V>> list(List<SearchCondition> searchConditionList) throws Exception {
        if (BaseEntity.class.isAssignableFrom(clazzD)){
            searchConditionList.add(new SearchCondition("enable","=","1"));
        }
        return listWithDisable(searchConditionList);
    }

    protected HttpResult<List<V>> listWithDisable(List<SearchCondition> searchConditionList) throws Exception {
        if (BaseEntity.class.isAssignableFrom(clazzD)){
            searchConditionList.add(new SearchCondition("isDelete","=","0"));
        }
        List<V>             resultList = baseService.listAll(clazzD, clazzV, searchConditionList);
        return HttpResult.success(resultList);
    }

    @ApiOperation("基础__根据id获取实体类")
    @GetMapping("getById")
    public HttpResult<V> getById(String id){
        D domain = this.baseService.queryById(clazzD, id);
        if (BaseUtil.objectNotNull(domain)) {
            try {
                Constructor<V> VOconstructor = clazzV.getConstructor(clazzD);
                V              voInstance    = VOconstructor.newInstance(domain);
                return HttpResult.success(voInstance);
            } catch (Exception e) {
//                e.printStackTrace();
                throw new RuntimeException(clazzV.getSimpleName()+"缺少对应域模型的构造方法");
            }

        } else {
            throw new RuntimeException("未查询到" + id + "的对象:" + clazzD.getSimpleName());
        }
    }

    protected  <T> HttpResult<T> getDetailById(String id, Class<T> dvClass){
        D domain = this.baseService.queryById(clazzD, id);
        if (BaseUtil.objectNotNull(domain)) {
            try {
                Constructor<T> VOconstructor = dvClass.getConstructor(clazzD);
                T voInstance = VOconstructor.newInstance(domain);
                return HttpResult.success(voInstance);
            } catch (Exception e) {
                throw new RuntimeException(clazzV.getSimpleName()+"缺少对应域模型的构造方法");
            }

        } else {
            throw new RuntimeException("未查询到" + id + "的对象:" + clazzD.getSimpleName());
        }
    }

    @ApiOperation("基础__更新status状态")
    @PostMapping("changeEnable")
    public HttpResult changeEnable(@RequestBody ChangeStatusRO ro) throws Exception {
        D domain = this.baseService.queryById(clazzD, ro.getId());
        if (BaseEntity.class.isAssignableFrom(clazzD)) {
            ((BaseEntity)domain).setEnable(ro.getEnable());
            this.baseService.update(domain);
            return HttpResult.success(Boolean.TRUE);
        } else {
            throw new RuntimeException("该域模型没有删除状态");
        }
    }

    @ApiOperation("假删除")
    @PostMapping(value = "delete")
    public HttpResult<Boolean> delete(String id) throws Exception {
        D domain = this.baseService.queryById(clazzD, id);
        if (BaseEntity.class.isAssignableFrom(clazzD)) {
            BaseEntity baseEntity = (BaseEntity) domain;
            if (baseEntity.getIsDelete()==0){
                baseEntity.setIsDelete(1);
                this.baseService.update(domain);
            }
            return HttpResult.success(Boolean.TRUE);
        } else {
            throw new RuntimeException("该域模型没有启用禁用状态");
        }
    }


}