package com.equinor.servicebustest.messages;

public class ApproveDWAssessmentCreatedIntegrationEvent_Handler extends DefaultMessageHandler {
    @Override
    public String friendlyName() {
        return "WellTrajectory Approved";
    }
}
