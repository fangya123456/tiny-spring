package com.fy.example.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by ya.fang on 2017/11/27.
 */
public class TimerInterceptor implements MethodInterceptor{
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long time = System.nanoTime();
        System.out.println("Invocation of Method " + methodInvocation.getMethod().getName() + " start!");
        Object proceed = methodInvocation.proceed();
        System.out.println("Invocation of Method " + methodInvocation.getMethod().getName() + " end! takes " + (System.nanoTime() - time)
                + " nanoseconds.");
        return proceed;    }
}
