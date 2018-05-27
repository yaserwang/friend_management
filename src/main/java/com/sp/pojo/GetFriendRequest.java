package com.sp.pojo;


public class GetFriendRequest {

    private String email;

    private GetFriendRequest() {
    }

    public GetFriendRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
