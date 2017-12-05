package com.fy.example.spring.aop.framework.adapter;

import com.fy.example.spring.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 *
 * @author ya.fang
 * @date 2017/11/27
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor{

    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice){
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.advice.before(methodInvocation.getThis(), methodInvocation.getMethod(), methodInvocation.getArguments());
        return methodInvocation.proceed();
    }
}
