package com.fy.example.spring.aop.framework;

/**
 * aop代理接口
 * @author ya.fang
 * @date 2017/11/25
 */
public interface AopProxy {

    /**
     * 根据默认加载器获取代理
     * @return
     */
    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
