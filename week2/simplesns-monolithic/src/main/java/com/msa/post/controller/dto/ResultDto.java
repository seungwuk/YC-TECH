package com.msa.post.controller.dto;

public record ResultDto<T>(
        int code,
        String message,
        T data
) {

}
