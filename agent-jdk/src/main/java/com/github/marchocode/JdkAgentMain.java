package com.github.marchocode;

import java.lang.reflect.Proxy;

public class JdkAgentMain {

    public static void main(String[] args) {

        Object target = new JdkAgentSmsInterfaceImpl();

        JdkAgentSmsInterface proxyObject = (JdkAgentSmsInterface) Proxy.newProxyInstance(
                JdkAgentMain.class.getClassLoader(),
                new Class[]{JdkAgentSmsInterface.class},
                new JdkAgentSmsHandler(target)
        );

        boolean result = proxyObject.testNetwork();
        System.out.println(result);

//        proxyObject.message();
    }

}
