package teclan.spring.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

	/**
	 * 遍历类的所有属性，将值为null的属性全部置""
	 * 
	 * @param e
	 */
	@SuppressWarnings("rawtypes")
	public static void setAllProperties(Object e) {
		Class cls = e.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			try {
				if (f.get(e) == null) {
					f.set(e, "");
				}
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				LOGGER.error(e1.toString());
			}

		}
	}

}
