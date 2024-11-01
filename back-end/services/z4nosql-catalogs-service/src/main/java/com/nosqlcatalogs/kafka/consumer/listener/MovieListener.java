package com.nosqlcatalogs.kafka.consumer.listener;

import com.nosqlcatalogs.core.entity.MovieEntity;
import com.nosqlcatalogs.core.service.MovieService;
import com.nosqlcatalogs.kafka.producer.publisher.ErrorPublisher;
import com.shared.constants.TopicConstans;
import com.shared.dto.kafka.dto.KafkaDto;
import com.shared.objectmapper.KafkaDeserializer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

@AllArgsConstructor
@Configuration
public class MovieListener {

    private final Logger logger = LoggerFactory.getLogger(MovieListener.class);
    private final MovieService movieService;
    private final KafkaDeserializer kafkaDeserializer;
    private final RetryTemplate retryTemplate;
    private final ErrorPublisher errorPublisher;

    @KafkaListener(groupId = "movie-group", topicPartitions = @TopicPartition(topic = TopicConstans.MOVIE, partitions = {"0"}), containerFactory = "movieKafkaListenerContainerFactory")
    public void listenMovieTopicPartition0(@Payload String payload, Acknowledgment acknowledgment) throws Exception {
        this.retryTemplate.execute(context -> {
            this.executeProccess(payload, acknowledgment);
            return null;
        }, context -> {
            this.handlerRetry(payload, context);
            return null;
        });

    }

    private void executeProccess(String payload, Acknowledgment acknowledgment) {
        try {
            MovieEntity data = this.getData(payload);
           this.saveMovie(acknowledgment, data);
        } catch (Exception e) {
            logger.error("Error processing MOVIE message: {}", e.getMessage());
            throw new RuntimeException(e); // Lanzara una excepcion para que vuelva a reintentar
            // No reconocemos el mensaje, por lo que se volverá a intentar
        }
    }

    private MovieEntity getData(String payload) throws Exception {
        KafkaDto<MovieEntity> kafkaDto = this.kafkaDeserializer.deserializeKafka(payload, MovieEntity.class);

        logger.info("Received MOVIE id: {}", kafkaDto.getData().getMovieId());
        logger.info("Received MOVIE name: {}", kafkaDto.getData().getName());

        return kafkaDto.getData();
    }

    private void saveMovie(Acknowledgment acknowledgment, MovieEntity data) {
        this.movieService.createMovie(data)
                .doOnSuccess(movie -> {
                    logger.info("Movie created successfully: {}", movie.getName());
                    acknowledgment.acknowledge(); // Reconocer el mensaje si se procesa correctamente
                })
                .doOnError(throwable -> {
                    logger.error("Error creating movie: {}", throwable.getMessage());
                    // Aquí puedes decidir no reconocer el mensaje para que se reintente
                    // Si quieres manejar el reintento, no llames a acknowledge()
                })
                .subscribe();
    }

    private void handlerRetry(String payload, RetryContext context) {
        // Si se han agotado los intentos, envía el mensaje al topic de error
        int maxAttempts = 3;
        if (this.hasExceededRetryLimit(maxAttempts, context)) {
            this.errorPublisher.sendError(payload, context.getLastThrowable());
        }
    }

    private boolean hasExceededRetryLimit(int maxAttempts, RetryContext context) {
       // Asegúrate de que este valor coincide con el configurado en RetryTemplate
        int retryCount = context.getRetryCount();
        return retryCount >= maxAttempts - 1;
    }

    // El groupId es clave para:
    // Controlar cómo Kafka balancea la carga entre múltiples consumidores.
    // Evitar la duplicación de mensajes.
    // Administrar el offset de los consumidores y manejar el reintento o la continuidad en caso de fallos.

}