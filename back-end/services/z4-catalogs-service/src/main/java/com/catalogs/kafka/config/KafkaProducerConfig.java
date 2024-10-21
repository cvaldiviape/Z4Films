package com.catalogs.kafka.config;

import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.kafka.dto.KafkaDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrapServers}")
    private String bootstrapServer;

    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer); // indicando la ubicacion de Kafka con la cual se trabajara
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // se va serializar con
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties;
    }

    @Bean
    public ProducerFactory<String, KafkaDto<MovieDto>> movieProducerFactory() { // creando un "provider"
        return new DefaultKafkaProducerFactory<>(this.producerConfig());
    }

    // simplifica la producción de mensajes hacia Kafka, es decir ya vinene con: configuración del productor, serialización de datos, gestión de transacciones (si se habilita),
    // y proporcionando métodos convenientes para enviar mensajes de manera síncrona o asíncrona.
    @Bean
    public KafkaTemplate<String, KafkaDto<MovieDto>> movieKafkaTemplate(ProducerFactory<String, KafkaDto<MovieDto>> movieProducerFactory) {
        return new KafkaTemplate<>(movieProducerFactory);
    }

    @Bean
    public ProducerFactory<String, KafkaDto<SerieDto>> serieProducerFactory() { // creando un "provider"
        return new DefaultKafkaProducerFactory<>(this.producerConfig());
    }

    // simplifica la producción de mensajes hacia Kafka, es decir ya vinene con: configuración del productor, serialización de datos, gestión de transacciones (si se habilita),
    // y proporcionando métodos convenientes para enviar mensajes de manera síncrona o asíncrona.
    @Bean
    public KafkaTemplate<String, KafkaDto<SerieDto>> serieKafkaTemplate(ProducerFactory<String, KafkaDto<SerieDto>> serieProducerFactory) {
        return new KafkaTemplate<>(serieProducerFactory);
    }

}