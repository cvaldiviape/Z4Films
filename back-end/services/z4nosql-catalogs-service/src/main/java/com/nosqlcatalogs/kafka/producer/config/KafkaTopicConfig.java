package com.nosqlcatalogs.kafka.producer.config;

import com.shared.constants.TopicConstans;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic errorTopic() {
        Map<String, String> configs = new HashMap<>();
        configs.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        configs.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");
        configs.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824");
        configs.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000012");

        return TopicBuilder.name(TopicConstans.ERROR)
                .partitions(2)
                .replicas(1)
                .configs(configs)
                .build();
    }

}