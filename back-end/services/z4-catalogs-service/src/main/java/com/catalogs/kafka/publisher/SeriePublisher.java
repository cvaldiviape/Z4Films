package com.catalogs.kafka.publisher;

import com.shared.constants.TopicConstans;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.kafka.dto.KafkaDto;
import com.shared.dto.kafka.enums.ActionTypeEnum;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SeriePublisher {

    private final KafkaTemplate<String, KafkaDto<SerieDto>> kafkaTemplate;

    public void send(SerieDto data) {
        String id = data.getSerieId().toString();

        KafkaDto<SerieDto> payload = KafkaDto.<SerieDto>builder()
                .actionType(ActionTypeEnum.CREATE)
                .data(data)
                .build();

        this.kafkaTemplate.send(TopicConstans.SERIE, 0, id, payload);
    }

}