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
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties;
    }

    private <T> KafkaTemplate<String, KafkaDto<T>> createKafkaTemplate(Class<T> dtoClass) {
        Map<String, Object> configs = this.producerConfig();
        ProducerFactory<String, KafkaDto<T>> producerFactory = new DefaultKafkaProducerFactory<>(configs);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, KafkaDto<MovieDto>> movieKafkaTemplate() {
        return this.createKafkaTemplate(MovieDto.class);
    }

    @Bean
    public KafkaTemplate<String, KafkaDto<SerieDto>> serieKafkaTemplate() {
        return this.createKafkaTemplate(SerieDto.class);
    }

}