package com.fy.example.spring.aop;

/**
 *
 * @author ya.fang
 * @date 2017/12/1
 */
public interface PointcutAdvisor extends Advisor{

    Pointcut getPointcut();
}
