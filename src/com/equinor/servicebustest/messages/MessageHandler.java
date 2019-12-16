package com.equinor.servicebustest.messages;

import com.microsoft.azure.servicebus.IMessage;

public interface MessageHandler {
    void handle(IMessage message);
    String friendlyName();
    String messageLabel();
}
