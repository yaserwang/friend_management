package com.sp.pojo;

import java.util.Objects;
import java.util.Set;

public class PublishResponse {

    private boolean success;
    private Set<String> recipients;

    private PublishResponse() { }

    public PublishResponse(boolean success, Set<String> recipients) {
        this.success = success;
        this.recipients = recipients;
    }

    public boolean isSuccess() {
        return success;
    }

    public Set<String> getRecipients() {
        return recipients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublishResponse that = (PublishResponse) o;
        return success == that.success &&
                Objects.equals(recipients, that.recipients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, recipients);
    }

    @Override
    public String toString() {
        return "PublishResponse{" +
                "success=" + success +
                ", recipients=" + recipients +
                '}';
    }
}
