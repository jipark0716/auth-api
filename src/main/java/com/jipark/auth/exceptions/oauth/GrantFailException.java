package com.jipark.auth.exceptions.oauth;

import com.jipark.auth.exceptions.IRendererException;
import org.springframework.http.HttpStatus;

public class GrantFailException extends Exception implements IRendererException {
    public GrantFailException(String message, Throwable parent) {
        super(message, parent);
    }
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
