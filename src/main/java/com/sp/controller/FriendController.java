package com.sp.controller;

import com.sp.pojo.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    private Set<String> users = new HashSet<>();
    {
        users.add("andy@example.com");
        users.add("john@example.com");
        users.add("common@example.com");
        users.add("lisa@example.com");
        users.add("kate@example.com");
    }

    private Map<String, Set<String>> friends = new HashMap<>();
    private Map<String, Set<String>> subscribe = new HashMap<>();
    private Map<String, Set<String>> blocks = new HashMap<>();

    @PostMapping(value = "/addUser", headers = "Accept=application/json", produces = "application/json")
    public Response addUser(@RequestBody String email){
        if(isNotValidUser(email)) {
            users.add(email);
            return new Response(true);
        } else return new Response(false, email + " already exists");
    }

    @PostMapping(value = "/addFriend", headers = "Accept=application/json", produces = "application/json")
    public Response addFriend(@RequestBody FriendRequest request){
        if(request.getFriends().size()!=2){
            return new Response(false,  "only two users are allowed in the request to add friend");
        }
        String requestor = request.getFriends().get(0);
        String target = request.getFriends().get(1);
        if(isNotValidUser(requestor)){
            return new Response(false, requestor + " is not a valid user");
        }
        if(isNotValidUser(target)){
            return new Response(false, target + " is not a valid user");
        }
        if (isBlocked(requestor, target)) {
            return new Response(false, target + " is blocked by " + requestor);
        } else if (isBlocked(target, requestor)) {
            return new Response(false, requestor + " is blocked by " + target);
        } else {
            friends.computeIfAbsent(requestor, k -> new HashSet<>()).add(target);
            friends.computeIfAbsent(target, k -> new HashSet<>()).add(requestor);
            return new Response(true);
        }
    }

    private boolean isNotValidUser(String user) {
        return !users.contains(user);
    }

    private boolean isBlocked(String requestor, String target){
        return blocks.get(requestor) != null && blocks.get(requestor).contains(target);
    }

    @PostMapping(value = "/getFriend", headers = "Accept=application/json", produces = "application/json")
    public GetFriendResponse getFriend(@RequestBody GetFriendRequest request){
        String email = request.getEmail();
        if(isNotValidUser(email)){
            return new GetFriendResponse(false, email + " is not a valid user");
        }
        return new GetFriendResponse(true, friends.getOrDefault(email, new HashSet<>()));
    }

    @PostMapping(value = "/getCommon", headers = "Accept=application/json", produces = "application/json")
    public GetFriendResponse getCommonFriend(@RequestBody FriendRequest request){
        if(request.getFriends().size()!=2){
            return new GetFriendResponse(false,  "only two users are allowed in the request to get common friend");
        }
        String a = request.getFriends().get(0);
        String b = request.getFriends().get(1);
        if(isNotValidUser(a)){
            return new GetFriendResponse(false, a + " is not a valid user");
        }
        if(isNotValidUser(b)){
            return new GetFriendResponse(false, b + " is not a valid user");
        }
        Set<String> af = friends.get(a);
        Set<String> bf = friends.get(b);
        Set<String> common = new HashSet<>(af);
        common.retainAll(bf);
        return new GetFriendResponse(true, common);
    }

    @PostMapping(value = "/subscribe", headers = "Accept=application/json", produces = "application/json")
    public Response subscribe(@RequestBody SubscribeRequest request){
        if(isNotValidUser(request.getRequestor())){
            return new Response(false, request.getRequestor() + " is not a valid user");
        }
        if(isNotValidUser(request.getTarget())){
            return new Response(false, request.getTarget() + " is not a valid user");
        }
        subscribe.computeIfAbsent(request.getRequestor(), k -> new HashSet<>()).add(request.getTarget());
        return new Response(true);
    }

    @PostMapping(value = "/block", headers = "Accept=application/json", produces = "application/json")
    public Response block(@RequestBody SubscribeRequest request){
        if(isNotValidUser(request.getRequestor())){
            return new Response(false, request.getRequestor() + " is not a valid user");
        }
        if(isNotValidUser(request.getTarget())){
            return new Response(false, request.getTarget() + " is not a valid user");
        }
        blocks.computeIfAbsent(request.getRequestor(), k-> new HashSet<>()).add(request.getTarget());
        return new Response(true);
    }

    @PostMapping(value = "/publish", headers = "Accept=application/json", produces = "application/json")
    public PublishResponse publish(@RequestBody PublishRequest request){
        if(isNotValidUser(request.getSender())){
            return new PublishResponse(false, request.getSender() + " is not a valid user");
        }
        Set<String> friendList = friends.getOrDefault(request.getSender(), new HashSet<>());
        Set<String> recipients = new HashSet<>(friendList);
        Set<String> subscribeList = subscribe.getOrDefault(request.getSender(), new HashSet<>());
        recipients.addAll(subscribeList);
        Set<String> mentioned = getMentioned(request.getText());
        if(mentioned!=null && !mentioned.isEmpty()) {
            recipients.addAll(mentioned);
        }
        Set<String> blockList = blocks.getOrDefault(request.getSender(), new HashSet<>());
        recipients.removeAll(blockList);
        return new PublishResponse(true, recipients);
    }

    private Set<String> getMentioned(String text){
        return users.stream().filter(text::contains).collect(Collectors.toSet());
    }
}
