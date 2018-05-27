package com.sp.pojo;


import java.util.List;

public class GetCommonFriendRequest {
    private List<String> friends;

    private GetCommonFriendRequest() { }

    public GetCommonFriendRequest(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriends() {
        return friends;
    }
}
