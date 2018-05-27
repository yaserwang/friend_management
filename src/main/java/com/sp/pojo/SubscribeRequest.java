package com.sp.pojo;

public class SubscribeRequest {

    private String requestor;
    private String target;

    private SubscribeRequest() {
    }

    public SubscribeRequest(String requestor, String target) {
        this.requestor = requestor;
        this.target = target;
    }

    public String getRequestor() {
        return requestor;
    }

    public String getTarget() {
        return target;
    }
}
