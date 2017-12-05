package com.fy.example.spring.aop.support;

import com.fy.example.spring.aop.Pointcut;
import com.fy.example.spring.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 *
 * @author ya.fang
 * @date 2017/11/29
 */
public class DefaultPointcutAdvisor implements PointcutAdvisor {

    private Advice advice;

    public DefaultPointcutAdvisor(Advice advice){
        this.advice = advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }


    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return null;
    }
}
