package com.meehoo.biz.core.basic.sql;


import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.param.PageResult;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zc
 * @date 2020-08-05
 */
public class SQLBuilder {
    private StringBuilder select;
    private StringBuilder from;
    private StringBuilder where;
    private StringBuilder groupBy;
    private StringBuilder orderBy;
    private SQLHelper sqlHelper;
    private Class entityClass;
    private Class joinClass1;
    private Class joinClass2;
    private Class joinClass3;

    public SQLBuilder(SQLHelper sqlHelper) {
        this.select = new StringBuilder("SELECT ");
        this.from = new StringBuilder("FROM ");
        this.where = new StringBuilder("WHERE ");
        this.groupBy = new StringBuilder("GROUP BY ");
        this.orderBy = new StringBuilder("ORDER BY ");
        this.sqlHelper = sqlHelper;
    }

    void setEntityClass(Class entityClass){
        this.entityClass = entityClass;
    }

    public SQLBuilder join(Class joinClass){
        if ((joinClass1!=null&&joinClass1.equals(joinClass))||(joinClass2!=null&&joinClass2.equals(joinClass))){
            return this;
        }
        if (joinClass1 == null){
            joinClass1 = joinClass;
        }else if (joinClass2 == null){
            joinClass2 = joinClass;
        }else if (joinClass3 == null){
            joinClass3 = joinClass;
        }else{
            throw new RuntimeException("最多支持join三个类");
        }
        // 判断外键在哪边
        boolean b = foreignKeyPoint(entityClass, joinClass);
        String joinTableName = sqlHelper.getTableNameFromClass(joinClass);
        if (StringUtil.stringIsNull(joinTableName)){
            throw new RuntimeException("没有找到关联class");
        }
        // 关联表
        fromJoin(joinTableName,joinClass.getSimpleName(),b);
        return this;
    }

    // entityClass的参数添加类别称，joinClass的参数添加关联类
    private String modifyParam(String param){
        if (param.contains(entityClass.getSimpleName()+".")||param.contains("*")){
            return param;
        }
        else if (param.contains(".")){
            if (entityClass==null){
                throw new RuntimeException("没有找到所属查询class");
            }
            String join = param.substring(0,param.indexOf("."));
            if (join.contains("(")){
                join = join.substring(join.lastIndexOf("(")+1,join.length());
            }
            // 关联类
            Class c;
            if (joinClass1 == null){
                joinClass1 = (c = getClassFromFieldName(join));
            } else if (StringUtil.equalsWithLowcase(joinClass1.getSimpleName(),join)){
                return param;
            } else if (joinClass2 == null){
                joinClass2 = (c = getClassFromFieldName(join));
            } else if (StringUtil.equalsWithLowcase(joinClass2.getSimpleName(),join)){
                return param;
            } else if (joinClass3 == null){
                joinClass3 = (c = getClassFromFieldName(join));
            } else if (StringUtil.equalsWithLowcase(joinClass3.getSimpleName(),join)){
                return param;
            } else{
                throw new RuntimeException("最多支持join三个类");
            }
            // 找到对应表名
            String joinTableName = sqlHelper.getTableNameFromClass(c);
            if (StringUtil.stringIsNull(joinTableName)){
                throw new RuntimeException("没有找到关联class");
            }
            // 判断外键在哪边
//            boolean b = foreignKeyPoint(entityClass, c);
            // 关联表
            fromJoin(joinTableName,join,true);
            return param;
        }else{
            return addEntityClass(param);
        }
    }

    private boolean foreignKeyPoint(Class a,Class b){
        Field[] aFields = a.getDeclaredFields();
        for (Field f : aFields) {
            if (f.getDeclaringClass().equals(b)){
                return true;
            }
        }
        return false;
    }

    private String addEntityClass(String param){
        return entityClass.getSimpleName()+"."+param;
    }

    private Class getClassFromFieldName(String join){
        Field f = null;
        try {
            f = entityClass.getDeclaredField(join);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(entityClass.getSimpleName()+"没有找到所对应的字段:"+join);
        }
        return f.getType();
    }

    public SQLBuilder selectPure(String select){
        if (this.select.length()!=7){
            this.select.append(",");
        }
        this.select.append(select);
        this.select.append(" ");
        return this;
    }

