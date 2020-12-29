package teclan.spring.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import teclan.spring.model.Log;
import teclan.spring.rowmapper.LogRowMapper;
import teclan.spring.util.Objects;

import java.util.List;
import java.util.Map;

@Repository
public class FileMgrDao extends AbstractDao implements Dao{


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String,Object> findOne(String id) {
        return  jdbcTemplate.queryForMap("select * from file_mgr where id=?",new Object[]{id});
    }

    @Override
    public Integer delete(String id) {
        return  jdbcTemplate.update("select * from file_mgr where id=?",new Object[]{id});
    }

    @Override
    public Integer deleteBatch(String[] ids) {
        return  jdbcTemplate.update(String.format("select * from file_mgr where id in ('%s')", Objects.Joiner("','",ids)));
    }

    @Override
    public Integer create(JSONObject o) {
        return null;
    }

    @Override
    public Integer update(JSONObject o) {
        return null;
    }

    @Override
    public List<Map<String, Object>> query(Map<String, Object> parameter) {
        parameter.put("opttype","AUTHORIZE");
        return jdbcTemplate.queryForList("select t1.*,t2.cname authorize_display,t3.name owner_dispay from file_mgr t1 inner join s_dic t2 on t1.authorize=t2.ename inner join user_info t3 on t3.account=t1.owner "+ transform4Query(parameter));
    }

    @Override
    public Integer countQuery(Map<String, Object> parameter) {
        parameter.put("opttype","AUTHORIZE");
        return jdbcTemplate.queryForObject("select COUNT(*) from file_mgr t1 inner join s_dic t2 on t1.authorize=t2.ename inner join user_info t3 on t3.account=t1.owner "+ transform4Count(parameter),Integer.class);
    }
}
