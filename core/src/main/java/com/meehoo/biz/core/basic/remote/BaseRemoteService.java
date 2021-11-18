package com.meehoo.biz.core.basic.remote;

import com.meehoo.biz.common.util.StringUtil;
import com.meehoo.biz.core.basic.domain.security.Admin;
import com.meehoo.biz.core.basic.domain.security.User;
import com.meehoo.biz.core.basic.exception.TokenInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zc
 * @date 2020-06-17
 */
@Component
@Slf4j
@Qualifier("BaseRemoteService")
public class BaseRemoteService {
//    @Value("${tdc.url}")
    protected String tdcURL;
    private final BaseGet baseGet;
    private final BasePost basePost;

    public BaseRemoteService() {
        this.baseGet = new BaseGet(this);
        this.basePost = new BasePost(this);
    }

    public BaseGet get(){
        return baseGet;
    }

    public BasePost post(){
        return basePost;
    }

    String postUrl(String path){
        return tdcURL + "/"+path;
    }

    String getUrl(String path,Map<String,String> params){
        StringBuilder sb = new StringBuilder(tdcURL+"/"+path+"?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StringUtil.stringNotNull(entry.getKey())&& StringUtil.stringNotNull(entry.getValue())){
                sb.append(entry.getKey()+"="+entry.getValue()+"&");
            }
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }


    HttpEntity<Object> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", getToken());
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return new HttpEntity<>(headers);
    }
    HttpEntity<Object> postJsonHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型，这里用json所以是MediaType
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", getToken());
        //RestTemplate带参传的时候要用HttpEntity<?>对象传递
        return new HttpEntity<>(body, headers);
    }
    HttpEntity<Map<String,Object>> postFormHttpEntity(Map<String,Object> params){
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型，这里用json所以是MediaType
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("token", getToken());
        //RestTemplate带参传的时候要用HttpEntity<?>对象传递

//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//        params.setAll(ro);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(params, headers);
        return request;
    }

//    private static final Map<String,String> token_cache = new HashMap<>(100);
    private String getToken(){
//        return token_cache.getOrDefault(userId,"");
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof Admin){
            return "admin";
        }else if (principal instanceof User){
            return ((User) principal).getId();
        }
        throw new TokenInvalidException();
    }
}
