package com.equinor.servicebustest.messages;

import com.equinor.servicebustest.ui.TreeComponent;
import com.equinor.servicebustest.util.GSON;
import com.microsoft.azure.servicebus.IMessage;
import com.microsoft.azure.servicebus.MessageBodyType;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class DefaultMessageHandler implements MessageHandler {
    private String messageLabel;
    @Override
    public void handle(IMessage message) {
        messageLabel = message.getLabel();
        if (message.getMessageBody().getBodyType() == MessageBodyType.BINARY) {
            var binaryData = message.getMessageBody().getBinaryData();
            byte[] byteArray = binaryData.get(0);
            var foo = new String(byteArray, UTF_8);
            var obj = GSON.get().fromJson(foo, MessagePackage.class);
            TreeComponent.getInstance().addEvent(this, obj);
            System.out.println("Added " + message.getLabel() + " to the event log.");
        } else {
            System.err.println("Error: Currently only handling binary messagebodies.");
        }
    }

    public abstract String friendlyName();

    public String messageLabel() {
         return messageLabel;
    }
}

