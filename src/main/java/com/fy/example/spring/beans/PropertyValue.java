package com.fy.example.spring.beans;

/**
 * 用于Bean的属性注入
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
