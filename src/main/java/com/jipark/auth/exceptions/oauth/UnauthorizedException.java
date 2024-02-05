package com.jipark.auth.exceptions.oauth;

import com.jipark.auth.exceptions.IRendererException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends Exception implements IRendererException {
    public UnauthorizedException(String message) {
        super(message);
    }
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
