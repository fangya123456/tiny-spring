package com.fy.example.spring.aop.support;

import com.fy.example.spring.aop.ClassFilter;
import com.fy.example.spring.aop.DynamicIntroductionAdvice;
import com.fy.example.spring.aop.IntroductionAdvisor;
import com.fy.example.spring.aop.IntroductionInfo;
import org.aopalliance.aop.Advice;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author ya.fang
 * @date 2017/12/1
 */
public class DefaultIntroductionAdvisor implements IntroductionAdvisor, ClassFilter,Serializable{

    private final Advice advice;

    private final Set<Class<?>> interfaces = new LinkedHashSet<Class<?>>();

    private DefaultIntroductionAdvisor(Advice advice){
        this(advice, (advice instanceof IntroductionInfo ? (IntroductionInfo)advice : null));
    }

    public DefaultIntroductionAdvisor(Advice advice, IntroductionInfo introductionInfo){
        this.advice = advice;
        if (introductionInfo != null) {
            Class<?>[] introducedInterfaces = introductionInfo.getInterfaces();
            if (introducedInterfaces.length == 0) {
                throw new IllegalArgumentException("IntroductionAdviceSupport implements no interfaces");
            }
            for (Class<?> ifc : introducedInterfaces) {
                addInterface(ifc);
            }
        }
    }

    public void addInterface(Class<?> intf) {
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("Specified class [" + intf.getName() + "] must be an interface");
        }
        this.interfaces.add(intf);
    }

    @Override
    public Class<?>[] getInterfaces() {

        return this.interfaces.toArray(new Class<?>[this.interfaces.size()]);
    }

    @Override
    public ClassFilter getClassFilter() {
        return null;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return true;
    }

    @Override
    public void validateInterfaces() throws IllegalArgumentException {
        for (Class<?> ifc : this.interfaces) {
            if (this.advice instanceof DynamicIntroductionAdvice &&
                    !((DynamicIntroductionAdvice) this.advice).implementsInterface(ifc)) {
                throw new IllegalArgumentException("DynamicIntroductionAdvice [" + this.advice + "] " +
                        "does not implement interface [" + ifc.getName() + "] specified for introduction");
            }
        }
    }
}
