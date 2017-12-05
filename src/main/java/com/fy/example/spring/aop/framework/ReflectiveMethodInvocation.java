package com.fy.example.spring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * MethodInvocation 参数暴露将被调用的方法、目标连接点、AOP代理和传递给被调用方法的参数
 * @author ya.fang
 * @date 2017/11/27
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object proxy;

    protected final Object target;

    protected final Method method;

    protected Object[] args;

    protected final List<?> interceptorsAndDynamicMethodMatchers;

    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] args, List<Object> interceptorsAndDynamicMethodMatchers){
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.args = args;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object getProxy() {
        return this.proxy;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return invokeJoinpoint();
        }
        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        if (interceptorOrInterceptionAdvice instanceof MethodInterceptor){
            return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
        }else {
            return proceed();
        }
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.method;
    }

    protected Object invokeJoinpoint() throws Throwable{
        return this.method.invoke(this.target, this.args);
    }
}
