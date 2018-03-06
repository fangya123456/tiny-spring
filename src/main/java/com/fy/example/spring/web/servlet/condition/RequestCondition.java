package com.fy.example.spring.web.servlet.condition;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求过滤接口
 * Created by ya.fang on 2017/12/18.
 */
public interface RequestCondition<T> {

    /**
     * 组合过滤条件对象
     * @param other
     * @return
     */
    T combine(T other);

    /**
     * 创建/获取请求匹配的过滤条件对象
     * @param request
     * @return
     */
    T getMatchingCondition(HttpServletRequest request);

    /**
     * 比较过滤条件对象优先级
     * @param other
     * @param request
     * @return
     */
    int compareTo(T other, HttpServletRequest request);
}
