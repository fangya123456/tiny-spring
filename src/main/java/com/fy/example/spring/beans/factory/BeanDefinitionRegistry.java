package com.fy.example.spring.beans.factory;

import com.fy.example.spring.beans.BeanDefinition;

/**
 * 注册BeanDefinition
 * @author ya.fang
 * @date 2017/12/14
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception;

    void removeBeanDefinition(String beanName) throws Exception;

    BeanDefinition getBeanDefinition(String beanName) throws Exception;

    boolean containsBeanDefinition(String beanName);

    String[] getBeanDefinitionNames();

    int getBeanDefinitionCount();

    boolean isBeanNameInUse(String beanName);
}
