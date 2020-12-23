package teclan.spring.filter;

import java.io.IOException;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.ResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import teclan.spring.util.HttpTool;
import teclan.spring.util.Objects;
import teclan.spring.util.PropertyConfigUtil;

public class DataSyncFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(DataSyncFilter.class);
    private final static PropertyConfigUtil propertyconfigUtil;
    private static List<String> fileUploadUrls = new ArrayList<>();
    private static String baseUrl = null;

    static {
        propertyconfigUtil = PropertyConfigUtil.getInstance("config.properties");
        fileUploadUrls = Arrays.asList(propertyconfigUtil.getValue("fileUploadUrls").split(","));
        baseUrl = propertyconfigUtil.getValue("baseUrl");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();

        Object para = "";

        HttpServletRequest request = null;
        StatusExposingServletResponse response = new StatusExposingServletResponse(
                (HttpServletResponse) servletResponse);

        // 如果是文件上传的接口
        if (fileUploadUrls.contains(requestURI.replace(baseUrl, ""))) {

            para = "【文件】";
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver
                    .resolveMultipart(httpServletRequest);
            request = multipartRequest;
        } else {

            request = new BodyReaderHttpServletRequestWrapper(
                    (HttpServletRequest) servletRequest);

//            if ("application/json".equalsIgnoreCase(request.getContentType())) {
//                para = HttpTool.readJSONParam(request);
//            } else {
//                Set<String> parameters = request.getParameterMap().keySet();
//                List<String> query = new ArrayList<>();
//                Iterator<String> iterator = parameters.iterator();
//                while (iterator.hasNext()) {
//                    String m = iterator.next();
//                    query.add(String.format("%s=%s", m, request.getParameter(m)));
//                }
//                para = Objects.Joiner("&", query);
//            }
        }
//        new Thread(new ProcessFilter(request.getSession().getId(),requestURI,para)).start();
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
