package com.jipark.auth.http.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class Response {
    private final int code;
    private final String message;

    protected Response() {
        this(200, "success");
    }
}
