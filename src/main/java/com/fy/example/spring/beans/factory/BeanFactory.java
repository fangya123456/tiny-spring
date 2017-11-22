package com.fy.example.spring.beans.factory;

import com.fy.example.spring.beans.BeanDefinition;

/**
 * Created by ya.fang on 2017/11/20.
 */
public interface BeanFactory {
    Object getBean(String name) throws Exception;
}
