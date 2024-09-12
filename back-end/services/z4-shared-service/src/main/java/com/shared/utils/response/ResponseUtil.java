package com.shared.utils.response;

import com.shared.enums.ControllerMessageEnum;
import com.shared.error.TypeExceptionEnum;
import com.shared.utils.DateUtil;
import java.time.LocalDateTime;

public class ResponseUtil {

    public static ResponseDto ok(ControllerMessageEnum messageEnum, Object data) {
        String dateTime = DateUtil.convertLocalDateTimeToString(LocalDateTime.now());
        return ResponseDto.builder()
                .dateTime(dateTime)
                .success(true)
                .message(messageEnum.getValue())
                .data(data)
                .type(null)
                .build();
    }

    public static ResponseDto error(String message, String code, TypeExceptionEnum type) {
        String dateTime = DateUtil.convertLocalDateTimeToString(LocalDateTime.now());
        return ResponseDto.builder()
                .dateTime(dateTime)
                .success(false)
                .message(message)
                .code(code)
                .data(null)
                .type(type)
                .build();
    }

    public static ResponseDto error(String message) {
        String dateTime = DateUtil.convertLocalDateTimeToString(LocalDateTime.now());
        return ResponseDto.builder()
                .dateTime(dateTime)
                .success(false)
                .message(message)
                .code(null)
                .data(null)
                .type(null)
                .build();
    }

}