package com.fy.example.spring.aop.framework;

import java.io.Serializable;

/**
 * Aop代理的默认实现类
 * @author ya.fang
 * @date 2017/11/28
 */
public class DefaultAopProxyFactory implements AopProxyFactory,Serializable {
    @Override
    public AopProxy createAopProxy(AdvisedSupport config) {
        if (config.isProxyTargetClass()){
            return new CglibAopProxy(config);
        }else {
            return new JdkDynamicAopProxy(config);
        }
    }
}
