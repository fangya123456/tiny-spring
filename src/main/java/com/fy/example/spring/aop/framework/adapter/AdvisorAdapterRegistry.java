package com.fy.example.spring.aop.framework.adapter;

import com.fy.example.spring.aop.Advisor;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * Created by ya.fang on 2017/11/28.
 */
public interface AdvisorAdapterRegistry {

    MethodInterceptor[] getInterceptors(Advisor advisor);

    void registerAdvisorAdapter(AdvisorAdapter adapter);
}
