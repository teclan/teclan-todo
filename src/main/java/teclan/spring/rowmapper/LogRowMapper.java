package teclan.spring.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import teclan.spring.model.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogRowMapper implements RowMapper<Log> {

    @Override
    public Log mapRow(ResultSet rs, int i) throws SQLException {
        Log log = new Log();
        log.setSessionId(rs.getString("sessionId"));
        log.setHost(rs.getString("host"));
        log.setPort(rs.getInt("port"));
        log.setUser(rs.getString("user"));
        log.setHeader(rs.getString("header"));
        log.setParameter(rs.getString("parameter"));
        log.setResult(rs.getString("result"));
        log.setStatus(rs.getString("status"));
        log.setCreatedAt(rs.getString("createdAt"));
        return log;
    }
}
