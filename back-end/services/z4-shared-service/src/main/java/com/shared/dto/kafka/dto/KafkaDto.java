package com.shared.dto.kafka.dto;

import com.shared.dto.kafka.enums.ActionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaDto<T> {

    private ActionTypeEnum actionType;
    private T data;

}

