package com.fy.example.spring.web.bind.annotation;

import java.lang.annotation.*;

/**
 * Created by ya.fang on 2017/12/18.
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {

    String name() default "";
    /**
     * 模式请求路径数组(最常用的一个配置项)，诸如，/pets/{petId}
     */
    String[] value() default {};
    /**
     * 请求方法枚举对象数组(支持GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE)
     */
    RequestMethod[] method() default {};
    /**
     * 请求参数表达式数组,使用举例,
     * 1.params="myParam=myValue",  必须存在参数myParam,并且值为myValue.
     * 2.params="myParam"       必须存在参数myParam.
     * 3.params="!myParam"   必须不存在参数myParam.
     */
    String[] params() default {};
    /**
     * 头字段表达式数组,使用举例,
     * 1.headers="myHeader=myValue" 必须存在头字段myHeader,并且值为myValue
     */
    String[] headers() default {};
    /**
     * 请求内容媒体类型数组,如以下配置只处理Content-Type值为application/json的请求
     * 1.consumes="application/json"
     */
    String[] consumes() default {};
    /**
     * 应答媒体类型数组,如以下配置只处理Accept值为application/json的请求
     * 1.produces="application/json"
     */
    String[] produces() default {};
}
