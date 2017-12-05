package com.fy.example.spring.aop.framework;

import com.fy.example.spring.aop.SpringNamingPolicy;
import com.fy.example.spring.aop.TargetSource;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * 类的代理
 * @author ya.fang
 * @date 2017/11/29
 */
public class CglibAopProxy implements AopProxy, Serializable {


    protected final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config){
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        //目标对象的源类
        Class<?> rootClass = this.advised.getTargetSource().getTargetClass();

        //设置代理的类，这里如果代理类为CglibProxyClass,需要作处理
        Class<?> proxySuperClass = rootClass;

        //配置 Cglib Enhancer
        Enhancer enhancer = new Enhancer();
        if (classLoader != null){
            enhancer.setClassLoader(classLoader);
            //这里需要判断classLoader是否是重写的classLoader，若是则设置Enhancer的useCache属性为false
            /*if (classLoader instanceof SmartClassLoader &&
                    ((SmartClassLoader) classLoader).isClassReloadable(proxySuperClass)) {
                enhancer.setUseCache(false);
            }*/
        }

        //设置被代理的目标类
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setInterfaces(advised.getProxiedInterfaces());
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);

        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        Object enhanced = enhancer.create();
        return enhanced;
    }

    private static class  DynamicAdvisedInterceptor implements MethodInterceptor,Serializable{

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised){
            this.advised = advised;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            TargetSource targetSource = this.advised.getTargetSource();
            Object target = targetSource.getTarget();
            Class<?> targetClass = (target != null? target.getClass() : null);
            Object retVal;

            /**此处的MethodInterceptor 非Cglib的，是对MethodInvocation做了一次封装封装*/
            List<Object> chain= this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,targetClass);
            if (chain.isEmpty()){
                retVal = method.invoke(target, args);
            }else {

                retVal = new CglibMethodInvocation(proxy, target, method,args,chain,methodProxy).proceed();
            }
            retVal = processReturnType(proxy, target, method, retVal);
            return retVal;
        }
    }

    private static class  CglibMethodInvocation extends ReflectiveMethodInvocation{
        private final MethodProxy methodProxy;

        public final boolean publicMethod;

        public CglibMethodInvocation(Object proxy, Object target, Method method,
                                     Object[] arguments,
                                     List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {

            super(proxy, target, method, arguments, interceptorsAndDynamicMethodMatchers);
            this.methodProxy = methodProxy;
            this.publicMethod = Modifier.isPublic(method.getModifiers());
        }

        @Override
        protected Object invokeJoinpoint() throws Throwable {
            if (this.publicMethod) {
                return this.methodProxy.invoke(this.target, this.args);
            }
            else {
                return super.invokeJoinpoint();
            }
        }
    }

    private static Object processReturnType(
            Object proxy,Object target, Method method, Object returnValue) {

        // Massage return value if necessary
        if (returnValue != null && returnValue == target) {
            // Special case: it returned "this". Note that we can't help
            // if the target sets a reference to itself in another returned object.
            returnValue = proxy;
        }
        Class<?> returnType = method.getReturnType();
        if (returnValue == null && returnType != Void.TYPE && returnType.isPrimitive()) {
            throw new RuntimeException(
                    "Null return value from advice does not match primitive return type for: " + method);
        }
        return returnValue;
    }
}
