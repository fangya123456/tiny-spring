package com.fy.example.spring.aop.framework;

import com.fy.example.spring.aop.TargetSource;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * JDK动态代理
 * @author ya.fang
 * @date 2017/11/27
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    AdvisedSupport config;

    public JdkDynamicAopProxy(AdvisedSupport config){
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{this.config.getTargetSource().getTargetClass()},
                this);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, new Class[]{this.config.getTargetSource().getTargetClass()}, this) ;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TargetSource targetSource = this.config.getTargetSource();
        Object target = targetSource.getTarget();
        Class<?> targetClass = (target != null? target.getClass():null);

        Object retVal;
        /**此处的MethodInterceptor 非Cglib的，是对MethodInvocation做了一次封装封装*/
        List<Object> chain= this.config.getInterceptorsAndDynamicInterceptionAdvice(method,targetClass);
        if (chain.isEmpty()){
           retVal = method.invoke(method, args);
        }else {
            ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(proxy,target, method, args, chain);
            retVal = invocation.proceed();
        }
        return retVal;
    }
}
