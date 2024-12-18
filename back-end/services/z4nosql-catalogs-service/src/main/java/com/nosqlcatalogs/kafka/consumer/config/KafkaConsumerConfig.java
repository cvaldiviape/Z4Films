package com.nosqlcatalogs.kafka.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrapServers}")
    private String bootstrapServer;

    @Autowired
    private KafkaInteceptors kafkaInteceptors;

    private Map<String, Object> consumerConfig(String groupId) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer); // indicando la ubicacion de Kafka con la cual se trabajara
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        // earliest: El consumidor comenzará a leer desde el primer mensaje en la partición.
        // latest: El consumidor comenzará a leer solo los mensajes que se produzcan después de que se inicie.
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // se va desserializar con "StringSerializer"
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return properties;
    }

    // a). Listener Containers: Los contenedores de escucha son responsables de iniciar, detener, y administrar los consumidores.
    // Los contenedores de escucha son componentes más amplios que manejan la configuración y la ejecución de los consumidores. En Spring Kafka,
    // b). ConcurrentKafkaListenerContainerFactory es una fábrica que crea estos contenedores de escucha, los cuales a su vez pueden manejar múltiples
    // hilos de ejecución (y por ende, múltiples consumidores) para procesar mensajes de manera concurrente.
    // c). Spring Kafka permite que un único consumidor pueda manejar la escucha y procesamiento de mensajes desde varios topics de Kafka al mismo tiempo.

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> movieKafkaListenerContainerFactory() {
        Map<String, Object> configs = this.consumerConfig("movie-group");
        ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(configs);

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordInterceptor(this.kafkaInteceptors.interceptorMovie());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> serieKafkaListenerContainerFactory() {
        Map<String, Object> configs = this.consumerConfig("serie-group");
        ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(configs);
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordInterceptor(this.kafkaInteceptors.interceptorSerie());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

}
