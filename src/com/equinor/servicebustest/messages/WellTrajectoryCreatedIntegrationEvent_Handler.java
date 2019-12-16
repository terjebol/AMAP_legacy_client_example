package com.equinor.servicebustest.messages;

public class WellTrajectoryCreatedIntegrationEvent_Handler extends DefaultMessageHandler {

    @Override
    public String friendlyName() {
        return "WellTrajectory Created";
    }
}
