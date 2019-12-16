package com.equinor.servicebustest;

import com.equinor.servicebustest.messages.MessagePackage;
import com.equinor.servicebustest.util.GSON;
import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.TopicClient;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ServiceBusClient {

    private TopicClient sendClient;
    public ServiceBusClient(ServiceBusConfig serviceBusConfig) throws ServiceBusException, InterruptedException {

        String connectionString = serviceBusConfig.connectionString;
        sendClient = new TopicClient(new ConnectionStringBuilder(connectionString, serviceBusConfig.topic));
    }

    public void sendMessage(ServiceBusConfig serviceBusConfig) {
        sendMessagesAsync(serviceBusConfig).thenRunAsync(() -> sendClient.closeAsync());
    }
    private CompletableFuture<Void> sendMessagesAsync(ServiceBusConfig serviceBusConfig) {

          HashMap<String,String> obj = new HashMap<>() {{
             put("TrajectoryId", serviceBusConfig.trajectoryId);
             put("Length", serviceBusConfig.trajectoryLength);
          }};


        final String messageId = "javaId";
        var messagePackage = new MessagePackage<>(obj);
        messagePackage.Id = "FromJava";
        messagePackage.Timestamp = DateTime.now().toDate();

        var message = new Message(GSON.get().toJson(messagePackage, MessagePackage.class).getBytes(UTF_8));
        message.setLabel("WellTrajectoryCreatedIntegrationEvent");
        message.setMessageId(messageId);
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(
                sendClient.sendAsync(message).thenRunAsync(() -> {
                    System.out.printf("\tMessage acknowledged: Id = %s\n", message.getMessageId());
                }));
        return CompletableFuture.allOf(tasks.toArray(new CompletableFuture<?>[tasks.size()]));
    }


}
