package teclan.spring.filter;

import com.teclam.jwt.JwtFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;
import teclan.spring.cache.GuavaCache;
import teclan.spring.util.HttpTool;
import teclan.spring.util.Objects;
import teclan.spring.util.PropertyConfigUtil;
import teclan.spring.util.ResultUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class HttpRequestHeaderFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpRequestHeaderFilter.class);
    private final static PropertyConfigUtil propertyconfigUtil;
    private static String[] headers= null;
    private static List<String> whiteUrls=new ArrayList<>();
    private static String baseUrl=null;
    private static ApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
    private static JdbcTemplate jdbcTemplate;


    static {
        propertyconfigUtil = PropertyConfigUtil.getInstance("config.properties");
        headers = propertyconfigUtil.getValue("headers").split(",");
        whiteUrls = Arrays.asList(propertyconfigUtil.getValue("whiteUrls").split(","));
        baseUrl=propertyconfigUtil.getValue("baseUrl");
        jdbcTemplate=(JdbcTemplate) wac.getBean("jdbcTemplate");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        StatusExposingServletResponse response = new StatusExposingServletResponse(
                (HttpServletResponse) servletResponse);

        String requestURI = request.getRequestURI();


        if(!whiteUrls.contains(requestURI.replace(baseUrl,""))){

            for(String key :headers){
                String value = request.getHeader(key);
                if(value==null){
                    LOGGER.error("\n\n 请求头信息错误，字段 {} 值为空,url={},请求被拦截!!\n\n",key,requestURI);
                    HttpTool.setResponse(response,200,ResultUtil.get(403, "认证失败,缺失字段:"+key));
                    return ;
                }
            }

            try{
                String user = request.getHeader("user");
                String cacheToken = GuavaCache.get(user);
                Assert.isTrue(JwtFactory.verify(user,cacheToken));
            }catch (Exception e){
                LOGGER.error("未找到会话信息",e);
                HttpTool.setResponse(response,200,ResultUtil.get(403, "认证失败，会话无效"));
                return;
            }
        }


        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy() {

    }


}
