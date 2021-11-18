package com.meehoo.biz.core.basic.sql;

import com.meehoo.biz.common.util.BaseUtil;
import com.meehoo.biz.common.util.DateUtil;
import com.meehoo.biz.core.basic.param.PageResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 创建sql的工具类
 * @author zc
 * @date 2019-08-22
 */
public class SqlUtil {

    public static String objectToString(Object object){
        if (object!=null)
            return String.valueOf(object);
        return "";
    }

    public static double objectToDouble(Object object){
        try {
             if (object!=null){
                String s = String.valueOf(object);
                if (!"".equals(s)&&!"null".equalsIgnoreCase(s)){
                    return Double.parseDouble(s);
                }
            }
        }catch (NumberFormatException e){
            System.out.println("转化Double失败:"+String.valueOf(object));
        }
        return 0d;
    }

    public static int objectToInt(Object object){
        try {
            if (object!=null){
                String s = String.valueOf(object);
                if (!"".equals(s)) {
                    if (s.contains(".")){
                        s = s.substring(0,s.indexOf("."));
                    }
                    return Integer.parseInt(s);
                }
            }
        }catch (NumberFormatException e){
            System.out.println("转化Int失败:"+String.valueOf(object));
        }
        return 0;
    }

    public static Date objectToDate(Object object){
        if (object == null)
            return null;
        if (object instanceof Date){
            return (Date) object;
        }
        else if (object instanceof String){
            try {
                return DateUtil.stringToDate((String) object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> PageResult<T> listToPage(List<T> list, int page, int row){
        Long total = BaseUtil.collectionNotNull(list)?list.size():0l;
        int startIndex = (page - 1)*row;
        int endIndex = startIndex + row;
        if (startIndex > total - 1){
            return new PageResult<>(total,new ArrayList<>());
        }else if (endIndex > total - 1){
//            endIndex = (int)(total - 1);
            endIndex = total.intValue();
        }
        return new PageResult<>(total,list.subList(startIndex,endIndex));
    }

    public static String getInParam(Collection<String> sources){
        if (BaseUtil.collectionNotNull(sources)){
            StringBuilder params = new StringBuilder("(");
            for (String source : sources) {
                params.append("'");
                params.append(source);
                params.append("',");
            }
            params.delete(params.length()-1,params.length());
            params.append(")");
            return params.toString();
        }
        return "('!')";
    }

    public static String getYMDSelect(String field){
        String select =  "CONCAT(year(field),'-',\n" +
                "IF(LENGTH(month(field))=1,CONCAT('0',month(field)),month(field)),'-',\n" +
                "IF(LENGTH(day  (field))=1,CONCAT('0',day  (field)),day  (field))) AS " + SQLHelper.Result_Date;
        return select.replaceAll("field",field);
    }

    public static String getYMSelect(String field){
        String select =  "CONCAT(year(field),'-',\n" +
                "IF(LENGTH(month(field))=1,CONCAT('0',month(field)),month(field))) AS " + SQLHelper.Result_Date;
        return select.replaceAll("field",field);
    }

    public static String getWeekSelect(String field){
        String select = " weekofyear("+field+") AS "+ SQLHelper.Result_Date;
        return select;
    }

    public static String getYMDGroupBy(String field){
        String groupBy = "year("+field+"),month("+field+"),day("+field+")";
        return groupBy;
    }

    public static String getYMGroupBy(String field){
        String groupBy = "year("+field+"),month("+field+")";
        return groupBy;
    }

    public static String getWeekGroupBy(String field){
        String groupBy = "weekofyear("+field+")";
        return groupBy;
    }

    public static List<PieChartVO> getEveryDayOfMonth(){
        List<Date> everyDayOfMonth = DateUtil.getEveryDayOfMonth();
        return everyDayOfMonth.stream().map(e -> new PieChartVO(DateUtil.dateToString(e))).collect(Collectors.toList());
    }

    public static List<PieChartVO> getEveryMonthOfYear(){
        List<Date> everyMonthOfYear = DateUtil.getEveryMonthOfYear();
        return everyMonthOfYear.stream().map(e -> new PieChartVO(DateUtil.ymToString(e))).collect(Collectors.toList());
    }

    public static List<PieChartVO> getGroupByPieChartMap(GroupByYmdType groupByYmdType){
        List<PieChartVO> everyMonthOfYear;
        if (groupByYmdType == GroupByYmdType.YMD){
            everyMonthOfYear = getEveryDayOfMonth();
        }else if (groupByYmdType == GroupByYmdType.YM){
            everyMonthOfYear = getEveryMonthOfYear();
        }else if (groupByYmdType == GroupByYmdType.WEEK){
            everyMonthOfYear = getEveryWeekOfYear();
        }else{
            everyMonthOfYear = new ArrayList<>(0);
        }
        return everyMonthOfYear;
    }

    private static List<PieChartVO> getEveryWeekOfYear() {
        // 获得今年到今天的每一周
        Calendar calendar = Calendar.getInstance();
        int qty = calendar.get(Calendar.WEEK_OF_YEAR);

        List<PieChartVO> pieChartVOS = new ArrayList<>(qty);
        for (int i=0;i++<qty;){
            calendar.set(Calendar.WEEK_OF_YEAR,i);

            PieChartVO vo = new PieChartVO();
            calendar.set(Calendar.DAY_OF_WEEK,1);
            String time = DateUtil.dateToString(calendar.getTime());
            calendar.set(Calendar.DAY_OF_WEEK,7);
            time+="~"+DateUtil.dateToString(calendar.getTime());
            vo.setName(time);

            vo.setValue((double) i);

            pieChartVOS.add(vo);
        }
        return pieChartVOS;
    }

    public static void copyValue(Map source,Object target){
        Class<?> targetClass = target.getClass();
        Method[] methods = targetClass.getMethods();

        Map<String,Method> name_method = new HashMap<>(methods.length/2);
        for (Method method : methods) {
            String name = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (name.startsWith("set")&&parameterTypes.length==1){
                String key = name.substring(3, name.length());
                name_method.put(key.toLowerCase(),method);
            }
        }

        for (Object key : source.keySet()) {
            String keyStr = String.valueOf(key).toLowerCase();
            if (name_method.containsKey(keyStr)){
                Method method = name_method.get(keyStr);
                try {
                    Object value = source.get(key);
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes[0]==Integer.class){
                        value = SqlUtil.objectToInt(value);
                    }
                    method.invoke(target, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private static Map<String,Float> getProporte(List<Object[]> sqlResult){
//        sqlResult.
//    }
}
