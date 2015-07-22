package com.eova.engine;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

/**
 * SQL引擎
 * @author Jieven
 *
 */
public class SqlEngine {

	public static GroupTemplate gt;
	
	static{
		if (gt == null) {
			StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
			Configuration cfg = null;
			try {
				cfg = Configuration.defaultConfiguration();
			} catch (IOException e) {
				e.printStackTrace();
			}
			gt = new GroupTemplate(resourceLoader, cfg);
		}
	}

	public static String buildSql(String sql, Object... params) {
		
		Template t = gt.getTemplate(sql);
		for(Object o : params){
			t.binding(o.getClass().getSimpleName().toLowerCase(), o);
		}
		
		return t.render();
	}
	
}
