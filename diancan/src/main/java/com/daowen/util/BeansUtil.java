package com.daowen.util;


import com.daowen.jdbc.simplecrud.NativeJdbcAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daowen.jdbc.simplecrud.Adapter;

import com.daowen.jdbc.simplecrud.SimpleJdbc;

import java.util.Map;

public class BeansUtil {


	private static ApplicationContext ctx = null;

	public static void setApplicationContext(ApplicationContext context) {
		ctx = context;
	}

	public static <T> T getBean(String name, Class<T> clz) {

		Object obj = ctx.getBean(name);
		if (clz.isInstance(obj)) {
			return clz.cast(obj);
		}
		return null;

	}

	public static <T> Map<String, T> getBeanOfType(Class<T> clz){

		if(ctx==null)
			ctx=new ClassPathXmlApplicationContext("applicationContext.xml");;
		Map<String, T> map = ctx.getBeansOfType(clz);
        return map;

	}


}
