package com.equinor.servicebustest.messages;

import com.microsoft.azure.servicebus.IMessage;

import java.lang.reflect.InvocationTargetException;

public class MessageHandlerFactory {

    public static MessageHandler findHandler(IMessage message) {
        String label = message.getLabel();
        String className = MessageHandler.class.getPackage().getName()+"."+label+"_Handler";

        try {
            System.out.println("Trying: "+className);
            var cls = Class.forName(className);
            return ((MessageHandler)cls.getConstructor().newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

            System.out.println("Cannot find handler, using Unknown handler");
            return new UnknownMessageHandler();
        }
    }
}
