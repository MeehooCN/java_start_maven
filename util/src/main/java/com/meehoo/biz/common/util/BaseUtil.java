package com.meehoo.biz.common.util;

import java.util.*;

/**
 * @author zc
 * @date 2020-01-08
 */
public class BaseUtil {
    /**
     @ApiOperation("")
     @GetMapping("")
     public HttpResult statistic(){
     return HttpResult.success();
     }
     */


    /**
     @ApiOperation("")
     @PostMapping("")
     public HttpResult statistic(){
     return HttpResult.success();
     }
     */

    /**
     * 检查List是否为空
     */
    public static boolean collectionNotNull(Collection list) {
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean listNotNull(List list) {
       return collectionNotNull(list);
    }

    public static boolean objectNotNull(Object object) {
        if (object instanceof String){
            return StringUtil.stringNotNull((String) object);
        }
        else if (object instanceof Collection){
            return collectionNotNull((Collection) object);
        }
        else{
            return object!=null;
        }
    }

    /**
     * 数组拼接
     */
    public static <T> T[] arrayConcat(T[] a, T[] b) {
        if (a == null) {
            return b;
        }
        if (a != null && b != null) {
            T[] result = Arrays.copyOf(a, a.length + b.length);
            System.arraycopy(b, 0, result, a.length, b.length);
            return result;
        }
        return a;
    }
    // 生成number
    public static String createTimeNumber(String pre){
        String no = "";
        no += DateUtil.timeToString(new Date());
        //随机生成3位随机码
        int max = 1000;
        int min = 100;
        Random random = new Random();
        int code = random.nextInt(max) % (max - min + 1) + min;
        return pre+no+code;
    }

    public Integer sexTransform(String sex){
        if ("男".equals(sex)){
            return 1;
        }else if ("女".equals(sex)){
            return 0;
        }else{
            return -1;
        }
    }

    public String sexTransform(int sex){
        if (sex==1){
            return "男";
        }else if (sex==0){
            return "女";
        }else{
            return "";
        }
    }
}
