package com.ribas.kafka.serializer;

import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.Deserializer;

import java.lang.reflect.Method;
import java.util.Map;

public class ProtobufDeserializer<T extends Message> implements Deserializer<T> {

    private Class<T> type;

    @Override
    @SuppressWarnings("unchecked")
    public void configure(Map<String, ?> configs, boolean isKey) {
        String className = (String) configs.get("value.deserializer.target.class");
        try {
            type = (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new KafkaException("Failed to load class for protobuf deserialization", e);
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            if (data == null || type == null) return null;
            Method parserMethod = type.getMethod("parser");
            Parser<T> parser = (Parser<T>) parserMethod.invoke(null);
            return parser.parseFrom(data);
        } catch (Exception e) {
            throw new KafkaException("Failed to deserialize protobuf message", e);
        }
    }
}
