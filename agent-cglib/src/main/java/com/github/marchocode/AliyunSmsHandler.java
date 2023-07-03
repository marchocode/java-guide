package com.github.marchocode;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AliyunSmsHandler implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        System.out.println("method=" + method);
        System.out.println("methodProxy=" + methodProxy);

        System.out.println("before.");

        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("after.");

        return result;
    }
}
