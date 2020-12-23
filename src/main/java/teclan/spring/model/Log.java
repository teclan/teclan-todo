package teclan.spring.model;

import com.alibaba.fastjson.JSON;
import teclan.spring.util.HttpTool;
import teclan.spring.util.IdUtils;
import teclan.spring.util.PropertyConfigUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Log extends Model {
    private final static PropertyConfigUtil propertyconfigUtil= PropertyConfigUtil.getInstance("config.properties");
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String[] headers = propertyconfigUtil.getValue("headers").split(",");

    private String id;
    private String sessionId;
    private String host;
    private int port;
    private String url;
    private String header;
    private Object parameter;
    private String result;
    private String status;
    private String createdAt;

    public Log(){}

    public Log(String id, String sessionId, String host, int port, String url, String header, Object parameter, String result, String status, String createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.host = host;
        this.port = port;
        this.url = url;
        this.header = header;
        this.parameter = parameter;
        this.result = result;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Log(String id, String sessionId, String host, int port, String url, String header, Object parameter, String createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.host = host;
        this.port = port;
        this.url = url;
        this.header = header;
        this.parameter = parameter;
        this.createdAt = createdAt;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String getTableName() {
        return "log";
    }

    public static Log parse(HttpServletRequest httpServletRequest){

        String id = IdUtils.get();
        String sessionId = httpServletRequest.getSession().getId();
        String host = httpServletRequest.getRemoteHost();
        int port = httpServletRequest.getRemotePort();
        String url = httpServletRequest.getRequestURI();

        Map<String,Object> header = new HashMap<>();

        for(String key :headers){
            String value = httpServletRequest.getHeader(key);
            header.put(key,value);
        }

        String parameter = HttpTool.readJSONString(httpServletRequest);
        String createdAt =sdf.format(new Date());


        return new Log(id,sessionId,host,port,url, JSON.toJSONString(header),parameter,createdAt);
    }

}
