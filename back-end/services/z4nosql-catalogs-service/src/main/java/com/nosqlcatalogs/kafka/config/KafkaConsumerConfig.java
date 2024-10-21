package com.nosqlcatalogs.kafka.config;

import com.nosqlcatalogs.core.entity.MovieEntity;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.kafka.dto.KafkaDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrapServers}")
    private String bootstrapServer;

    public Map<String, Object> consumerConfig(String groupId) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer); // indicando la ubicacion de Kafka con la cual se trabajara
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // se va desserializar con "StringSerializer"
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Permitir deserialización de cualquier paquete
        return properties;
    }

    // a). Listener Containers: Los contenedores de escucha son responsables de iniciar, detener, y administrar los consumidores.
    // Los contenedores de escucha son componentes más amplios que manejan la configuración y la ejecución de los consumidores. En Spring Kafka,
    // b). ConcurrentKafkaListenerContainerFactory es una fábrica que crea estos contenedores de escucha, los cuales a su vez pueden manejar múltiples
    // hilos de ejecución (y por ende, múltiples consumidores) para procesar mensajes de manera concurrente.
    // c). Spring Kafka permite que un único consumidor pueda manejar la escucha y procesamiento de mensajes desde varios topics de Kafka al mismo tiempo.
    @Bean
    public ConsumerFactory<String, KafkaDto<MovieEntity>> movieConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(this.consumerConfig("movie-group"));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaDto<MovieEntity>> personaKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaDto<MovieEntity>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.movieConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaDto<SerieDto>> serieConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(this.consumerConfig("serie-group"));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaDto<SerieDto>> serieKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaDto<SerieDto>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.serieConsumerFactory());
        return factory;
    }

}