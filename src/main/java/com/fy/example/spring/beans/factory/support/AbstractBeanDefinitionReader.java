package com.fy.example.spring.beans.factory.support;

import com.fy.example.spring.beans.factory.BeanDefinitionRegistry;
import com.fy.example.spring.beans.io.Resource;
import com.fy.example.spring.beans.io.ResourceLoader;
import com.fy.example.spring.core.env.Environment;
import com.fy.example.spring.core.env.EnvironmentCapable;

import java.util.Set;

/**
 * BeanDefinitionReader的抽象实现，并增加获取应用上下文环境的功能
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public abstract class AbstractBeanDefinitionReader implements EnvironmentCapable, BeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    private ClassLoader beanClassLoader;

    private Environment environment;

    /*private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();*/


    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
        // Determine ResourceLoader to use.
        if (this.registry instanceof ResourceLoader) {
            this.resourceLoader = (ResourceLoader) this.registry;
        }

        // Inherit Environment if possible
        if (this.registry instanceof EnvironmentCapable) {
            this.environment = ((EnvironmentCapable) this.registry).getEnvironment();
        }

    }

    /**
     * 获取BeanFactory
     * @return
     */
    public final BeanDefinitionRegistry getBeanFactory(){
        return this.registry;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    /**
     * 设置配置数据源加载器
     * @param resourceLoader
     */
    public void setResourceLoader(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    /**
     * 设置bean的类加载器
     * @param beanClassLoader
     */
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    /**
     * 设置应用上下文环境
     * @param environment
     */
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }


    @Override
    public int loadBeanDefinitions(Resource... resources) throws Exception {
        int counter = 0;
        for (Resource resource : resources) {
            counter += loadBeanDefinitions(resource);
        }
        return counter;
    }

    public int loadBeanDefinitions(String location, Set<Resource> actualResources) throws Exception{
        ResourceLoader resourceLoader = getResourceLoader();
        if (resourceLoader == null) {
            throw new Exception(
                    "Cannot import bean definitions from location [" + location + "]: no ResourceLoader available");
        }
        //1.Resource pattern matching available.

        //2.Can only load single resources by absolute URL.
        Resource resource = resourceLoader.getResource(location);
        int loadCount = loadBeanDefinitions(resource);
        if (actualResources != null) {
            actualResources.add(resource);
        }
        return loadCount;
    }

    @Override
    public int loadBeanDefinitions(String... locations) throws Exception {
        int counter = 0;
        for (String location : locations) {
            counter += loadBeanDefinitions(location);
        }
        return counter;
    }
}
