package tools.commons;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * @BelongsProject: Tools
 * @BelongsPackage: tools.commons
 * @Author: csn
 * @Description: generate uid ,or convert map into javabean;rely on commons-beanutils.jar and commons-logging.jar
 *
 */
public class CommonUtils {
	/**
	 * 返回一个不重复的字符串
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 把map转换成对象
	 * 把Map转换成指定类型
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T toBean(Map map, Class<T> clazz) {
		try {
			/*
			 * 1. 通过参数clazz创建实例
			 * 2. 使用BeanUtils.populate把map的数据封闭到bean中
			 */
			T bean = clazz.newInstance();
			ConvertUtils.register(new DateConverter(), java.util.Date.class);
			BeanUtils.populate(bean, map);
			return bean;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}

