package teclan.spring.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import teclan.spring.cache.GuavaCache;
import teclan.spring.db.MySqlFlywayFactory;

public class InitServlet extends HttpServlet{
	
	private static final long serialVersionUID = 142275568750796042L;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private ApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  

	@Override
	public void init() throws ServletException {
		super.init();
		
		LOGGER.info("\n\n程序自检开始..\n\n");

		new MySqlFlywayFactory().flyway();

		GuavaCache.init();

		LOGGER.info("\n\n程序自检结束..\n\n");

	}
}