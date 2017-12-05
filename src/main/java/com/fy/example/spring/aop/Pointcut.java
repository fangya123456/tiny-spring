package com.fy.example.spring.aop;

/**
 * 切入点
 * @author ya.fang
 * @date 2017/12/1
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
