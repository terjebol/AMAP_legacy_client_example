package com.equinor.servicebustest.messages;

public class RejectDWAssessmentCreatedIntegrationEvent_Handler extends DefaultMessageHandler {
    @Override
    public String friendlyName() {
        return "WellTrajectory Rejected";
    }
}
