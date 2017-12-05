package com.fy.example.spring.aop.framework;

/**
 * Aop代理工厂接口
 * @author ya.fang
 * @date 2017/11/28
 */
public interface AopProxyFactory {

    /**
     * 创建Aop代理
     * @param config
     * @return
     * */
    AopProxy createAopProxy(AdvisedSupport config);
}
