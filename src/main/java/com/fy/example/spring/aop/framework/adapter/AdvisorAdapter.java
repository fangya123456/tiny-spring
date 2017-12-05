package com.fy.example.spring.aop.framework.adapter;

import com.fy.example.spring.aop.Advisor;
import com.fy.example.spring.aop.framework.AdvisedSupport;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * 将Advice适配为MethodInterceptor
 * @author ya.fang
 * @date 2017/11/28
 */
public interface AdvisorAdapter {

    Boolean supportAdvice(Advice advice);

    MethodInterceptor getInterceptor(Advisor advisor);
}
