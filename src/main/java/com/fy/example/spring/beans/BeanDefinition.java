package com.fy.example.spring.beans;

/**
 * 对应xml文件或者注解@Bean的bean结构
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public class BeanDefinition {

    private Object bean;

    private Class<?> beanClass;

    private String beanClassName;

    private PropertyValues propertyValues = new PropertyValues();

    public BeanDefinition(){}


    public Object getBean(){
        return this.bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try{
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

}
