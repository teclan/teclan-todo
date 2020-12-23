package teclan.spring.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teclan.spring.dao.Dao;
import teclan.spring.dao.LogDao;
import teclan.spring.model.Log;
import teclan.spring.model.Model;
import teclan.spring.util.HttpTool;
import teclan.spring.util.PropertyConfigUtil;
import teclan.spring.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LogService extends AbstractService {
    private final Logger LOGERR = LoggerFactory.getLogger(LogService.class);

    private final static PropertyConfigUtil propertyconfigUtil;
    private static int poolSize = 0;

    static {
        propertyconfigUtil = PropertyConfigUtil.getInstance("config.properties");
        poolSize = propertyconfigUtil.getIntValue("logPoolSize");
    }

    Map<String, JSONObject> logs = new ConcurrentHashMap<>();

    @Autowired
    private LogDao logDao;

    @Override
    protected Dao getDao() {
        return logDao;
    }


    public JSONObject create(HttpServletRequest request) {
        JSONObject json = null;
        try {
            json = HttpTool.readJSONParam(request);
        } catch (Exception e) {
            LOGERR.error(e.getMessage(), e);
            return ResultUtil.get(200, "推送失败", e.getMessage());
        }
        return create(json);
    }


    public JSONObject create(Log log) {
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(JSONObject.toJSONString(log));

        } catch (Exception e) {
            LOGERR.error(e.getMessage(), e);
            return ResultUtil.get(200, "推送失败", e.getMessage());
        }
        return create(json);
    }


    public JSONObject create(JSONObject jsonObject) {
        logs.put(jsonObject.getString("id"), jsonObject);

        if (logs.size() >= poolSize) {
            doCreate();
        }
        return ResultUtil.get(200, "推送成功");
    }


    private void doCreate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String key : logs.keySet()) {

                    JSONObject jsonObject = logs.get(key);

                    if("".equals(jsonObject.getString("result").trim())){
                        continue;
                    }
                    getDao().create(logs.remove(key));
                }
            }
        }).start();
    }
}
