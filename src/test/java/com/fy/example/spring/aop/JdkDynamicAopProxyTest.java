package com.fy.example.spring.aop;

import com.fy.example.spring.HelloWorldService;
import com.fy.example.spring.aop.framework.AdvisedSupport;
import com.fy.example.spring.aop.framework.AopProxyFactory;
import com.fy.example.spring.aop.framework.DefaultAopProxyFactory;
import com.fy.example.spring.context.ApplicationContext;
import com.fy.example.spring.context.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * Created by ya.fang on 2017/11/27.
 */
public class JdkDynamicAopProxyTest {

    @Test
    public void testInterceptor() throws Exception{

        // --------- helloWorldService without AOP
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tiny-spring.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();

        AdvisedSupport config = new AdvisedSupport();
        //1.设置被代理对象
        TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldService.class);
        config.setTargetSource(targetSource);

        //2.设置拦截器
        /*MethodBeforeAdviceInterceptor methodBeforeAdviceInterceptor = new MethodBeforeAdviceInterceptor(new TestMethodBeforeAdvice());
        AfterReturningAdviceInterceptor afterReturningAdviceInterceptor = new AfterReturningAdviceInterceptor(new TestAfterReturningAdvice());
        config.addInterceptors(methodBeforeAdviceInterceptor);
        config.addInterceptors(afterReturningAdviceInterceptor);*/
      /*  config.addAdvice(new TestMethodBeforeAdvice());
        config.addAdvice(new TestAfterReturningAdvice());
        config.addAdvice(new TestMethodBeforeAdvice());*/
        config.addAdvice(new TimerInterceptor());

        //3.创建代理
        AopProxyFactory aopProxyFactory = new DefaultAopProxyFactory();
        HelloWorldService helloWorldServiceProxy = (HelloWorldService)aopProxyFactory.createAopProxy(config).getProxy();

        //基于Aop的调用
        helloWorldServiceProxy.helloWorld();
    }
}