    // select
    public SQLBuilder select(String param){
        if (StringUtil.stringIsNull(param)){
            return this;
        }
        param = modifyParam(param);
        if (select.length()!=7){
            select.append(",");
        }
        select.append(param);
        select.append(" ");
        return this;
    }

    public SQLBuilder selectDistinct(String param){
        if (StringUtil.stringIsNull(param)){
            return this;
        }
        param = modifyParam(param);
        if (select.length()!=7){
            select.append(",");
        }
        select.append("DISTINCT ").append(param).append(" ");
        return this;
    }

    public SQLBuilder selectCount(){
        return select("COUNT(*) AS " + SQLHelper.Result_Count);
    }

    public SQLBuilder selectDateDiff(String startField, String endField){
        startField = modifyParam(startField);
        endField = modifyParam(endField);
        return select("DATEDIFF("+endField+","+startField+") as " + SQLHelper.Result_DATEDIFF);
    }

    public SQLBuilder selectDateDiffFromNow(String endField){
        endField = modifyParam(endField);
        return selectPure("DATEDIFF(SYSDATE(),"+endField+") as " + SQLHelper.Result_DATEDIFF);
    }

    public SQLBuilder selectCount(String distinctField){
        distinctField = modifyParam(distinctField);
        return select("COUNT(DISTINCT "+distinctField+") AS " + SQLHelper.Result_Count);
    }

    public SQLBuilder selectSum(String field){
        field = modifyParam(field);
        return select("SUM("+field+") AS " + SQLHelper.Result_Sum);
    }

    public SQLBuilder selectAvg(String field){
        field = modifyParam(field);
        return select("AVG("+field+") AS " + SQLHelper.Result_Avg);
    }

    public SQLBuilder selectIsNULL(String field){
        field = modifyParam(field);
        return select("ISNULL("+field+") AS " + SQLHelper.Result_IsNull);
    }

    public SQLBuilder selectIf(boolean isFirst, String p1, String p2){
        if (isFirst){
            return select(p1);
        }else{
            return select(p2);
        }
    }

    // from
    SQLBuilder from(String tableName){
        from.append(" ").append(tableName).append(" ")
            .append(entityClass.getSimpleName())
                .append(" ");
        return this;
    }

    SQLBuilder fromJoin(String joinTableName, String joinField, boolean foreignPoint){
        from.append(" LEFT JOIN ")
                .append(joinTableName)
                .append(" ").append(joinField)
                .append(" ON ");
        if (foreignPoint){
            from.append(entityClass.getSimpleName()).append(".").append(joinField).append("_id")
                    .append(" = ")
                    .append(joinField).append(".id ");
        }else{
            from.append(entityClass.getSimpleName()).append(".id ")
                    .append(" = ")
                    .append(joinField).append(".").append(entityClass.getSimpleName()).append("_id ");
        }
        return this;
    }

    // where
    public SQLBuilder where(String param, String operator, Object oldValue){
        if (oldValue instanceof String){
            String value = (String)oldValue;
            if (StringUtil.stringNotNull(value)&&!value.contains("null")){
                param = modifyParam(param);
                if (where.length()!=6){
                    where.append(" AND ");
                }
                where.append(" ").append(param).append(" ")
                        .append(operator);
                if ("IN".equalsIgnoreCase(operator)){
                    where.append(value);
                }else{
                    where.append(" '").append(value).append("' ");
                }
            }
        }else if ( oldValue instanceof Integer){
            Integer value = (Integer)oldValue;
            if (value !=null){
                param = modifyParam(param);
                if (where.length()!=6){
                    where.append(" AND ");
                }
                where.append(" ").append(param).append(" ")
                        .append(operator);
                if ("IN".equalsIgnoreCase(operator)){
                    where.append(value);
                }else{
                    where.append(value).append(" ");
                }
            }
        }

        return this;
    }

    public SQLBuilder whereParamsGE(String param, String value){
        return where(param,">=",value);
    }

    public SQLBuilder whereParamsGT(String param, String value){
        return where(param,">",value);
    }

    public SQLBuilder whereParamsIn(String param, Collection<String> values){
        if (BaseUtil.collectionNotNull(values)){
            where(param,"IN",SqlUtil.getInParam(values));
        }
        return this;
    }

    public SQLBuilder whereParamsLT(String param, String value){
        return where(param," < ",value);
    }

    public SQLBuilder whereParamsEQ(String param, Object value){
        return where(param," = ",value);
    }

