package com.fy.example.spring.beans;

/**
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
