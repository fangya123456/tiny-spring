package com.fy.example.spring.aop.framework.adapter;

import com.fy.example.spring.aop.Advisor;
import com.fy.example.spring.aop.AfterReturningAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * 后置增强适配拦截器
 * @author ya.fang
 * @date 2017/11/28
 */
public class AfterReturningAdviceAdapter implements AdvisorAdapter {
    @Override
    public Boolean supportAdvice(Advice advice) {
        return (advice instanceof AfterReturningAdvice);
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        AfterReturningAdvice afterReturningAdvice = (AfterReturningAdvice)advisor.getAdvice();
        return new AfterReturningAdviceInterceptor(afterReturningAdvice);
    }
}
