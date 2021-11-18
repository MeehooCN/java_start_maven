package com.meehoo.biz.core.basic.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meehoo.biz.core.basic.param.PageResult;
import com.meehoo.biz.core.basic.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @date 2020-08-10
 */
@Slf4j
public class BaseGet extends BaseHttp{
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final BaseRemoteService remoteService;

    public BaseGet(BaseRemoteService remoteService) {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
        this.remoteService = remoteService;
    }

    private String baseGet(String path, Map<String,String> params){
        String url = remoteService.getUrl(path, params);
        HttpEntity<Object> entity = remoteService.getHttpEntity();

        Object result = exchange(restTemplate, url, entity);
        return gson.toJson(result);
    }

    public <T> List<T> forList(String path, Map<String,String> params){
        String json = baseGet(path, params);
        List<T> list = gson.fromJson(json,new TypeToken<List<T>>(){}.getType());
        return list;
    }

    public <T> PageResult<T> forPageResult(String path, Map<String,String> params){
        String json = baseGet(path, params);
        PageResult<T> list = gson.fromJson(json,new TypeToken<PageResult<T>>(){}.getType());
        return list;
    }

    public <T> PageResult<T> forPageResult(String path, Map<String,String> params, Class<T> tClass){
        PageResult<T> pageResult = forPageResult(path,params);

        List<T> sourceList = pageResult.getRows();
        List<T> targetList = new ArrayList<>(sourceList.size());
        try {

            for (Object source : sourceList) {
//                BeanUtils.copyProperties(source,target);
                if (source instanceof Map){
                    T target = tClass.newInstance();

                    SpringUtil.copyProperties((Map) source,target);
                    targetList.add(target);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("LinkedHashMap转化Object失败");
//            targetList = sourceList;
        }
        return new PageResult<>(pageResult.getTotal(),targetList);
    }

    public <T> T forEntity(String path,Map<String,String> params,Class<T> tClass){
        String json = baseGet(path, params);
        T t = gson.fromJson(json,tClass);
        return t;
    }


    @Override
    protected void log(String s, Object o) {
        log.error(s,o);
    }
}
