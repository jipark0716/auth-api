package com.jipark.auth.http.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CollectionResponse extends Response {
    private final List<Object> collect;
}
