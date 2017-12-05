package com.fy.example.spring.aop.framework.adapter;

import com.fy.example.spring.aop.AfterReturningAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 后置增强方法拦截器
 * @author ya.fang
 * @date 2017/11/27
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor {

    private AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice){
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = methodInvocation.proceed();
        this.advice.after(methodInvocation.getThis(), methodInvocation.getMethod(), methodInvocation.getArguments());
        return result;
    }
}
