package com.fy.example.spring.aop;

import java.lang.reflect.Method;

/**
 * Created by ya.fang on 2017/11/28.
 */
public class TestAfterReturningAdvice implements AfterReturningAdvice{
    @Override
    public Object after(Object target, Method method, Object[] args) {
        System.out.println("Invocation of Method " + method.getName() + " end!");
        return null;
    }
}
