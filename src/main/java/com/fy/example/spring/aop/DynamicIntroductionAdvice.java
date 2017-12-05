package com.fy.example.spring.aop;

import org.aopalliance.aop.Advice;

/**
 *
 * @author ya.fang
 * @date 2017/12/1
 */
public interface DynamicIntroductionAdvice extends Advice{

    /**
     * 校验引入增强类是否实现给定的接口
     * @param intf
     * @return
     * */
    boolean implementsInterface(Class<?> intf);
}
