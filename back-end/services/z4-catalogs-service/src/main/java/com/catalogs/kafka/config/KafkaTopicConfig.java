package com.catalogs.kafka.config;

import com.shared.dto.kafka.enums.TopicEnum;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    public NewTopic movieTopic() {
        Map<String, String> configs = new HashMap<>();
        configs.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE); // "CLEANUP_POLICY_DELETE" despues de cierto tiempo, si el mensaje ya no es usado se ELIMINA.
        // "CLEANUP_POLICY_COMPACT" mantendra el ultimo mensaje que se envio.
        configs.put(TopicConfig.RETENTION_MS_CONFIG, "86400000"); // indicando cuanto tiempo se matendra el mensaje, despues de ese tiempo se eliminara automaticamente.
        // por defecto viene "-1", es decir, nunca se borraran. "se pone todo en milisegundos"
        configs.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824"); // capacidad maxima de un segmento sera de 1GB (por default es 1GB), "se pone todo en bytes"
        configs.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000012"); // si no se configura, no hay limite de la capacidad maxima de un mensaje. "se pone todo en bytes"

        return TopicBuilder.name(TopicEnum.MOVIE.getName())
                .partitions(2)
                .replicas(2)
                .configs(configs)
                .build();

        // en mi topic defino la cantidad de particiones para indicar a su vez el limite maximo de consumidores
        // que pueden estar a la escucha al mismo tiempo del topic.
    }

    public NewTopic serieTopic() {
        Map<String, String> configs = new HashMap<>();
        configs.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE); // "CLEANUP_POLICY_DELETE" despues de cierto tiempo, si el mensaje ya no es usado se ELIMINA.
        // "CLEANUP_POLICY_COMPACT" mantendra el ultimo mensaje que se envio.
        configs.put(TopicConfig.RETENTION_MS_CONFIG, "86400000"); // indicando cuanto tiempo se matendra el mensaje, despues de ese tiempo se eliminara automaticamente.
        // por defecto viene "-1", es decir, nunca se borraran. "se pone todo en milisegundos"
        configs.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824"); // capacidad maxima de un segmento sera de 1GB (por default es 1GB), "se pone todo en bytes"
        configs.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000012"); // si no se configura, no hay limite de la capacidad maxima de un mensaje. "se pone todo en bytes"

        return TopicBuilder.name(TopicEnum.SERIE.getName())
                .partitions(2)
                .replicas(2)
                .configs(configs)
                .build();

        // en mi topic defino la cantidad de particiones para indicar a su vez el limite maximo de consumidores
        // que pueden estar a la escucha al mismo tiempo del topic.
    }

}