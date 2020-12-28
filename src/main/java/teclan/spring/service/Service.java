package teclan.spring.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface Service {

    public JSONObject findOne(String  id);

    public JSONObject delete(String  id);

    public JSONObject deleteBatch(String  ids);

    public JSONObject create(HttpServletRequest request);

    public JSONObject update(HttpServletRequest request);

    public JSONObject query(HttpServletRequest request);

}
