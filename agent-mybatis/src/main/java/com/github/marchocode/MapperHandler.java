package com.github.marchocode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("proxy class=" + proxy.getClass());
        System.out.println("Method: " + method);
        System.out.println("Method return value=" + method.getReturnType());

        System.out.println("Have selected.");

        return null;
    }
}
