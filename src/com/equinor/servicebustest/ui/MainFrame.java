package com.equinor.servicebustest.ui;

import com.equinor.servicebustest.ServiceBusClient;
import com.equinor.servicebustest.ServiceBusConfig;
import com.equinor.servicebustest.ServiceBusReceiver;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame {


    private Border border =  BorderFactory.createEmptyBorder(5, 20, 5, 20);

    public MainFrame(ServiceBusConfig config) {
        super("ServiceBus Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout( new GridLayout(12,1));
        panel.setBorder(border);
        this.getContentPane().setLayout( new BorderLayout());
        setPreferredSize(new Dimension(1024,768));

        JTextField connectionStringField = new JTextField();
        panel.add(new JLabel("Connection string"));
        panel.add(connectionStringField);

        JTextField subscriptionField = new JTextField();
        panel.add(new JLabel("Subscription"));
        panel.add(subscriptionField);

        JTextField topicField = new JTextField();
        panel.add(new JLabel("Topic"));
        panel.add(topicField);

        JTextField trajectoryIdField = new JTextField();
        panel.add(new JLabel("Trajectory ID"));
        panel.add(trajectoryIdField);

        JTextField trajectoryLengthField = new JTextField();
        panel.add(new JLabel("Trajectory Length"));
        panel.add(trajectoryLengthField);

        JButton sendButton = new JButton("Send Trajectory");
        JButton receiveButton = new JButton("Start receiving");

        connectionStringField.setText(config.connectionString);
        subscriptionField.setText(config.subscription);
        topicField.setText(config.topic);
        trajectoryIdField.setText(config.trajectoryId);
        trajectoryLengthField.setText(config.trajectoryLength);

        sendButton.addActionListener(actionEvent -> {
            String connectionString = connectionStringField.getText();
            String subscription = subscriptionField.getText();
            String topic = topicField.getText();
            String wellTrajectoryId = trajectoryIdField.getText();
            String wellTrajectoryLength = trajectoryLengthField.getText();

            try {
                config.connectionString = connectionString;
                config.subscription = subscription;
                config.topic = topic;
                config.trajectoryId = wellTrajectoryId;
                config.trajectoryLength = wellTrajectoryLength;
                ServiceBusClient client = new ServiceBusClient(config);
                client.sendMessage(config);
            } catch (ServiceBusException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        receiveButton.addActionListener(actionEvent -> {
            try {
                ServiceBusReceiver serviceBusReceiver = new ServiceBusReceiver(config);
                serviceBusReceiver.startReceiving();
            } catch (ServiceBusException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        panel.add(sendButton);
        panel.add(receiveButton);

        JScrollPane treeView = new JScrollPane( TreeComponent.create("Events").treeComponent);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                panel, treeView);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(500);
        this.getContentPane().add(splitPane);
    }
}
