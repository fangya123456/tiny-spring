package com.fy.example.spring.context;

import com.fy.example.spring.beans.BeanDefinition;
import com.fy.example.spring.beans.factory.AbstractBeanFactory;
import com.fy.example.spring.beans.factory.AutowireCapableBeanFactory;
import com.fy.example.spring.beans.io.ResourceLoader;
import com.fy.example.spring.beans.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * Created by ya.fang on 2017/11/21.
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    private String configLocation;

    public ClassPathXmlApplicationContext(String configLocation) throws Exception{
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    public void refresh() throws Exception {
        // 1.读取配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(this.configLocation);

        // 2.初始化BeanFactory并注册bean
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            this.beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }
}
