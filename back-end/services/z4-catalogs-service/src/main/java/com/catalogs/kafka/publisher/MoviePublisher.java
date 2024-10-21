package com.catalogs.kafka.publisher;

import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.kafka.dto.KafkaDto;
import com.shared.dto.kafka.enums.ActionTypeEnum;
import com.shared.dto.kafka.enums.TopicEnum;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MoviePublisher {

    private final KafkaTemplate<String, KafkaDto<MovieDto>> kafkaTemplate;

    public void send(MovieDto data) {
        String id = data.getMovieId().toString();

        KafkaDto<MovieDto> payload = KafkaDto.<MovieDto>builder()
                .actionType(ActionTypeEnum.CREATE)
                .data(data)
                .build();

        this.kafkaTemplate.send(TopicEnum.MOVIE.getName(), id, payload);
    }

}