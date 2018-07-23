package com.sinux.ssh.global;

import java.util.Map;

import com.google.common.collect.Maps;
import com.sinux.ssh.common.utils.PropertiesLoader;
import com.sinux.ssh.common.utils.StringUtils;
/**
 * 全局配置类
 * 
 * @author liliangdong
 * 
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	@SuppressWarnings("unused")
	private static Global global = new Global();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("config.properties");

	/**
	 * 获取配置
	 * 
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}

	public static void main(String[] args) {
		System.out.println(Global.getAdminPath());
	}
}
