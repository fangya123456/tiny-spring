package com.fy.example.spring.aop;

/**
 * 获取引入增强的引入接口信息
 * @author ya.fang
 * @date 2017/12/1
 */
public interface IntroductionInfo {

    Class<?>[] getInterfaces();
}
