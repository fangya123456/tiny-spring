package com.fy.example.spring.aop.support;

import com.fy.example.spring.aop.DynamicIntroductionAdvice;
import com.fy.example.spring.aop.IntroductionInfo;
import com.fy.example.spring.aop.IntroductionInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ya.fang
 * @date 2017/12/1
 */
public class DelegatingIntroductionInterceptor implements IntroductionInfo,IntroductionInterceptor{

    private Object delegate;

    private Set<Class<?>> publishedInterfaces = new LinkedHashSet<Class<?>>();

    private transient Map<Method, Boolean> rememberedMethods = new ConcurrentHashMap<Method, Boolean>(32);


    /**
     * Construct a new DelegatingIntroductionInterceptor, providing
     * a delegate that implements the interfaces to be introduced.
     * @param delegate the delegate that implements the introduced interfaces
     */
    public DelegatingIntroductionInterceptor(Object delegate) {
        init(delegate);
    }

    /**
     * Construct a new DelegatingIntroductionInterceptor.
     * The delegate will be the subclass, which must implement
     * additional interfaces.
     */
    protected DelegatingIntroductionInterceptor() {
        init(this);
    }


    /**
     * Both constructors use this init method, as it is impossible to pass
     * a "this" reference from one constructor to another.
     * @param delegate the delegate object
     */
    private void init(Object delegate) {
        this.delegate = delegate;
        implementInterfacesOnObject(delegate);

        // We don't want to expose the control interface
        suppressInterface(IntroductionInterceptor.class);
        suppressInterface(DynamicIntroductionAdvice.class);
    }


    /**
     * Subclasses may need to override this if they want to perform custom
     * behaviour in around advice. However, subclasses should invoke this
     * method, which handles introduced interfaces and forwarding to the target.
     */
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (isMethodOnIntroducedInterface(mi)) {
            Object retVal = mi.getMethod().invoke(this.delegate, mi.getArguments());
            return retVal;
        }
        return doProceed(mi);
    }

    /**
     * Proceed with the supplied {@link org.aopalliance.intercept.MethodInterceptor}.
     * Subclasses can override this method to intercept method invocations on the
     * target object which is useful when an introduction needs to monitor the object
     * that it is introduced into. This method is <strong>never</strong> called for
     * {@link MethodInvocation MethodInvocations} on the introduced interfaces.
     */
    protected Object doProceed(MethodInvocation mi) throws Throwable {
        // If we get here, just pass the invocation on.
        return mi.proceed();
    }



    /**
     * 排除特定的接口
     */
    public void suppressInterface(Class<?> intf) {
        this.publishedInterfaces.remove(intf);
    }

    @Override
    public Class<?>[] getInterfaces() {
        return this.publishedInterfaces.toArray(new Class<?>[this.publishedInterfaces.size()]);
    }

    /**
     * 校验给定接口是否为公开的引入增强接口
     * */
    @Override
    public boolean implementsInterface(Class<?> intf) {
        for (Class<?> pubIfc : this.publishedInterfaces) {
            if (intf.isInterface() && intf.isAssignableFrom(pubIfc)) {
                return true;
            }
        }
        return false;
    }

    protected void implementInterfacesOnObject(Object delegate) {
        this.publishedInterfaces.addAll(ClassUtils.getAllInterfacesAsSet(delegate));
    }

    /**
     * Is this method on an introduced interface?
     * @param mi the method invocation
     * @return whether the invoked method is on an introduced interface
     */
    protected final boolean isMethodOnIntroducedInterface(MethodInvocation mi) {
        Boolean rememberedResult = this.rememberedMethods.get(mi.getMethod());
        if (rememberedResult != null) {
            return rememberedResult;
        }
        else {
            // Work it out and cache it.
            boolean result = implementsInterface(mi.getMethod().getDeclaringClass());
            this.rememberedMethods.put(mi.getMethod(), result);
            return result;
        }
    }
}
