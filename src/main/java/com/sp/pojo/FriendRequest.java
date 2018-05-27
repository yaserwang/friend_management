package com.sp.pojo;

import java.util.List;

public class FriendRequest {

    private List<String> friends;

    private FriendRequest() { }

    public FriendRequest(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriends() {
        return friends;
    }
}
