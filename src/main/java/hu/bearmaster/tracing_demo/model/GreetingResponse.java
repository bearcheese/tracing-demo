package hu.bearmaster.tracing_demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.StringJoiner;

public class GreetingResponse {

    private String message;

    @JsonCreator
    public GreetingResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GreetingResponse.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .toString();
    }
}
