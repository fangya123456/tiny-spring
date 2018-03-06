package com.fy.example.spring.beans.factory.support;

import com.fy.example.spring.beans.config.BeanDefinitionHolder;
import com.fy.example.spring.beans.factory.BeanDefinitionRegistry;

/**
 * 工具类
 *
 * @author ya.fang
 * @create 2018/03/06
 **/
public class BeanDefinitionReaderUtils {

    /**
     * Register the given bean definition with the given bean factory.
     * @param definitionHolder the bean definition including name and aliases
     * @param registry the bean factory to register with
     * @throws Exception if registration failed
     */
    public static void registerBeanDefinition(
            BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry)
            throws Exception {
        // Register bean definition under primary name.
        String beanName = definitionHolder.getBeanName();
        registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());
    }
}
