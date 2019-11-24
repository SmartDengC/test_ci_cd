package com.utils;

import com.dao.BasicConfigDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyBatisUtil {

    public BasicConfigDao getBean() {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring/spring-dao.xml");
        BasicConfigDao basicConfigDao=(BasicConfigDao) applicationContext.getBean("basicConfigDao");
        return basicConfigDao;
    }
}
