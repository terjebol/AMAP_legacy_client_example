package com.equinor.servicebustest;

import java.util.Properties;

public class ServiceBusConfig {

    public String connectionString;
    public String subscription;
    public String topic;
    public String trajectoryId;
    public String trajectoryLength;

    public ServiceBusConfig() {
    }

    public static ServiceBusConfig fromProperties(Properties properties) {
        ServiceBusConfig config = new ServiceBusConfig();
        config.connectionString = properties.getProperty("bus.connectionString");
        config.subscription = properties.getProperty("bus.subscription");
        config.topic = properties.getProperty("bus.topic");
        config.trajectoryId = properties.getProperty("bus.trajectoryId");
        config.trajectoryLength = properties.getProperty("bus.trajectoryLength");
        return config;
    }
}
