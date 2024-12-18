package com.nosqlcatalogs.kafka.producer.publisher;

import com.shared.constants.TopicConstans;
import com.shared.dto.kafka.dto.ErrorDetailsDto;
import com.shared.dto.kafka.dto.KafkaDto;
import com.shared.dto.kafka.enums.ActionTypeEnum;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@AllArgsConstructor
@Service
public class ErrorPublisher {

    private final KafkaTemplate<String, KafkaDto<ErrorDetailsDto>> kafkaTemplate;

    public void sendError(String payload, Throwable throwable) {
        ErrorDetailsDto errorDetails = ErrorDetailsDto.builder()
                .errorMessage(throwable.getMessage())
                .stackTrace(Arrays.toString(throwable.getStackTrace())) // O algún otro formato de stack trace
                .originalPayload(payload)
                .build();

        KafkaDto<ErrorDetailsDto> errorPayload = KafkaDto.<ErrorDetailsDto>builder()
                .actionType(ActionTypeEnum.ERROR)
                .data(errorDetails)
                .build();

        this.kafkaTemplate.send(TopicConstans.ERROR, errorPayload);
    }

}