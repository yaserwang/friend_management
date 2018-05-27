package com.sp.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublishResponse {

    private boolean success;
    private Set<String> recipients;
    private String message;

    private PublishResponse() { }

    public PublishResponse(boolean success, Set<String> recipients) {
        this(success, recipients, null);
    }

    public PublishResponse(boolean success, String message) {
        this(success, null, message);
    }

    public PublishResponse(boolean success, Set<String> recipients, String message) {
        this.success = success;
        this.recipients = recipients;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Set<String> getRecipients() {
        return recipients;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublishResponse that = (PublishResponse) o;
        return success == that.success &&
                Objects.equals(recipients, that.recipients) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, recipients, message);
    }

    @Override
    public String toString() {
        return "PublishResponse{" +
                "success=" + success +
                ", recipients=" + recipients +
                ", message='" + message + '\'' +
                '}';
    }
}
