package com.sp.controller;

import com.sp.pojo.FriendRequest;
import com.sp.pojo.GetFriendResponse;
import com.sp.pojo.Response;
import com.sp.pojo.SubscribeRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FriendControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addFriend() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("john@example.com");
        Response body = this.restTemplate.postForObject("/add", new FriendRequest(friends), Response.class);
        assertThat(body, is(new Response(true)));
    }

    @Test
    public void getFriends() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("john@example.com");
        Response addFriendResponseBody = this.restTemplate.postForObject("/add", new FriendRequest(friends), Response.class);
        assertThat(addFriendResponseBody, is(new Response(true)));

        friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("common@example.com");
        addFriendResponseBody = this.restTemplate.postForObject("/add", new FriendRequest(friends), Response.class);
        assertThat(addFriendResponseBody, is(new Response(true)));

        Set<String> returned = new HashSet<>();
        returned.add("john@example.com");
        returned.add("common@example.com");

        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/get", "andy@example.com", GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(true, returned, returned.size())));
    }

    @Test
    public void getCommonFriends() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("common@example.com");
        Response addFriendResponseBody = this.restTemplate.postForObject("/add", new FriendRequest(friends), Response.class);
        assertThat(addFriendResponseBody, is(new Response(true)));

        friends = new ArrayList<>();
        friends.add("common@example.com");
        friends.add("john@example.com");
        addFriendResponseBody = this.restTemplate.postForObject("/add", new FriendRequest(friends), Response.class);
        assertThat(addFriendResponseBody, is(new Response(true)));

        friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("john@example.com");

        Set<String> returned = new HashSet<>();
        returned.add("common@example.com");
        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getCommon", new FriendRequest(friends), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(true, returned, returned.size())));

    }

    @Test
    public void subscribe(){
        Response body = this.restTemplate.postForObject("/subscribe", new SubscribeRequest("lisa@example.com", "john@example.com"), Response.class);
        assertThat(body, is(new Response(true)));
    }

}