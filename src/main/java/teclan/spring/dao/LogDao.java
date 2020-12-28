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
public class LogDao implements Dao<Log>{

    @Override
    public Log findOne(String  id) {
        return jdbcTemplate.queryForObject("select * from logs where id=?",new Object[]{id},new LogRowMapper());
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
        return null;
    }

    @Override
    public Integer update(JSONObject o) {
        return null;
    }

    @Override
    public List<Log> query(Map<String, Object> parameter) {
        return jdbcTemplate.queryForList("select * from logs where 1=?",new Object[]{1},Log.class);
    }

    @Override
    public Integer countQuery(Map<String, Object> parameter) {
        return null;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
