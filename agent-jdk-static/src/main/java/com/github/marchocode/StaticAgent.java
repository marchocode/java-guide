package com.github.marchocode;

public class StaticAgent implements StaticAgentSmsInterface {

    private final StaticAgentSmsInterface smsInterface;

    public StaticAgent(StaticAgentSmsInterface staticAgentSmsInterface) {
        this.smsInterface = staticAgentSmsInterface;
    }

    @Override
    public void sendMessage() {
        // before.
        System.out.println("Check xxx.");

        // call the function.
        this.smsInterface.sendMessage();

        // after.
        System.out.println("Clean some...");
    }
}
