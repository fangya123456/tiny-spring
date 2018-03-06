package com.fy.example.spring.web.servlet.condition;

/**
 *
 * @author ya.fang
 * @date 2017/12/18
 */
public interface NameValueExpression<T> {
    /**获取参数名称*/
    String getName();
    /**获取参数值*/
    T getValue();
    /**匹配規則：= or !=*/
    boolean isNegated();
}
