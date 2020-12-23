package teclan.spring.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyConfigUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConfigUtil.class);

	private static final String SEP = ",";
	private static final String SEP2 = ":";

	private static final String DEFAULT_ENV = "ENV";
	private static final String DEFAULT_PROPERTIES_DIR = "properties/";

	private static Hashtable<String, PropertyConfigUtil> propertyConfigUtils = new Hashtable<String, PropertyConfigUtil>();

	private URL url;

	public String getPropertiesPath() {
		return url.getPath();
	}

	private PropertyConfigUtil(String propertiesFile, String dir, String env) {

		String environment = System.getenv(env);

		if (environment == null) {
			LOGGER.warn("环境变量 {} 未设置...", env);
			this.url = PropertyConfigUtil.class.getClassLoader().getResource(dir +"/"+propertiesFile);
		} else {
			this.url = PropertyConfigUtil.class.getClassLoader().getResource(dir + environment + "/" + propertiesFile);
		}

		if (url == null) {
			LOGGER.error("\n配置文件加载失败 {}\n",
					environment == null ? propertiesFile : dir + environment + "/" + propertiesFile);
		} else {
			LOGGER.error("\n配置文件加载成功 {}\n",
					environment == null ? propertiesFile : dir + environment + "/" + propertiesFile);
		}
	}

	/**
	 * 默认加载 classpath 下 properties 目录中环境变量YW_ENV对应目录下的配置
	 * 
	 * @param propertiesFile
	 *            配置文件名称
	 * @return
	 */
	public static PropertyConfigUtil getInstance(String propertiesFile) {
		PropertyConfigUtil configUtil = (PropertyConfigUtil) propertyConfigUtils
				.get(propertiesFile);
		if (configUtil == null) {
			configUtil = new PropertyConfigUtil(propertiesFile, DEFAULT_PROPERTIES_DIR, DEFAULT_ENV);
			propertyConfigUtils.put(propertiesFile, configUtil);
		}
		return configUtil;
	}

	/**
	 * 加载指定目录下，指定环境变量对应的目录下的某个配置文件
	 * 
	 * @param propertiesFile
	 *            配置文件名称
	 * @param propertiesDir
	 *            配置文件目录
	 * @param env
	 *            环境变量
	 * @return
	 */
	public static PropertyConfigUtil getInstance(String propertiesFile, String propertiesDir, String env) {
		PropertyConfigUtil configUtil = (PropertyConfigUtil) propertyConfigUtils.get(propertiesFile);
		if (configUtil == null) {
			configUtil = new PropertyConfigUtil(propertiesFile, propertiesDir, env);
			propertyConfigUtils.put(propertiesFile, configUtil);
		}
		return configUtil;
	}

	public int getIntValue(String key) {
		return Integer.parseInt(getValue(key));
	}

	public long getLongValue(String key) {
		return Long.parseLong(getValue(key));
	}

	public synchronized String getValue(String key) {
		Properties properties = new Properties();
		InputStream inputStream = null;

		try {
			LOGGER.debug("load the proerties :" + this.url);
			inputStream = url.openStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); // 解决中文乱码问题
			// properties.load(inputStream);
			properties.load(bf); // /加载属性列表
			
			check(key, properties);
			String value = properties.getProperty(key);
			LOGGER.debug("getValue from proerties:" + url
					+ ":" + key + "=" + value);
			return value;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("getValue(String)resourse:" + this.url,
					e);
		} catch (IOException e) {
			throw new RuntimeException("getValue(String)", e);
		} finally {
			try {
				if (inputStream == null) {
					LOGGER.error("can not get resourse:" + this.url);
				} else {
					inputStream.close();
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	protected boolean check(String key, Properties properties) {

		if (properties.get(key) != null) {
			return true;
		} else {
			throw new RuntimeException("配置文件中未发现配置项:" + key);
		}
	}

	public synchronized void setValue(String key, String value) {
		Properties properties = new Properties();
		OutputStream os = null;
		InputStream resourceAsStream = null;
		try {
			resourceAsStream = this.url.openStream();
			properties.load(resourceAsStream);
			properties.put(key, value);
			String fileAbPath = url.getFile();
			os = new FileOutputStream(new File(fileAbPath));
			properties.store(os, this.url.getFile());
		} catch (IOException e) {
			throw new RuntimeException(
					"setValue(String key, String value, String fileAbPath)resourse:"
							+ this.url, e);
		} finally {
			try {
				resourceAsStream.close();
				os.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
		LOGGER.debug("set value to proerties:" + this.url
				+ " " + key + "=" + value);
	}

	public boolean getBoolean(String key) {
		return new Boolean(this.getValue(key)).booleanValue();
	}

	public String[] getArray(String key) {
		return this.getValue(key).split(SEP);
	}

	public Map<String, String> getMap(String key) {
		String[] strings = this.getArray(key);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (int i = 0; i < strings.length; i++) {
			String[] att_value = strings[i].split(SEP2);
			if (att_value.length != 2) {
				throw new IllegalArgumentException("\"" + strings[i]
						+ "\"config error!");
			} else {
				hashMap.put(att_value[0], att_value[1]);
			}
		}
		return hashMap;
	}
}
