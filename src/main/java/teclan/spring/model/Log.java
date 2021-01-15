package teclan.spring.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import teclan.flyway.utils.Strings;
import teclan.spring.util.HttpTool;
import teclan.spring.util.IdUtils;
import teclan.spring.util.PropertyConfigUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
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
    private String user;
    private String header;
    private Object parameter;
    private String result;
    private String status;
    private String createdAt;

    public Log(){}

    public Log(String sessionId, String host, int port,String user, String url, String header, Object parameter, String result, String status, String createdAt) {
        this.id = IdUtils.get();
        this.sessionId = sessionId;
        this.host = host;
        this.port = port;
        this.url = url;
        this.user=user;
        this.header = header;
        this.parameter = parameter;
        this.result = result;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Log( String sessionId, String host, int port, String user,String url, String header, Object parameter, String createdAt) {
        this.id = IdUtils.get();
        this.sessionId = sessionId;
        this.host = host;
        this.port = port;
        this.url = url;
        this.user=user;
        this.header = header;
        this.parameter = parameter;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static Log parse(HttpServletRequest httpServletRequest){

        String sessionId = httpServletRequest.getSession().getId();
        String host = httpServletRequest.getRemoteHost();
        int port = httpServletRequest.getRemotePort();
        String url = httpServletRequest.getRequestURI();

        Map<String,String> header = new HashMap<>();
        for(String key :headers){
            String value = httpServletRequest.getHeader(key);
            header.put(key,value);
        }

        String user = (String)httpServletRequest.getAttribute("user");
        String token = (String)httpServletRequest.getAttribute("token");
        if(!Strings.isEmpty(user)){
            header.put("user",user);
        }
        if(!Strings.isEmpty(token)){
            header.put("token",token);
        }

        String headerStr = JSON.toJSONString(header);

        String parameter = HttpTool.readJSONString(httpServletRequest);
        String createdAt =sdf.format(new Date());

        if(url.contains("logout.do")){
            JSONObject jsonObject = JSONObject.parseObject(parameter);
            user = jsonObject.getString("account");
            header.put("user",user);
        }


        return new Log(sessionId,host,port,header.get("user"),url,headerStr,parameter,createdAt);
    }

}
