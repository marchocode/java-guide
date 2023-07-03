package com.github.marchocode;

public class JdkAgentSmsInterfaceImpl implements JdkAgentSmsInterface {

    @Override
    public void message() {
        System.out.println("JdkAgentSmsInterfaceImpl is sending message.");
    }

    @Override
    public boolean testNetwork() {
        System.out.println("JdkAgentSmsInterfaceImpl is testing network.");
        return true;
    }
}
