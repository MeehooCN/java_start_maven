package com.meehoo.biz.core.basic.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.param.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @date 2020-08-10
 */
//@Component
@Slf4j
public class BasePost extends BaseHttp{
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final BaseRemoteService remoteService;

    public BasePost(BaseRemoteService remoteService) {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
        this.remoteService = remoteService;
    }

    private String basePost(String path,Object body){
        String url = remoteService.postUrl(path);
        HttpResult<Object> httpResult = null;
        if(body instanceof Map){
            // 表单
            HttpEntity<Map<String,Object>> request = remoteService.postFormHttpEntity((Map<String, Object>) body);
            ResponseEntity<HttpResult<Object>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<HttpResult<Object>>() {});
            httpResult = responseEntity.getBody();
        }else{
            // json
            HttpEntity<Object> request = remoteService.postJsonHttpEntity(body);

            ResponseEntity<HttpResult<Object>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<HttpResult<Object>>() {
            });
            httpResult = responseEntity.getBody();
        }

        if (httpResult==null){
            log.error("数据中台无法连接:"+path);
            throw new RuntimeException("网络连接错误");
        } else if (httpResult.getFlag()==0){
            Object data = httpResult.getData();
            return gson.toJson(data);
        }else{
            log.error("数据中台报错","path:\n"+httpResult.getMsg());
            throw new RuntimeException("获得数据错误");
        }
    }

    public  <T> PageResult<T> forPageResult(String path, Object body){
        String json = basePost(path, body);
        PageResult<T> list = gson.fromJson(json,new TypeToken<PageResult<T>>(){}.getType());
        return list;

    }

    public <T> List<T> forList(String path,Object body) throws Exception {
        String json = basePost(path, body);
        List<T> list = gson.fromJson(json,new TypeToken<List<T>>(){}.getType());
        return list;
    }

    public <T> T forEntity(String path,Object body,Class<T> tClass){
        String json = basePost(path, body);
        T t = gson.fromJson(json,tClass);
        return t;
    }

    public String fileForString(String path, MultipartFile file, Map<String,Object> params){
//        String url = tdcURL + "/"+path;
//        //设置请求头
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("multipart/form-data");
//        headers.setContentType(type);
//
//        //设置请求体，注意是LinkedMultiValueMap
//        FileSystemResource fileSystemResource = new FileSystemResource(filePath+"/"+fileName);
//        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
//        form.add("excel", fileSystemResource);
//        form.add("filename",file.getOriginalFilename());
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            form.add(entry.getKey(),entry.getValue());
//        }
//
//        //用HttpEntity封装整个请求报文
//        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
//
//        String s = restTemplate.postForObject(url, files, String.class);
//        return s;
        throw new RuntimeException("不可用");
    }

    @Override
    protected void log(String s, Object o) {
        log.error(s,o);
    }
}
