package com.sp.pojo;

import java.util.List;

public class AddFriendRequest {

    private List<String> friends;

    private AddFriendRequest() { }

    public AddFriendRequest(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriends() {
        return friends;
    }
}
