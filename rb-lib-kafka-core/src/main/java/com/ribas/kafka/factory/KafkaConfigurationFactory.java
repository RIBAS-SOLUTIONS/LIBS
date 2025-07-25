package com.ribas.kafka.factory;

import com.google.protobuf.Message;
import com.ribas.kafka.config.KafkaConfig;
import com.ribas.kafka.serializer.ProtobufDeserializer;
import com.ribas.kafka.serializer.ProtobufSerializer;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

@Factory
@RequiredArgsConstructor
public class KafkaConfigurationFactory {

    private final KafkaConfig kafkaConfig;

    @Singleton
    public KafkaProducer<String, Message> kafkaProducer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConfig.getClientId());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProtobufSerializer.class);

        return new KafkaProducer<>(props);
    }

    @Singleton
    public <T extends Message> KafkaConsumer<String, T> kafkaConsumer(Class<T> type) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "default-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProtobufDeserializer.class.getName());
        props.put("value.deserializer.target.class", type.getName());

        return new KafkaConsumer<>(props);
    }
}
