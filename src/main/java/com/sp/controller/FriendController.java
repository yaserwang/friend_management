package com.sp.controller;

import com.sp.pojo.AddFriendRequest;
import com.sp.pojo.AddFriendResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

    @RequestMapping(value = "/add", headers = "Accept=application/json", produces = "application/json")
    public AddFriendResponse addFriend(@RequestBody AddFriendRequest request){
        return new AddFriendResponse(true);
    }
}
