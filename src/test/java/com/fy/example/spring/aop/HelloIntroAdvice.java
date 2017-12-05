package com.fy.example.spring.aop;

import com.fy.example.spring.aop.support.DelegatingIntroductionInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by ya.fang on 2017/12/2.
 */
public class HelloIntroAdvice extends DelegatingIntroductionInterceptor implements Apology {

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable{
        return super.invoke(mi);
    }

    @Override
    public void saySorry(String name) {
        System.out.println("Sorry," + name);
    }
}
