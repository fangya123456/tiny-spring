package com.fy.example.spring.aop;

import java.lang.reflect.Method;

/**
 * 检查给定的方法是否匹配
 * @author ya.fang
 * @date 2017/12/1
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);
}
