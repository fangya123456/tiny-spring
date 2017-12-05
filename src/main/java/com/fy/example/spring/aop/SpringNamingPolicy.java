package com.fy.example.spring.aop;

import net.sf.cglib.core.DefaultNamingPolicy;

/**
 *
 * @author ya.fang
 * @date 2017/11/30
 */
public class SpringNamingPolicy extends DefaultNamingPolicy {

    public static final SpringNamingPolicy INSTANCE = new SpringNamingPolicy();

    protected String getTag() {
        return "BySpringCGLIB";
    }
}
