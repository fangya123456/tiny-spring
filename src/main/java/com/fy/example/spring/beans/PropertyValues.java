package com.fy.example.spring.beans;

import com.fy.example.spring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * 包装一个对象所有的PropertyValue。<br/>
 * 为什么封装而不是直接用List?因为可以封装一些操作。
 * @author ya.fang
 * Created by ya.fang on 2017/11/20.
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();

    public PropertyValues(){

    }

    public void addPropertyValue(PropertyValue pv){
        //TODO: 此处可以对propertyName进行判重，直接使用List没法做到
        this.propertyValueList.add(pv);
    }

    public List<PropertyValue> getPropertyValues(){
        return this.propertyValueList;
    }
}
