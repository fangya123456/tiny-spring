package com.fy.example.spring.aop.framework.adapter;

import com.fy.example.spring.aop.Advisor;
import com.fy.example.spring.aop.MethodBeforeAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * 前置增强适配拦截器
 * @author ya.fang
 * @date 2017/11/28
 */
public class MethodBeforeAdviceAdapter implements AdvisorAdapter {
    @Override
    public Boolean supportAdvice(Advice advice) {
        return (advice instanceof MethodBeforeAdvice);
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        MethodBeforeAdvice methodBeforeAdvice = (MethodBeforeAdvice) advisor.getAdvice();
        return new MethodBeforeAdviceInterceptor(methodBeforeAdvice);
    }
}
