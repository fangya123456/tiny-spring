package com.fy.example.spring.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * Created by ya.fang on 2017/11/27.
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Object target, Method method, Object[] args);
}
