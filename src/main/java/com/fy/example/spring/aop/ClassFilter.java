package com.fy.example.spring.aop;

/**
 * 过滤满足一个切入点或者引入约束条件的目标类集合
 * @author ya.fang
 * @date 2017/12/1
 */
public interface ClassFilter {

    boolean matches(Class<?> clazz);
}
