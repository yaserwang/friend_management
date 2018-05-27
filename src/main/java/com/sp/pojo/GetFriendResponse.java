package com.sp.pojo;

import java.util.Objects;
import java.util.Set;

public class GetFriendResponse {

    private boolean success;
    private Set<String> friends;
    private int count;

    private GetFriendResponse() { }

    public GetFriendResponse(boolean success, Set<String> friends, int count) {
        this.success = success;
        this.friends = friends;
        this.count = count;
    }

    public boolean isSuccess() {
        return success;
    }

    public Set<String> getFriends() {
        return friends;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetFriendResponse that = (GetFriendResponse) o;
        return success == that.success &&
                count == that.count &&
                Objects.equals(friends, that.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, friends, count);
    }

    @Override
    public String toString() {
        return "GetFriendResponse{" +
                "success=" + success +
                ", friends=" + friends +
                ", count=" + count +
                '}';
    }
}
