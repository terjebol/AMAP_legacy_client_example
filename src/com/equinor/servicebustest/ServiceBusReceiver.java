package com.equinor.servicebustest;

import com.equinor.servicebustest.messages.MessageHandlerFactory;
import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;

import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class ServiceBusReceiver {

    private final MessageReceiver receiver;

    public ServiceBusReceiver(ServiceBusConfig config) throws ServiceBusException, InterruptedException {
        receiver = createSubscriptionClient(config);
    }

    public void startReceiving() throws ServiceBusException, InterruptedException {
        receiver.register();
    }

    public MessageReceiver createSubscriptionClient(ServiceBusConfig config) throws ServiceBusException, InterruptedException {
        return new MessageReceiver(
                new  SubscriptionClient(new ConnectionStringBuilder(config.connectionString, config.topic+"/subscriptions/"+config.subscription),ReceiveMode.PEEKLOCK));
    }

    private static class MessageReceiver {
        private SubscriptionClient client;

        public MessageReceiver(SubscriptionClient client) {
            this.client = client;
        }

        public void register() throws ServiceBusException, InterruptedException {
            IMessageHandler messageHandler = new IMessageHandler() {
                @Override
                public CompletableFuture<Void> onMessageAsync(IMessage message) {
                    System.out.println(message.getLabel());
                    EventQueue.invokeLater(() -> MessageHandlerFactory.findHandler(message).handle(message));
                    return client.completeAsync(message.getLockToken());
                }

                @Override
                public void notifyException(Throwable exception, ExceptionPhase phase) {
                    System.err.println(phase + " - " + exception.getMessage());
                }
            };
            client.registerMessageHandler(messageHandler, Executors.newSingleThreadExecutor());
        }
    }
}
