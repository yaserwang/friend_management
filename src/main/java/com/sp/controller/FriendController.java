package com.sp.controller;

import com.sp.pojo.FriendRequest;
import com.sp.pojo.GetFriendResponse;
import com.sp.pojo.Response;
import com.sp.pojo.SubscribeRequest;
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
    private Map<String, Set<String>> blocks = new HashMap<>();

    @PostMapping(value = "/add", headers = "Accept=application/json", produces = "application/json")
    public Response addFriend(@RequestBody FriendRequest request){
        String requestor = request.getFriends().get(0);
        String target = request.getFriends().get(1);
        if (isBlocked(requestor, target)) {
            return new Response(false);
        } else if (isBlocked(target, requestor)) {
            return new Response(false);
        } else {
            friends.computeIfAbsent(requestor, k -> new HashSet<>()).add(target);
            friends.computeIfAbsent(target, k -> new HashSet<>()).add(requestor);
            return new Response(true);
        }
    }

    private boolean isBlocked(String requestor, String target){
        return blocks.get(requestor) != null && blocks.get(requestor).contains(target);
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

    @PostMapping(value = "/subscribe")
    public Response subscribe(@RequestBody SubscribeRequest request){
        return new Response(true);
    }

    @PostMapping(value = "/block")
    public Response block(@RequestBody SubscribeRequest request){
        blocks.computeIfAbsent(request.getRequestor(), k-> new HashSet<>()).add(request.getTarget());
        return new Response(true);
    }
}
