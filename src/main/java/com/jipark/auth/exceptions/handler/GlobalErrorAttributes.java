package com.jipark.auth.exceptions.handler;

import com.jipark.auth.exceptions.IRendererException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> result = new HashMap<>();

        Throwable exception = getError(request);
        result.put("message", exception.getMessage());

        if (exception instanceof IRendererException e) {
            Object detail = e.getDetail();
            if (detail != null) {
                result.putIfAbsent("detail", e.getDetail());
            }
        }

        return result;
    }
}