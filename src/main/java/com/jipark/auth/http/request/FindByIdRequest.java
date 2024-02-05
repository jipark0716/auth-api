package com.jipark.auth.http.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindByIdRequest {
    private Long[] ids;
}
