package teclan.spring.dao;

import com.alibaba.fastjson.JSONObject;
import teclan.spring.model.Model;

import java.util.List;
import java.util.Map;

public interface Dao{

    public Map<String,Object> findOne(String  id);

    public Integer delete(String  id);

    public Integer deleteBatch(String[]  ids);

    public Integer create(JSONObject o);

    public Integer update(JSONObject o);

    public List<Map<String,Object>> query(Map<String,Object> parameter);

    public Integer countQuery(Map<String,Object> parameter);

}
