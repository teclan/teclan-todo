package teclan.spring.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import teclan.spring.model.Log;
import teclan.spring.model.Model;

import java.util.List;
import java.util.Map;

public interface Dao {

    public Object findOne(@Param("id")String  id);

    public Integer delete(@Param("id")String  id);

    public Integer deleteBatch(@Param("ids")String[]  ids);

    public Integer create(JSONObject o);

    public Integer update(JSONObject o);

    public List<Object> query(Map<String,Object> parameter);

    public Integer countQuery(Map<String,Object> parameter);

}
