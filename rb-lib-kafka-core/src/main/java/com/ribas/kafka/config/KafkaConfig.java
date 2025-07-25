package com.ribas.kafka.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("kafka")
public class KafkaConfig {
    private String bootstrapServers;
    private String clientId;
}
