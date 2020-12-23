package teclan.spring.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import teclan.spring.model.Log;

public class ProcessFilter implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessFilter.class);
	private static final ClassPathXmlApplicationContext appContextData;
	private static JdbcTemplate jdbcTemplate;

	static {
		appContextData = new ClassPathXmlApplicationContext("ApplicationContext/Application-mysql-data-source.xml");
		jdbcTemplate=(JdbcTemplate) appContextData.getBean("jdbcTemplate");
	}
	private String sessionId;
	private Object requestParams;
	private String requestUrl;

	public ProcessFilter( String sessionId,String requestUrl,Object requestParams) {
		this.sessionId=sessionId;
		this.requestUrl = requestUrl;
		this.requestParams = requestParams;
	}

	@Override
	public void run() {

		if(requestParams instanceof String){
			LOGGER.info(String.format("监测到会话：%s, URL=%s,参数:%s",sessionId,requestUrl,requestParams==null||"".equals(((String) requestParams).trim())?"":"?"+requestParams));
		}else {
			LOGGER.info(String.format("监测到会话：%s, URL=%s,参数:%s",sessionId,requestUrl,requestParams.toString()));
		}

	}
}
