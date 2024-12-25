package com.luu.picbackend.common;

import com.luu.picbackend.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public BaseResponse(String message, T data, int code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public BaseResponse(int code, T data) {
        this("", data, code);
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getMessage(), null, errorCode.getCode());
    }
}
