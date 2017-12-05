package com.fy.example.spring.aop;

/**
 * 封装引入增强相关信息
 * @author ya.fang
 * @date 2017/12/1
 */
public interface IntroductionAdvisor extends Advisor,IntroductionInfo{

    ClassFilter getClassFilter();

    void validateInterfaces() throws IllegalArgumentException;
}
