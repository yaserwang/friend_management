package com.sp.controller;

import com.sp.pojo.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FriendControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test0_getFriends_beforeAddFriend() {
        Set<String> returned = new HashSet<>();
        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getFriend", new GetFriendRequest("andy@example.com"), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(true, returned)));
    }

    @Test
    public void test1_addFriend() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("john@example.com");
        Response body = this.restTemplate.postForObject("/addFriend", new FriendRequest(friends), Response.class);
        assertThat(body, is(new Response(true)));
    }

    @Test
    public void test1_addFriend_withInvalidUser1() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("abc@example.com");
        Response body = this.restTemplate.postForObject("/addFriend", new FriendRequest(friends), Response.class);
        assertThat(body, is(new Response(false,"abc@example.com is not a valid user")));
    }

    @Test
    public void test1_addFriend_withInvalidUser2() {
        List<String> friends = new ArrayList<>();
        friends.add("abc@example.com");
        friends.add("john@example.com");
        Response body = this.restTemplate.postForObject("/addFriend", new FriendRequest(friends), Response.class);
        assertThat(body, is(new Response(false,"abc@example.com is not a valid user")));
    }

    @Test
    public void test2_getFriends() {
        Set<String> returned = new HashSet<>();
        returned.add("john@example.com");

        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getFriend", new GetFriendRequest("andy@example.com"), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(true, returned)));
    }

    @Test
    public void test2_getFriends_withInvalidUser() {
        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getFriend", new GetFriendRequest("abc@example.com"), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(false, "abc@example.com is not a valid user")));
    }

    @Test
    public void test3_getCommonFriends1() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("john@example.com");

        Set<String> returned = new HashSet<>();
        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getCommon", new FriendRequest(friends), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(true, returned)));

    }

    @Test
    public void test3_getCommonFriends2() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("common@example.com");
        Response addFriendResponseBody = this.restTemplate.postForObject("/addFriend", new FriendRequest(friends), Response.class);
        assertThat(addFriendResponseBody, is(new Response(true)));

        friends = new ArrayList<>();
        friends.add("common@example.com");
        friends.add("john@example.com");
        addFriendResponseBody = this.restTemplate.postForObject("/addFriend", new FriendRequest(friends), Response.class);
        assertThat(addFriendResponseBody, is(new Response(true)));

        friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("john@example.com");

        Set<String> returned = new HashSet<>();
        returned.add("common@example.com");
        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getCommon", new FriendRequest(friends), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(true, returned)));

    }

    @Test
    public void test3_getCommonFriends_withInvalidUser1() {
        List<String> friends = new ArrayList<>();
        friends.add("abc@example.com");
        friends.add("john@example.com");

        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getCommon", new FriendRequest(friends), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(false, "abc@example.com is not a valid user")));
    }

    @Test
    public void test3_getCommonFriends_withInvalidUser2() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("abc@example.com");

        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getCommon", new FriendRequest(friends), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(false, "abc@example.com is not a valid user")));
    }

    @Test
    public void test4_subscribe(){
        Response body = this.restTemplate.postForObject("/subscribe", new SubscribeRequest("lisa@example.com", "john@example.com"), Response.class);
        assertThat(body, is(new Response(true)));
    }

    @Test
    public void test4_subscribe_withInvalidUser1(){
        Response body = this.restTemplate.postForObject("/subscribe", new SubscribeRequest("abc@example.com", "john@example.com"), Response.class);
        assertThat(body, is(new Response(false, "abc@example.com is not a valid user")));
    }

    @Test
    public void test4_subscribe_withInvalidUser2(){
        Response body = this.restTemplate.postForObject("/subscribe", new SubscribeRequest("lisa@example.com", "abc@example.com"), Response.class);
        assertThat(body, is(new Response(false, "abc@example.com is not a valid user")));
    }

    @Test
    public void test5_block(){
        Response body = this.restTemplate.postForObject("/block", new SubscribeRequest("lisa@example.com", "john@example.com"), Response.class);
        assertThat(body, is(new Response(true)));

        List<String> friends = new ArrayList<>();
        friends.add("lisa@example.com");
        friends.add("john@example.com");
        body = this.restTemplate.postForObject("/addFriend", new FriendRequest(friends), Response.class);
        assertThat(body, is(new Response(false, "john@example.com is blocked by lisa@example.com")));
    }

    @Test
    public void test5_block_withInvalidUser1(){
        Response body = this.restTemplate.postForObject("/block", new SubscribeRequest("abc@example.com", "john@example.com"), Response.class);
        assertThat(body, is(new Response(false, "abc@example.com is not a valid user")));
    }

    @Test
    public void test5_block_withInvalidUser2(){
        Response body = this.restTemplate.postForObject("/block", new SubscribeRequest("lisa@example.com", "abc@example.com"), Response.class);
        assertThat(body, is(new Response(false, "abc@example.com is not a valid user")));
    }

    @Test
    public void test6_publish(){
        PublishResponse body = this.restTemplate.postForObject("/publish", new PublishRequest("john@example.com", "Hello World! kate@example.com"), PublishResponse.class);
        Set<String> recipients = new HashSet<>();
        recipients.add("common@example.com");
        recipients.add("andy@example.com");
        recipients.add("kate@example.com");
        assertThat(body, is(new PublishResponse(true, recipients)));
    }

    @Test
    public void test6_publish_withInvalidUser(){
        PublishResponse body = this.restTemplate.postForObject("/publish", new PublishRequest("abc@example.com", "Hello World! kate@example.com"), PublishResponse.class);
        assertThat(body, is(new PublishResponse(false, "abc@example.com is not a valid user")));
    }

    @Test
    public void test7_addUser(){
        Response body = this.restTemplate.postForObject("/addUser", "lisa2@example.com", Response.class);
        assertThat(body, is(new Response(true)));

        body = this.restTemplate.postForObject("/addUser", "lisa2@example.com", Response.class);
        assertThat(body, is(new Response(false, "lisa2@example.com already exists")));
    }
}