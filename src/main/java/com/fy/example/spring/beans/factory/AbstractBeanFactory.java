package com.fy.example.spring.beans.factory;

import com.fy.example.spring.beans.BeanDefinition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象Bean工厂类，这里使用到模板方法
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public abstract class AbstractBeanFactory implements BeanFactory{
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private final List<String> beanDefinitionNames = new ArrayList<String>();
    @Override
    public Object getBean(String name) throws Exception{
        if (!this.beanDefinitionMap.containsKey(name)){
            throw new IllegalArgumentException("Bean named" +name + " bean is not exist");
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null){
            throw new IllegalArgumentException("No bean named" +  name + "is defined");
        }
        Object bean = beanDefinition.getBean();
        if (bean == null){
            bean = doCreateBean(beanDefinition);
        }
        return bean;
    }

    /**
     * 注册BeanDefinition, 具体方法
     * @param name
     * @param beanDefinition
     */
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception{
        this.beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    /**
     * 预实例化
     * */
    public void preInstantiateSingletons() throws Exception{
        Iterator it = this.beanDefinitionNames.iterator();
        while (it.hasNext()){
            String beanName = (String)it.next();
            getBean(beanName);
        }
    }

    /**
     * 初始化Bean，抽象方法
     * @param beanDefinition
     * @return
     */
    protected abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;
}
