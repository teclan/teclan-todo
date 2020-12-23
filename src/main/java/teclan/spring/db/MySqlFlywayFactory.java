package teclan.spring.db;

import teclan.flyway.db.AbstractFlywayFactory;
import teclan.spring.util.PropertyConfigUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySqlFlywayFactory extends AbstractFlywayFactory {
    private final static PropertyConfigUtil propertyconfigUtil;

    static {
        propertyconfigUtil = PropertyConfigUtil.getInstance("mysql.properties");
    }

    @Override
    protected String getDriver() {
        return propertyconfigUtil.getValue("jdbc.driverClassName");
    }

    @Override
    protected String getUrl() {
        return propertyconfigUtil.getValue("jdbc.url");
    }

    @Override
    protected String getUser() {
        return propertyconfigUtil.getValue("jdbc.username");
    }

    @Override
    protected String getPassword() {
        return propertyconfigUtil.getValue("jdbc.password");
    }
}
