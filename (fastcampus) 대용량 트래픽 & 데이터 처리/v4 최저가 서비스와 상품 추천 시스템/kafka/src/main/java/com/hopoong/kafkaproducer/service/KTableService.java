package com.hopoong.kafkaproducer.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KTableService {


    @Autowired
    public void buildTablePipeline(StreamsBuilder sb) {

        // kafka-console-consumer --bootstrap-server localhost:9092 --topic joinedMsg --from-beginning
        // kafka-console-producer --broker-list localhost:9092 --topic leftTopic --property "parse.key=true" --property "key.separator=:"
        // kafka-console-producer --broker-list localhost:9092 --topic rightTopic --property "parse.key=true" --property "key.separator=:"
        KTable<String, String> leftTable = sb.stream("leftTopic", Consumed.with(Serdes.String(),Serdes.String())).toTable();
        KTable<String, String> rightTable = sb.stream("rightTopic", Consumed.with(Serdes.String(),Serdes.String())).toTable();

        ValueJoiner<String, String, String> stringJoiner = (leftValue, rightValue) -> {
            return "[StringJoiner]" + leftValue + "-" + rightValue;
        };

        KTable<String, String> joinTables = leftTable.join(rightTable, stringJoiner);
        joinTables.toStream().to("joinedMsg");
    }

}
