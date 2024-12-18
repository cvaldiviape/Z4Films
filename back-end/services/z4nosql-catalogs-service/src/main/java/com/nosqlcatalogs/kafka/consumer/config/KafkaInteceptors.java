package com.nosqlcatalogs.kafka.consumer.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosqlcatalogs.core.entity.MovieEntity;
import com.nosqlcatalogs.core.entity.SerieEntity;
import com.shared.dto.kafka.dto.KafkaDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.RecordInterceptor;
import java.util.Objects;

@AllArgsConstructor
@Configuration
public class KafkaInteceptors {
    private final Logger logger = LoggerFactory.getLogger(KafkaInteceptors.class);
    private final ObjectMapper objectMapper;

    public RecordInterceptor<String, String> interceptorMovie() {
        return (record, consumer) -> {
            try {
                String payload = record.value();
                KafkaDto<MovieEntity> kafkaDto = this.objectMapper.readValue(payload, new TypeReference<KafkaDto<MovieEntity>>() {});
                MovieEntity data = kafkaDto.getData();

                if(Objects.nonNull(data)){
                    logger.info("INTERCEPTOR MOVIE: " + data.getName());
                    return record;
                }
            } catch (Exception e) {
                logger.error("INTERCEPTOR ERROR: ", e.getMessage());
            }
            return record;
        };
    }

    public RecordInterceptor<String, String> interceptorSerie() {
        return (record, consumer) -> {
            try {
                String payload = record.value();
                KafkaDto<SerieEntity> kafkaDto = this.objectMapper.readValue(payload, new TypeReference<KafkaDto<SerieEntity>>() {});
                SerieEntity data = kafkaDto.getData();

                if(Objects.nonNull(data)){
                    logger.info("INTERCEPTOR SERIE: " + data.getName());
                    return record;
                }
            } catch (Exception e) {
                logger.error("INTERCEPTOR ERROR: ", e.getMessage());
            }
            return record;
        };
    }

}
