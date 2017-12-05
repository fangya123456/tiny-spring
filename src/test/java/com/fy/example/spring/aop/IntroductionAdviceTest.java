package com.fy.example.spring.aop;

import com.fy.example.spring.HelloWorldService;
import com.fy.example.spring.HelloWorldServiceImpl;
import com.fy.example.spring.aop.framework.AdvisedSupport;
import com.fy.example.spring.aop.framework.AopProxyFactory;
import com.fy.example.spring.aop.framework.DefaultAopProxyFactory;
import com.fy.example.spring.context.ApplicationContext;
import com.fy.example.spring.context.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * Created by ya.fang on 2017/12/2.
 */
public class IntroductionAdviceTest {

    @Test
    public void testIntroduction() throws Throwable{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tiny-spring.xml");
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();

        AdvisedSupport config = new AdvisedSupport();
        //1.设置被代理对象
        TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldServiceImpl.class);
        config.setTargetSource(targetSource);
        config.addAdvice(new HelloIntroAdvice());
        config.addInterface(Apology.class);

        config.setProxyTargetClass(true);
        //3.创建代理
        AopProxyFactory aopProxyFactory = new DefaultAopProxyFactory();
        HelloWorldServiceImpl helloWorldServiceProxy = (HelloWorldServiceImpl) aopProxyFactory.createAopProxy(config).getProxy();

        //基于Aop的调用
        helloWorldServiceProxy.helloWorld();

        Apology apology = (Apology)helloWorldServiceProxy;
        apology.saySorry("方亚");
    }
}
