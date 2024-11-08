package com.hopoong.kafkaproducer.service;

import com.hopoong.kafkaproducer.model.PurchaseLog;
import com.hopoong.kafkaproducer.model.PurchaseLogOneProduct;
import com.hopoong.kafkaproducer.model.WatchingAdLog;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.hopoong.kafkaproducer.model.EffectOrNot;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdEvaluationService {

    // 광고이력 : KTable, 구매이력 : KStream

    private final ProducerService producerService;


    // kafka-console-consumer --bootstrap-server localhost:9092 --topic AdEvaluationComplete --from-beginning
    // kafka-console-consumer --bootstrap-server localhost:9092 --topic purchaseLogOneProduct --from-beginning
    // kafka-console-producer --broker-list localhost:9092 --topic adLog
    // kafka-console-producer --broker-list localhost:9092 --topic purchaseLog
    @Autowired
    public void buildPipeline(StreamsBuilder sb) {
        JsonSerializer<EffectOrNot> effectSerializer = new JsonSerializer<>();
        JsonSerializer<PurchaseLog> purchaseLogSerializer = new JsonSerializer<>();
        JsonSerializer<WatchingAdLog> watchingAdLogSerializer = new JsonSerializer<>();
        JsonSerializer<PurchaseLogOneProduct> purchaseLogOneProductSerializer = new JsonSerializer<>();

        JsonDeserializer<EffectOrNot> effectDeserializer = new JsonDeserializer<>(EffectOrNot.class);
        JsonDeserializer<PurchaseLog> purchaseLogDeserializer = new JsonDeserializer<>(PurchaseLog.class);
//        JsonDeserializer<PurchaseLog> purchaseLogDeserializer = new JsonDeserializer<>(PurchaseLog.class);
//        purchaseLogDeserializer.ignoreTypeHeaders(); // 타입 정보를 무시하도록 설정
//        purchaseLogDeserializer.configure(Collections.singletonMap("spring.json.trusted.packages", "*"), false);

        JsonDeserializer<WatchingAdLog> watchingAdLogDeserializer = new JsonDeserializer<>(WatchingAdLog.class);
        JsonDeserializer<PurchaseLogOneProduct> purchaseLogOneProductDeserializer = new JsonDeserializer<>(PurchaseLogOneProduct.class);

        Serde<EffectOrNot> effectOrNotSerde = Serdes.serdeFrom(effectSerializer, effectDeserializer);
        Serde<PurchaseLog> purchaseLogSerde = Serdes.serdeFrom(purchaseLogSerializer, purchaseLogDeserializer);
        Serde<WatchingAdLog> watchingAdLogSerde = Serdes.serdeFrom(watchingAdLogSerializer, watchingAdLogDeserializer);
        Serde<PurchaseLogOneProduct> purchaseLogOneProductSerde = Serdes.serdeFrom(purchaseLogOneProductSerializer, purchaseLogOneProductDeserializer);

        // KTable
        // adLog: Kafka 토픽
        // adStore: KTable의 상태를 유지하기 위한 로컬 저장소로, Kafka Streams 애플리케이션이 관리하는 Key-Value 저장소
        KTable<String, WatchingAdLog> adTable = sb.stream("adLog", Consumed.with(Serdes.String(), watchingAdLogSerde))
                .selectKey((k,v) -> v.getUserId() + "_" + v.getProductId())
                .filter((k,v)-> Integer.parseInt(v.getWatchingTime()) > 10)
                .toTable(Materialized.<String, WatchingAdLog, KeyValueStore<Bytes, byte[]>>as("adStore")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(watchingAdLogSerde)
                );

        // KTable
        KStream<String, PurchaseLog> purchaseLogKStream = sb.stream("purchaseLog", Consumed.with(Serdes.String(), purchaseLogSerde));
        purchaseLogKStream.peek((k, v) -> System.out.println("Received data: " + v));
//        purchaseLogKStream.filter((key, value) -> value.getPrice() < 1000000L);

        purchaseLogKStream.foreach((k, v) -> {
            for(String prodId : v.getProductId()) {
                if(v.getPrice() < 1000000L) {
                    PurchaseLogOneProduct purchaseLogOneProductModel = new PurchaseLogOneProduct();
                    purchaseLogOneProductModel.setUserId(v.getUserId());
                    purchaseLogOneProductModel.setProductId(prodId);
                    purchaseLogOneProductModel.setOrderId(v.getOrderId());
                    purchaseLogOneProductModel.setPrice(v.getPrice());

                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> ????");
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> ????");
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> ????");
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> ????");
                    producerService.sendJoineMsg("purchaseLogOneProduct", purchaseLogOneProductModel);
                }
            }
        });

        // KTable
        KTable<String, PurchaseLogOneProduct> purchaseLogOneProductKTable = sb.stream("purchaseLogOneProduct", Consumed.with(Serdes.String(), purchaseLogOneProductSerde))
//                .selectKey((k, v) -> v.getUserId() + "_" + v.getProductId())
                .map((k, v) -> new KeyValue<>(v.getUserId() + "_" + v.getProductId(), v))
                .toTable(Materialized.<String, PurchaseLogOneProduct, KeyValueStore<Bytes, byte[]>>as("purchaseLogStore")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(purchaseLogOneProductSerde)
                );


        ValueJoiner<WatchingAdLog, PurchaseLogOneProduct, EffectOrNot> tableStreamJoiner = (leftValue, rightValue) -> {
            EffectOrNot returnValue = new EffectOrNot();
            returnValue.setUserId(rightValue.getUserId());
            returnValue.setAdId(leftValue.getAdId());
            returnValue.setOrderId(rightValue.getOrderId());
            return returnValue;
        };

        adTable.join(purchaseLogOneProductKTable,tableStreamJoiner)
                .toStream().to("AdEvaluationComplete", Produced.with(Serdes.String(), effectOrNotSerde));


        /*

             1. **입력 토픽**:
                - **`adLog`**: `WatchingAdLog` 데이터를 저장하고, 이 데이터를 `KTable`로 변환해 `adTable`로 사용합니다.
                - **`purchaseLog`**: `PurchaseLog` 데이터를 저장하고, 이 데이터를 `KStream`으로 가져와 가격이 100만원 이하인 경우 `foreach`에서 `purchaseLogOneProductModel`로 변환하여 `oneProduct` 토픽으로 전송합니다.
                - **`purchaseLogOneProduct`**: `PurchaseLogOneProduct` 데이터를 저장하는 토픽으로, `KTable`로 변환해 `purchaseLogOneProductKTable`로 사용됩니다.
            2. **출력 토픽**:
                - **`oneProduct`**: `purchaseLogKStream`의 `PurchaseLog` 데이터를 특정 조건에 맞게 변환하여 이 토픽으로 보냅니다.
                - **`AdEvaluationComplete`**: `adTable`과 `purchaseLogOneProductKTable`을 조인하여 생성된 `EffectOrNot` 데이터를 저장하는 최종 결과 토픽입니다.

            ### 토픽 정리

            - **입력 토픽**: `adLog`, `purchaseLog`, `purchaseLogOneProduct`
            - **출력 토픽**: `oneProduct`, `AdEvaluationComplete`

            각 토픽의 역할:

            - `adLog`: 광고 로그 데이터를 수신.
            - `purchaseLog`: 구매 로그 데이터를 수신.
            - `purchaseLogOneProduct`: 구매 로그에서 개별 제품 정보를 변환하여 수신.
            - `oneProduct`: `purchaseLog`의 필터링된 데이터를 `PurchaseLogOneProduct`로 변환하여 송신.
            - `AdEvaluationComplete`: `WatchingAdLog`와 `PurchaseLogOneProduct`를 조인하여 광고 평가 결과를 송신.

         */

    }

}
