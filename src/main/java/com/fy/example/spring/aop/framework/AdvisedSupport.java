package com.fy.example.spring.aop.framework;

import com.fy.example.spring.aop.Advisor;
import com.fy.example.spring.aop.IntroductionAdvisor;
import com.fy.example.spring.aop.IntroductionInfo;
import com.fy.example.spring.aop.support.DefaultIntroductionAdvisor;
import com.fy.example.spring.aop.support.DefaultPointcutAdvisor;
import com.fy.example.spring.aop.TargetSource;
import org.aopalliance.aop.Advice;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代理相关的元数据
 * @author ya.fang
 * @date 2017/11/27
 */
public class AdvisedSupport {

    private TargetSource targetSource;

    private List<Advisor> advisors = new LinkedList<Advisor>();

    AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

    List<Class<?>> interfaces = new ArrayList<Class<?>>();

    /**
     * 配置Jdk和Cglib代理，默认使用Jdk代理， true: Cglib代理  false: Jdk代理
     * */
    private boolean proxyTargetClass = false;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    /**
     * 以method为key值，advisor 调用链表为value
     * */
    private transient Map<Method, List<Object>> methodCache;

    public AdvisedSupport(){
        this.methodCache = new ConcurrentHashMap<Method, List<Object>>(32);
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        List<Object> cached = this.methodCache.get(method);
        if (cached == null){
            cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this,method,targetClass);
            this.methodCache.put(method, cached);
        }
        return cached;
    }

    public void setInterfaces(Class<?>... interfaces) {
        this.interfaces.clear();
        for (Class<?> ifc : interfaces) {
            addInterface(ifc);
        }
    }

    /**
     * Add a new proxied interface.
     * @param intf the additional interface to proxy
     */
    public void addInterface(Class<?> intf) {
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
        }
        if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);
        }
    }

    /**
     * Remove a proxied interface.
     * <p>Does nothing if the given interface isn't proxied.
     * @param intf the interface to remove from the proxy
     * @return {@code true} if the interface was removed; {@code false}
     * if the interface was not found and hence could not be removed
     */
    public boolean removeInterface(Class<?> intf) {
        return this.interfaces.remove(intf);
    }

    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(new Class<?>[this.interfaces.size()]);
    }

    public boolean isInterfaceProxied(Class<?> intf) {
        for (Class<?> proxyIntf : this.interfaces) {
            if (intf.isAssignableFrom(proxyIntf)) {
                return true;
            }
        }
        return false;
    }

    public Advisor[] getAdvisors(){
        return this.advisors.toArray(new Advisor[this.advisors.size()]);
    }

    /**
     * 在advisors末尾插入切面
     */
    public void addAdvisor(Advisor advisor){
        int pos = this.advisors.size();
        this.addAdvisor(pos, advisor);
    }

    /**
     * 在advisors指定位置插入切面
     */
    public void addAdvisor(int pos, Advisor advisor){
        if (advisor instanceof IntroductionAdvisor) {
            validateIntroductionAdvisor((IntroductionAdvisor) advisor);
        }
        this.advisors.add(pos, advisor);
        this.methodCache.clear();
    }

    public void addAdvice(Advice advice){
        if (advice instanceof IntroductionInfo) {
            // We don't need an IntroductionAdvisor for this kind of introduction:
            // It's fully self-describing.
            addAdvisor( new DefaultIntroductionAdvisor(advice, (IntroductionInfo) advice));
        }else {
            addAdvisor(new DefaultPointcutAdvisor(advice));
        }
    }

    private void validateIntroductionAdvisor(IntroductionAdvisor advisor) {
        advisor.validateInterfaces();
        // If the advisor passed validation, we can make the change.
        Class<?>[] ifcs = advisor.getInterfaces();
        for (Class<?> ifc : ifcs) {
            addInterface(ifc);
        }
    }
}
