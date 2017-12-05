package com.fy.example.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 *
 * @author ya.fang
 * @date 2017/12/1
 */
public interface IntroductionInterceptor extends MethodInterceptor, DynamicIntroductionAdvice {
}
