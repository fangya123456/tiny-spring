package com.fy.example.spring.aop;

import java.lang.reflect.Method;

/**
 * 后置方法增强类
 * @author ya.fang
 * @date 2017/11/27
 */
public interface AfterReturningAdvice extends AfterAdvice {

    Object after(Object target, Method method, Object[] args);
}
