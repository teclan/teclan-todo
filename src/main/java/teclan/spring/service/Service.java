package teclan.spring.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import teclan.spring.model.Log;
import teclan.spring.model.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface Service {

    public JSONObject findOne(@Param("id")String  id);

    public JSONObject delete(@Param("id")String  id);

    public JSONObject deleteBatch(@Param("ids")String  ids);

    public JSONObject create(HttpServletRequest request);

    public JSONObject update(HttpServletRequest request);

    public JSONObject query(HttpServletRequest request);

}
