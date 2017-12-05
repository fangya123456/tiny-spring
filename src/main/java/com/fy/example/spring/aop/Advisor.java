package com.fy.example.spring.aop;

import org.aopalliance.aop.Advice;

/**
 *
 * @author ya.fang
 * @date 2017/11/28
 */
public interface Advisor {

    /**
     * 获取通知，Advice 是一个空接口
     * @return
     * */
    Advice getAdvice();
}
