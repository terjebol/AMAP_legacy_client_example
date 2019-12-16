package com.equinor.servicebustest.messages;

public class WellTrajectoryChangedIntegrationEvent_Handler extends DefaultMessageHandler {
    @Override
    public String friendlyName() {
        return "WellTrajectory Changed";
    }
}
