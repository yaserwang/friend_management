package com.sp.controller;

import com.sp.pojo.AddFriendRequest;
import com.sp.pojo.AddFriendResponse;
import com.sp.pojo.GetCommonFriendRequest;
import com.sp.pojo.GetFriendResponse;
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
        AddFriendResponse body = this.restTemplate.postForObject("/add", new AddFriendRequest(friends), AddFriendResponse.class);
        assertThat(body, is(new AddFriendResponse(true)));
    }

    @Test
    public void getFriends() {
        List<String> friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("john@example.com");
        AddFriendResponse addFriendResponseBody = this.restTemplate.postForObject("/add", new AddFriendRequest(friends), AddFriendResponse.class);
        assertThat(addFriendResponseBody, is(new AddFriendResponse(true)));

        friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("common@example.com");
        addFriendResponseBody = this.restTemplate.postForObject("/add", new AddFriendRequest(friends), AddFriendResponse.class);
        assertThat(addFriendResponseBody, is(new AddFriendResponse(true)));

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
        AddFriendResponse addFriendResponseBody = this.restTemplate.postForObject("/add", new AddFriendRequest(friends), AddFriendResponse.class);
        assertThat(addFriendResponseBody, is(new AddFriendResponse(true)));

        friends = new ArrayList<>();
        friends.add("common@example.com");
        friends.add("john@example.com");
        addFriendResponseBody = this.restTemplate.postForObject("/add", new AddFriendRequest(friends), AddFriendResponse.class);
        assertThat(addFriendResponseBody, is(new AddFriendResponse(true)));

        friends = new ArrayList<>();
        friends.add("andy@example.com");
        friends.add("john@example.com");

        Set<String> returned = new HashSet<>();
        returned.add("common@example.com");
        GetFriendResponse getFriendResponseBody = this.restTemplate.postForObject("/getCommon", new GetCommonFriendRequest(friends), GetFriendResponse.class);
        assertThat(getFriendResponseBody, is(new GetFriendResponse(true, returned, returned.size())));

    }

}