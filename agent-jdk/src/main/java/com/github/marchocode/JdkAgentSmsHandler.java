package com.github.marchocode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkAgentSmsHandler implements InvocationHandler {

    private final Object target;

    public JdkAgentSmsHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("proxy method=" + method);
        System.out.println("proxy before.");

        // call the function.
        Object result = method.invoke(target, args);
        System.out.println("result=" + result);

        // after.
        System.out.println("proxy after.");

        return result;
    }
}
