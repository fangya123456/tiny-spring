package com.fy.example.spring;

/**
 * 设置
 * @author ya.fang
 * Created by ya.fang on 2017/11/21.
 */
public class BeanReference {
    private String name;

    private Object bean;

    public BeanReference(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
