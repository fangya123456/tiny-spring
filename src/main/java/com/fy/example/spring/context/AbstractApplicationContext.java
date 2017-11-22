package com.fy.example.spring.context;

import com.fy.example.spring.beans.factory.AbstractBeanFactory;

/**
 * Created by ya.fang on 2017/11/21.
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    public void refresh() throws Exception{
    }

    @Override
    public Object getBean(String name) throws Exception{
        return this.beanFactory.getBean(name);
    }
}
