# AMAP_legacy_client_example

This is just a sample project written in old technology (Java w/swing) to demonstrate how to send and receive messages from an Azure Service Bus from legacy applications.

You will need to define a "config.properties" file under src/resources/ (create resource folder is neded as well), which contains the keys:

* bus.connectionString  -> The endpoint in azure
* bus.subscription -> Your subscription
* bus.topic -> The topic to send/receive from
* bus.trajectoryId -> (Sort of example-specific, but can be any string)
* bus.trajectoryLength -> (Same as above, can be any string)

Other dependencies are gradle related, and might be specific for you.

Once application is running, just hit the "Start receiving" button in the application, and then spam "Send trajectory" as much as you would like to send messages to the service hub, and receive the messages back.
