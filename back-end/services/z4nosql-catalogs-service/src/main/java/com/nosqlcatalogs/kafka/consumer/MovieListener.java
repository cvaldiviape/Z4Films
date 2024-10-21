package com.nosqlcatalogs.kafka.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nosqlcatalogs.core.entity.MovieEntity;
import com.nosqlcatalogs.core.service.MovieService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.kafka.dto.KafkaDto;
import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class MovieListener {
    private final Logger logger = LoggerFactory.getLogger(MovieListener.class);
    @Autowired
    private MovieService movieService;

    @KafkaListener(topics = {"movie-topic"}, groupId = "movie-group") // indicando la lista de topic a los que va esta a la escucha el Consumer
    public void listen(@Payload String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        try {
            KafkaDto<MovieEntity> kafkaDto = objectMapper.readValue(payload, new TypeReference<KafkaDto<MovieEntity>>() {});
            // Procesar objeto PersonaDto
            logger.info("Received Persona message: {}", kafkaDto.getActionType());
            logger.info("Received Persona message: {}", kafkaDto.getData().getMovieId());

            this.movieService.createMovie(kafkaDto.getData())
                    .doOnSuccess(movie -> logger.info("Movie created successfully: {}", movie))
                    .doOnError(throwable -> logger.error("Error creating movie: {}", throwable.getMessage()))
                    .subscribe();
        } catch (Exception e) {
            logger.error("Error processing Persona message: {}", e.getMessage());
        }
    }

}