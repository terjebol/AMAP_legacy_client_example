package com.equinor.servicebustest;

import com.equinor.servicebustest.ui.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Properties properties = new Properties();
            try(InputStream is = Main.class.getResourceAsStream("/config.properties")) {
                if(is != null) {
                    properties.load(is);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            MainFrame frame = new MainFrame(ServiceBusConfig.fromProperties(properties));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.pack();
        });
    }
}
