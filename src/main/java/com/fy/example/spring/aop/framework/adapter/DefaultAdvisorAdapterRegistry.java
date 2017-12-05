package com.fy.example.spring.aop.framework.adapter;

import com.fy.example.spring.aop.Advisor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册适配拦截器
 * @author ya.fang
 * @date 2017/11/28
 */
public class DefaultAdvisorAdapterRegistry implements AdvisorAdapterRegistry {

    private final List<AdvisorAdapter> adapters = new ArrayList<AdvisorAdapter>(3);

    public DefaultAdvisorAdapterRegistry() {
        /**初始化所有增强类型适配器*/
        registerAdvisorAdapter(new MethodBeforeAdviceAdapter());
        registerAdvisorAdapter(new AfterReturningAdviceAdapter());
    }

    /**
     * 根据切面获取对应的方法增强拦截器
     */
    @Override
    public MethodInterceptor[] getInterceptors(Advisor advisor) {
        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>(3);
        Advice advice = advisor.getAdvice();
        if (advice instanceof MethodInterceptor){
            interceptors.add((MethodInterceptor)advice);
        }
        for (AdvisorAdapter adapter : this.adapters) {
            if (adapter.supportAdvice(advice)) {
                interceptors.add(adapter.getInterceptor(advisor));
            }
        }
        if (interceptors.isEmpty()){
            throw new RuntimeException("the advisor is not match interceptors");
        }
        return interceptors.toArray(new MethodInterceptor[interceptors.size()]);
    }

    /**
     * 注册自定义的切面适配器
     * @param adapter
     */
    @Override
    public void registerAdvisorAdapter(AdvisorAdapter adapter) {
        this.adapters.add(adapter);
    }
}
