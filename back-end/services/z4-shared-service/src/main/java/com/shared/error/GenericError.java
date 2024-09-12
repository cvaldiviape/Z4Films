package com.shared.error;

public interface GenericError {

    String getCode();
    String getMessage();
    TypeExceptionEnum getType();

}