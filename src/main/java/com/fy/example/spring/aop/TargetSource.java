package com.fy.example.spring.aop;

/**
 * 被代理的目标对象源
 * @author ya.fang
 * @date 2017/11/25
 */
public class TargetSource {

	private Class targetClass;

	private Object target;

	public TargetSource(Object target, Class<?> targetClass) {
		this.target = target;
		this.targetClass = targetClass;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public Object getTarget() {
		return target;
	}
}
