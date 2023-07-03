package com.github.marchocode;

public class StaticAgentMain {

    public static void main(String[] args) {

        StaticAgentSmsInterface smsInterface = new StaticAgentSmsInterfaceImpl();
        StaticAgent proxy = new StaticAgent(smsInterface);

        proxy.sendMessage();
    }

}
