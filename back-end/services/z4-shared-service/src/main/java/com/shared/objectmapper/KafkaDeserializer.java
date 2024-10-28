package com.shared.objectmapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shared.dto.kafka.dto.KafkaDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;

@AllArgsConstructor
@Service
public class KafkaDeserializer {

    private final ObjectMapper objectMapper;

    public <T> KafkaDto<T> deserializeKafka(String payload, Class<T> clazz) throws Exception {
        return objectMapper.readValue(payload, new TypeReference<KafkaDto<T>>() {
            @Override
            public Type getType() {
                return objectMapper.getTypeFactory().constructParametricType(KafkaDto.class, clazz);
            }
        });
    }

}