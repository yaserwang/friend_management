package com.sp.controller;

import com.sp.pojo.AddFriendResponse;
import com.sp.pojo.FriendRequest;
import com.sp.pojo.GetFriendResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class FriendController {

    private Map<String, Set<String>> friends = new HashMap<>();

    @PostMapping(value = "/add", headers = "Accept=application/json", produces = "application/json")
    public AddFriendResponse addFriend(@RequestBody FriendRequest request){
        friends.computeIfAbsent(request.getFriends().get(0), k -> new HashSet<>()).add(request.getFriends().get(1));
        friends.computeIfAbsent(request.getFriends().get(1), k -> new HashSet<>()).add(request.getFriends().get(0));
        return new AddFriendResponse(true);
    }

    @PostMapping(value = "/get")
    public GetFriendResponse getFriend(@RequestBody String email){
        return new GetFriendResponse(true, friends.get(email), friends.get(email).size());
    }

    @PostMapping(value = "/getCommon")
    public GetFriendResponse getCommonFriend(@RequestBody FriendRequest request){
        Set<String> a = friends.get(request.getFriends().get(0));
        Set<String> b = friends.get(request.getFriends().get(1));
        Set<String> common = new HashSet<>(a);
        common.retainAll(b);
        return new GetFriendResponse(true, common, common.size());
    }

}
