package com.ribas.kafka.serializer;

import com.google.protobuf.Message;
import org.apache.kafka.common.serialization.Serializer;

public class ProtobufSerializer<T extends Message> implements Serializer<T> {
    @Override
    public byte[] serialize(String topic, T data) {
        return data == null ? null : data.toByteArray();
    }
}
