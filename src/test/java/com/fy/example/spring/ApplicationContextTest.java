package com.fy.example.spring;

import com.fy.example.spring.context.ApplicationContext;
import com.fy.example.spring.context.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * Created by ya.fang on 2017/11/21.
 */
public class ApplicationContextTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tiny-spring.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
