package com.meehoo.biz.core.basic.remote;

import com.meehoo.biz.core.basic.exception.TokenInvalidException;
import com.meehoo.biz.core.basic.param.HttpResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author zc
 * @date 2020-08-12
 */
public abstract class BaseHttp {
    protected Object exchange(RestTemplate restTemplate, String url, HttpEntity<Object> entity){
        ResponseEntity<HttpResult<Object>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,entity,new ParameterizedTypeReference<HttpResult<Object>>(){});
        HttpResult<Object> httpResult = responseEntity.getBody();
        if (httpResult==null){
            log("数据中台无法连接path:"+url,"");
            throw new RuntimeException("网络连接错误");
        } else if (httpResult.getFlag()== HttpResult.Flag_Success){
            return httpResult.getData();
        }else if (httpResult.getFlag()== HttpResult.Flag_NotLogin){
            throw new TokenInvalidException();
        } else{
            log("数据中台报错:"+url,httpResult.getMsg());
            throw new RuntimeException("获得数据错误");
        }
    }



    protected abstract void log(String s,Object o);
}
