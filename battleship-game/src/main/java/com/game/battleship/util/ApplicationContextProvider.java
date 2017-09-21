package com.game.battleship.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextProvider.applicationContext = applicationContext;
	}

	public  static <T> T getBean(String name,Class<T> aClass){
        return applicationContext.getBean(name,aClass);
    }

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
