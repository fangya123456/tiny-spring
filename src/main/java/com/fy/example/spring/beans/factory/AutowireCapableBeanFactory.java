package com.fy.example.spring.beans.factory;

import com.fy.example.spring.beans.BeanDefinition;
import com.fy.example.spring.BeanReference;
import com.fy.example.spring.beans.PropertyValue;

import java.lang.reflect.Field;

/**
 * 具有自动装配功能的Bean工厂
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception{
        Object bean = createBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception{
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 设置Bean的成员属性
     */
    protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception{
        for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
            //返回该Class对象对应类的、指定名称的成员变量
            Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
            declaredField.setAccessible(true);
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference){
                BeanReference beanReference = (BeanReference)value;
                value = getBean(beanReference.getName());
            }
            declaredField.set(bean, value);
        }
    }
}
