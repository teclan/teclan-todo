package teclan.spring.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import teclan.spring.model.Log;
import teclan.spring.rowmapper.LogRowMapper;

import java.util.List;
import java.util.Map;

@Repository
public class LogDao extends AbstractDao implements Dao<Log>{

    @Override
    public Log findOne(String  id) {
        return jdbcTemplate.queryForObject("select * from log where id=?",new Object[]{id},new LogRowMapper());
    }

    @Override
    public Integer delete(String id) {
        return null;
    }

    @Override
    public Integer deleteBatch(String[] ids) {
        return null;
    }

    @Override
    public Integer create(JSONObject o) {
        QueryObject queryObject = getQueryObject4Insert(o,"log");
        return jdbcTemplate.update(queryObject.getSql(),queryObject.getValues());
    }

    @Override
    public Integer update(JSONObject o) {
        return null;
    }

    public List<Map<String,Object>> query(Map<String, Object> parameter) {
        return jdbcTemplate.queryForList("select * from log "+ transform4Query(parameter));
    }

    @Override
    public Integer countQuery(Map<String, Object> parameter) {
        return jdbcTemplate.queryForObject("select COUNT(*) from log "+ transform4Count(parameter),Integer.class);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
