package com.fy.example.spring.aop.framework;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 获取增强链列表工厂接口
 * @author ya.fang
 * @date 2017/11/29
 */
public interface AdvisorChainFactory {

    List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass);
}
