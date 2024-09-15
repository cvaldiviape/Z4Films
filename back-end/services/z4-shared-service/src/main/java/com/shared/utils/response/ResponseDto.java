package com.shared.utils.response;

import com.shared.error.TypeExceptionEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {

    private String dateTime;
    private Boolean success;
    private String message;
    private String code;
    private Object data;
    private TypeExceptionEnum type;

}