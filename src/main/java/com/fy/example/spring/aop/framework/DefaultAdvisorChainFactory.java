package com.fy.example.spring.aop.framework;

import com.fy.example.spring.aop.Advisor;
import com.fy.example.spring.aop.AfterAdvice;
import com.fy.example.spring.aop.BeforeAdvice;
import com.fy.example.spring.aop.MethodBeforeAdvice;
import com.fy.example.spring.aop.framework.adapter.AdvisorAdapterRegistry;
import com.fy.example.spring.aop.framework.adapter.AfterReturningAdviceAdapter;
import com.fy.example.spring.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import com.fy.example.spring.aop.framework.adapter.MethodBeforeAdviceAdapter;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ya.fang
 * @date 2017/11/29
 */
public class DefaultAdvisorChainFactory implements AdvisorChainFactory {
    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass) {
        List<Object> interceptorList = new ArrayList<Object>(config.getAdvisors().length);
        AdvisorAdapterRegistry registry = new DefaultAdvisorAdapterRegistry();
        for (Advisor advisor : config.getAdvisors()){
            MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
            interceptorList.addAll(Arrays.asList(interceptors));
        }
        return interceptorList;
    }
}
