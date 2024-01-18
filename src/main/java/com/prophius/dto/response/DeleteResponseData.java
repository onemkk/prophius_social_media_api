package com.prophius.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteResponseData<T> {
    private T obj;
    private Boolean deleted;
}
