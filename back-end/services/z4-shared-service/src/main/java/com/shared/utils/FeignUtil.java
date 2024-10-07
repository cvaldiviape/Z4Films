package com.shared.utils;

import com.shared.utils.response.ResponseDto;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class FeignUtil {

    public static <T> T extracstData(ResponseDto apiResponse, Class<T> typeClass) {
        if (apiResponse.getData() != null) {
            Object data = apiResponse.getData();
            return (T) ObjectMapperUtil.convertToObject(data, typeClass);
        }
        return null;
    }

    public static <T> T extracstData(ResponseDto apiResponse, Class<T> clase, String nameValueNotFound) {
        Object data = Optional.ofNullable(apiResponse.getData())
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(nameValueNotFound));
        return ObjectMapperUtil.convertToObject(data, clase);
    }

    public static <T> List<T> extractsDataList(ResponseDto apiResponse, Class<T> typeClass) {
        if (apiResponse.getData() != null) {
            return ObjectMapperUtil.convertObjectToList(apiResponse.getData(), typeClass);
        }
        return null;
    }

    public static <T> List<T> extractsDataList(ResponseDto apiResponse, Class<T> typeClass, String nameValueNotFound) {
        Object data = Optional.ofNullable(apiResponse.getData())
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(nameValueNotFound));
        return ObjectMapperUtil.convertObjectToList(data, typeClass);
    }

}