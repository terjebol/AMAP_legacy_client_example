package com.equinor.servicebustest.messages;

public class UnknownMessageHandler extends DefaultMessageHandler {

    @Override
    public String friendlyName() {
        return messageLabel();
    }
}
