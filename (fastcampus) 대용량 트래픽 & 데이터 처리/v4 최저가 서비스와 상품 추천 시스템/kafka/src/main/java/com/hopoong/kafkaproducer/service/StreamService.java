package com.hopoong.kafkaproducer.service;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class StreamService {

    private static final Serde<String> STRING_SERDE = Serdes.String();


    // click-logs에서 "user"라는 단어가 포함된 메시지만 필터링하여 userClass로 전송
    @Autowired
    public void buildPipeline(StreamsBuilder sb) {

        // kafka-console-consumer --bootstrap-server localhost:9092 --topic click-logs --from-beginning
        // kafka-console-consumer --bootstrap-server localhost:9092 --topic userClass --from-beginning
        // kafka-console-producer --broker-list localhost:9092 --topic click-logs
//        KStream<String, String> stream = sb.stream("click-logs", Consumed.with(STRING_SERDE, STRING_SERDE));
//        stream.print(Printed.toSysOut());
//        stream.filter( (key, value) -> value.equals("user") ).to("userClass");



        // kafka-console-consumer --bootstrap-server localhost:9092 --topic joinTopic --from-beginning
        // kafka-console-producer --broker-list localhost:9092 --topic leftTopic
        // kafka-console-producer --broker-list localhost:9092 --topic rightTopic
//        KStream<String, String> leftStream = sb.stream(
//                "leftTopic",
//                Consumed.with(STRING_SERDE, STRING_SERDE))
//                    .selectKey((k, v) -> v.substring(0, v.indexOf(":")));
//
//        KStream<String, String> rightStream = sb.stream(
//                        "rightTopic",
//                        Consumed.with(STRING_SERDE, STRING_SERDE))
//                .selectKey((k, v) -> v.substring(0, v.indexOf(":")));
//
//        leftStream.print(Printed.toSysOut());
//        rightStream.print(Printed.toSysOut());
//
//        KStream<String, String> joinStream = leftStream.join(
//                rightStream,
//                (leftValue, rightValue) -> leftValue + "_" + rightValue,
//                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1))
//        );
//
//        joinStream.print(Printed.toSysOut());
//        joinStream.to("joinTopic");


        KStream<String, String> leftStream = sb.stream(
                        "leftTopic",
                        Consumed.with(STRING_SERDE, STRING_SERDE));

        KStream<String, String> rightStream = sb.stream(
                        "rightTopic",
                        Consumed.with(STRING_SERDE, STRING_SERDE));

        leftStream.print(Printed.toSysOut());
        rightStream.print(Printed.toSysOut());


        ValueJoiner<String, String, String> stringValueJoiner = (left, right) -> {
            return "[stringValueJoiner]" + left + "-" + right;
        };

        ValueJoiner<String, String, String> stringOuterValueJoiner = (left, right) -> {
            return "[stringOuterValueJoiner]" + left + "<" + right;
        };

        KStream<String, String> joinStream = leftStream.join(
                rightStream,
                stringValueJoiner,
                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1))
        );

        KStream<String, String> outerJoinStream = leftStream.outerJoin(
                rightStream,
                stringOuterValueJoiner,
                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1))
        );

//        joinStream.print(Printed.toSysOut());
        joinStream.to("joinTopic");
        outerJoinStream.to("outerJoinTopic");
    }



}
