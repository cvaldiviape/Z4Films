package com.nosqlcatalogs.kafka.consumer.listener;

import com.nosqlcatalogs.core.entity.SerieEntity;
import com.nosqlcatalogs.core.service.SerieService;
import com.nosqlcatalogs.kafka.producer.publisher.ErrorPublisher;
import com.shared.constants.TopicConstans;
import com.shared.objectmapper.KafkaDeserializer;
import com.shared.dto.kafka.dto.KafkaDto;
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
public class SerieListener {

    private final Logger logger = LoggerFactory.getLogger(SerieListener.class);
    private final SerieService serieService;
    private final KafkaDeserializer kafkaDeserializer;
    private final RetryTemplate retryTemplate;
    private final ErrorPublisher errorPublisher;

    @KafkaListener(groupId = "serie-group", topicPartitions = @TopicPartition(topic = TopicConstans.SERIE, partitions = {"0"}), containerFactory = "serieKafkaListenerContainerFactory")
    public void listenSerieTopicPartition1(@Payload String payload, Acknowledgment acknowledgment) {
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
            SerieEntity data = this.getData(payload);
            this.saveSerie(acknowledgment, data);
        } catch (Exception e) {
            logger.error("Error processing SERIE message: {}", e.getMessage());
            throw new RuntimeException(e); // Lanzara una excepcion para que vuelva a reintentar
            // No reconocemos el mensaje, por lo que se volverá a intentar
        }
    }

    private SerieEntity getData(String payload) throws Exception {
        KafkaDto<SerieEntity> kafkaDto = this.kafkaDeserializer.deserializeKafka(payload, SerieEntity.class);

        logger.info("Received SERIE id: {}", kafkaDto.getData().getSerieId());
        logger.info("Received SERIE name: {}", kafkaDto.getData().getName());

        return kafkaDto.getData();
    }

    private void saveSerie(Acknowledgment acknowledgment, SerieEntity data) {
        this.serieService.createSerie(data)
                .doOnSuccess(serie -> {
                    logger.info("Serie created successfully: {}", serie.getName());
                    acknowledgment.acknowledge(); // Reconocer el mensaje si se procesa correctamente
                })
                .doOnError(throwable -> {
                    logger.error("Error creating serie: {}", throwable.getMessage());
                    // Aquí puedes decidir no reconocer el mensaje para que se reintente
                    // Si quieres manejar el reintento, no llames a acknowledge()
                })
                .subscribe();
    }

    private void handlerRetry(String payload, RetryContext context) {
        // Si se han agotado los intentos, envía el mensaje al topic de error
        int maxAttempts = 3; // Asegúrate de que este valor coincide con el configurado en RetryTemplate
        if (context.getRetryCount() >= maxAttempts - 1) {
            this.errorPublisher.sendError(payload, context.getLastThrowable());
        }
    }

    // El groupId es clave para:
    // Controlar cómo Kafka balancea la carga entre múltiples consumidores.
    // Evitar la duplicación de mensajes.
    // Administrar el offset de los consumidores y manejar el reintento o la continuidad en caso de fallos.

}