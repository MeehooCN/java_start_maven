package com.meehoo.biz.core.basic.sql;

import com.meehoo.biz.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zc
 * @date 2020-08-06
 */
@Component
public class SQLHelper {
    public static final String Result_Count = "sCount";
    public static final String Result_Date = "sDate";
    public static final String Result_Sum = "sSum";
    public static final String Result_Avg = "sAvg";
    public static final String Result_IsNull = "sIsNull";
    public static final String Result_DATEDIFF = "DATEDIFF";

    private final SqlExecute sqlExecute;
    private ThreadLocal<SQLBuilder> sqlLocal = new ThreadLocal<>();
    private final Map<String,String> entity_table = new ConcurrentHashMap<>(64);

    @Autowired
    public SQLHelper(SqlExecute sqlExecute) {
        this.sqlExecute = sqlExecute;
    }

//    public SQLBuilder createNewQuery(){
//        sqlBuilder = new SQLBuilder(this);
//        return sqlBuilder;
//    }

    String getTableNameFromClass(Class entityClass){
        String className = entityClass.getName();
        String tableName;
        if (entity_table.containsKey(className)){
            tableName = entity_table.get(className);
        }else{
            Table table = (Table) entityClass.getAnnotation(Table.class);
            tableName = table.name();
            entity_table.put(className,tableName);
        }
        return tableName;
    }

    public SQLBuilder createNewQuery(Class entityClass){
        String tableName = getTableNameFromClass(entityClass);

        SQLBuilder sqlBuilder = new SQLBuilder(this);
        sqlBuilder.setEntityClass(entityClass);
        sqlBuilder.from(tableName);
        sqlLocal.set(sqlBuilder);
        return sqlBuilder;
    }

    public SQLBuilder getTrend(Class entityClass, GroupByYmdType type, String timeField){
        String startTime = type == GroupByYmdType.YMD? DateUtil.dateToString(DateUtil.getThisMonthBegin()):DateUtil.dateToString(DateUtil.getThisYearBegin());
        return getTrend(entityClass, type, timeField,startTime,null);
    }

    public SQLBuilder getTrend(Class entityClass, GroupByYmdType type, String timeField, String startTime, String endTime){
        createNewQuery(entityClass);
        if (!timeField.contains(".")){
            timeField = entityClass.getSimpleName()+"."+timeField;
        }
        SQLBuilder sqlBuilder = sqlLocal.get();
        sqlBuilder
                .select(selectFromGroupByYmdType(type,timeField))
                .whereParamsGE(timeField,startTime)
                .whereParamsLT(timeField,endTime)
                .groupBy(groupByFromGroupByYmdType(type,timeField));
        return sqlBuilder;
    }

    private String groupByFromGroupByYmdType(GroupByYmdType groupByYmdType,String timeField){
        if (GroupByYmdType.YMD == groupByYmdType){
            return SqlUtil.getYMDGroupBy(timeField);
        }else if (GroupByYmdType.YM == groupByYmdType){
            return SqlUtil.getYMGroupBy(timeField);
        }else if (GroupByYmdType.WEEK == groupByYmdType){
            return SqlUtil.getWeekGroupBy(timeField);
        }else{
            throw new RuntimeException("sqlhelper不支持该groupByYmdType的groupBy");
        }
    }

    private String selectFromGroupByYmdType(GroupByYmdType groupByYmdType,String timeField){
        if (GroupByYmdType.YMD == groupByYmdType){
            return SqlUtil.getYMDSelect(timeField);
        }else if (GroupByYmdType.YM == groupByYmdType){
            return SqlUtil.getYMSelect(timeField);
        }else if (GroupByYmdType.WEEK == groupByYmdType){
            return SqlUtil.getWeekSelect(timeField);
        }else{
            throw new RuntimeException("sqlhelper不支持该groupByYmdType的select");
        }
    }

    SqlExecute getSqlExecute() {
        return sqlExecute;
    }

}
