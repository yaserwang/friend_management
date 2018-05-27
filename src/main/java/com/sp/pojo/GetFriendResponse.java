package com.sp.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetFriendResponse {

    private boolean success;
    private Set<String> friends;
    private Integer count;
    private String message;

    private GetFriendResponse() { }

    public GetFriendResponse(boolean success, Set<String> friends) {
        this(success, friends, null);
    }

    public GetFriendResponse(boolean success, String message){
        this(success, null, message);
    }

    public GetFriendResponse(boolean success, Set<String> friends, String message) {
        this.success = success;
        this.friends = friends;
        this.count = friends == null?null:friends.size();
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Set<String> getFriends() {
        return friends;
    }

    public Integer getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetFriendResponse that = (GetFriendResponse) o;
        return success == that.success &&
                Objects.equals(friends, that.friends) &&
                Objects.equals(count, that.count) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, friends, count, message);
    }

    @Override
    public String toString() {
        return "GetFriendResponse{" +
                "success=" + success +
                ", friends=" + friends +
                ", count=" + count +
                ", message='" + message + '\'' +
                '}';
    }
}
