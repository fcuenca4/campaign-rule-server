package com.decider.exception;

public class PublishException extends Exception {
    public PublishException(String publish_error) {
        super(publish_error);
    }
}