    public SQLBuilder whereParamsLikeStart(String param, String value){
        return where(param," LIKE ",value+"%");
    }

    public SQLBuilder whereParamsLike(String param, String value){
        return where(param," LIKE ","%"+value+"%");
    }

    public SQLBuilder where(String params){
        if (where.length()!=6){
            where.append(" AND ");
        }
        where.append(params).append(" ");
        return this;
    }

    // groupBy
    public SQLBuilder groupBy(String groupByParams){
        groupByParams = modifyParam(groupByParams);
        if (groupBy.length()!=9){
            groupBy.append(" ,");
        }
        groupBy.append(groupByParams);
        return this;
    }

    public SQLBuilder groupByWithSelect(String param){
        select(param);
        return groupBy(param);
    }

    public SQLBuilder groupByIf(boolean isFirst, String groupBy1, String groupBy2){
        if (isFirst){
            return groupBy(groupBy1);
        }else{
            return groupBy(groupBy2);
        }
    }

    public SQLBuilder groupByYMD(String groupByParams){
        groupByParams = modifyParam(groupByParams);
        return groupBy(SqlUtil.getYMDGroupBy(groupByParams));
    }

    public SQLBuilder groupByYM(String groupByParams){
        groupByParams = modifyParam(groupByParams);
        groupBy("year("+groupByParams+")");
        groupBy("month("+groupByParams+")");
        return this;
    }

    public SQLBuilder groupByIsNull(String groupByParams){
        groupByParams = modifyParam(groupByParams);
        groupBy("ISNULL("+groupByParams+")");
        return this;
    }

    public SQLBuilder orderByAsc(String field){
        return orderBy(1,field);
    }

    public SQLBuilder orderByDesc(String field){
        return orderBy(-1,field);
    }

    public SQLBuilder orderBy(Integer mark, String field){
        if (StringUtil.stringIsNull(field)){
            return this;
        }
        if (orderBy.length()>9){
            orderBy.append(",");
        }
        if (mark != null&&0 > mark){
            orderBy.append(field).append(" DESC ");
        } else {
            orderBy.append(field).append(" ASC ");
        }
        return this;
    }


    // exe
    private String toSQL(){
        String sql = check(select);
        sql+=from;
        String whereStr = where.toString();
        if (!"WHERE ".equals(whereStr)){
            sql+=whereStr;
        }

        String groupByStr = check(groupBy);
        if (!"GROUP BY ".equals(groupByStr)){
            sql+=groupByStr;
        }
        String orderByStr = check(orderBy);
        if (!"ORDER BY ".equals(orderByStr)){
            sql+=orderByStr;
        }
        return sql;
    }

    private String check(StringBuilder sb){
        if (','==sb.charAt(sb.length()-1)){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public List<Map<String,Object>> exe(){
        String sql = toSQL();
        try {
            List<Map<String, Object>> result = sqlHelper.getSqlExecute().queryForEntry(sql);
            destroy();
            return result;
        }catch (Exception e){
            System.out.println(sql);
            throw e;
        }
    }

    public List<SqlResultMap> exeForResultMap(){
        List<Map<String, Object>> result = exe();
        List<SqlResultMap> mapList = result.stream().map(SqlResultMap::new).collect(Collectors.toList());
        result.clear();
        return mapList;
    }

    public List<Object[]> exeForArray(){
        String sql = toSQL();
        try {
            List<Object[]> result = sqlHelper.getSqlExecute().queryList(sql);
            destroy();
            return result;
        }catch (Exception e){
            System.out.println(sql);
            throw e;
        }
    }

    public int exeForCount(){
        select = new StringBuilder("SELECT ");
        selectCount();
        List<SqlResultMap> sqlResultMaps = exeForResultMap();
        return sqlResultMaps.get(0).getCount();
    }

    public PageResult<Map<String, Object>> exeForPage(int page, int rows){
        String listSql = toSQL();
        listSql+=" LIMIT "+(page-1)*rows+","+rows;
        List<Map<String, Object>> list = sqlHelper.getSqlExecute().queryForEntry(listSql);
        // 查询数量
        int total = exeForCount();
        return new PageResult((long)total,list);
    }

    private void destroy(){
        select = null;
        where = null;
        from = null;
        groupBy = null;
        orderBy = null;
        sqlHelper=null;
        entityClass = null;
        joinClass1 = null;
        joinClass2 = null;
    }
}
