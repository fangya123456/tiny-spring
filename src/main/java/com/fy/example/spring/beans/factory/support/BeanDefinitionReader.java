package com.fy.example.spring.beans.factory.support;

import com.fy.example.spring.beans.factory.BeanDefinitionRegistry;
import com.fy.example.spring.beans.io.Resource;
import com.fy.example.spring.beans.io.ResourceLoader;

/**
 * 解决BeanDefinitionRegistry接口一次只能注册一个BeanDefinition，而且只能自己构造BeanDefinition类来注册
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public interface BeanDefinitionReader {

    /**
     * 获取注册类
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取数据源加载器
     * @return
     */
    ResourceLoader getResourceLoader();

    /**
     * 获取Bean的类加载器
     */
    ClassLoader getBeanClassLoader();

    /**
     * Return the BeanNameGenerator to use for anonymous beans
     * (without explicit bean name specified).
     */
   /* BeanNameGenerator getBeanNameGenerator();*/

    /**
     * 根据设置的单个数据源加载BeanDefinition
     * @param resource
     * @return
     * @throws Exception
     */
    int loadBeanDefinitions(Resource resource) throws Exception;

    /**
     * 根据设置的多个数据源加载BeanDefinition
     * @param resources
     * @return
     * @throws Exception
     */
    int loadBeanDefinitions(Resource... resources) throws Exception;

    /**
     * 根据
     * @param location
     * @return
     * @throws Exception
     */
    int loadBeanDefinitions(String location) throws Exception;

    int loadBeanDefinitions(String... locations) throws Exception;
}
