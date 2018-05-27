package com.sp.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private boolean success;
    private String message;

    private Response() {
    }

    public Response(boolean success) {
        this(success, null);
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return success == response.success &&
                Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(success, message);
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
