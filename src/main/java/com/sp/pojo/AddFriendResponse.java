package com.sp.pojo;

import java.util.Objects;

public class AddFriendResponse {

    private boolean success;

    public AddFriendResponse() {
    }

    public AddFriendResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddFriendResponse that = (AddFriendResponse) o;
        return success == that.success;
    }

    @Override
    public int hashCode() {
        return Objects.hash(success);
    }
}
