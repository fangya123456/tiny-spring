package com.fy.example.spring.beans;

import com.fy.example.spring.beans.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 从配置中读取/注册BeanDefinition
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private Map<String, BeanDefinition> registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader){
        this.registry = new HashMap<String, BeanDefinition>();
        this.resourceLoader = resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
