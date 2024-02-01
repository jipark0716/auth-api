package com.jipark.auth.exceptions;

import org.springframework.http.HttpStatus;

public interface IRendererException {
    default HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    default Object getDetail() {
        return null;
    }
}
