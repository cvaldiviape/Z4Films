package com.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.shared.utils.response.ResponseDto;
import java.util.List;
import java.util.Objects;

public class ObjectMapperUtil {

    public static <T> T convertToObject(Object data, Class<T> clase) {
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.convertValue(data, clase);  // convertir un "objeto" a un "objeto/json"
    }

    public static <T> List<T> convertObjectToList(Object data, Class<T> typeClass) {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        CollectionType collectionType = typeFactory.constructCollectionType(List.class, typeClass);
        return mapper.convertValue(data, collectionType);
    }

    public static ResponseDto convertJsonToObject(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, ResponseDto.class); // deserializar "json" para convertir a un "objeto"
    }

    public static String convertObjectToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
