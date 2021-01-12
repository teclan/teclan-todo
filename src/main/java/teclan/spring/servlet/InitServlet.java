package teclan.spring.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import teclan.netty.service.FileServer;
import teclan.spring.cache.GuavaCache;
import teclan.spring.db.MySqlFlywayFactory;
import teclan.spring.util.PropertyConfigUtil;

import java.util.Arrays;

public class InitServlet extends HttpServlet{
	
	private static final long serialVersionUID = 142275568750796042L;
	private final static PropertyConfigUtil propertyconfigUtil;

	static {
		propertyconfigUtil = PropertyConfigUtil.getInstance("config.properties");
	}

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private ApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			LOGGER.info("\n==============================程序自检开始..\n\n");

			new MySqlFlywayFactory().flyway();

			GuavaCache.init();

			new Thread(new Runnable() {
				@Override
				public void run() {
					new FileServer().run(propertyconfigUtil.getIntValue("fileServierPort"));
				}
			}).start();

			LOGGER.info("\n==============================程序自检结束..\n\n");
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			System.exit(0);
		}
	}
}