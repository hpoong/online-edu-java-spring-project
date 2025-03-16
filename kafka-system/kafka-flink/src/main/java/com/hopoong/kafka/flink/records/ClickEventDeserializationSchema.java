package com.hopoong.kafka.flink.records;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class ClickEventDeserializationSchema implements DeserializationSchema<ClickEvent> {

    @Override
    public ClickEvent deserialize(byte[] message) throws IOException {
        return null;
    }

    @Override
    public boolean isEndOfStream(ClickEvent nextElement) {
        return false;
    }

    @Override
    public TypeInformation<ClickEvent> getProducedType() {
        return null;
    }
}
