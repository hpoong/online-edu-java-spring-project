package com.hopoong.kafkaproducer.service;

import com.hopoong.kafkaproducer.model.PurchaseLog;
import com.hopoong.kafkaproducer.model.PurchaseLogOneProduct;
import com.hopoong.kafkaproducer.model.WatchingAdLog;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.hopoong.kafkaproducer.model.EffectOrNot;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdEvaluationService {

    // 광고이력 : KTable, 구매이력 : KStream

    private final ProducerService producerService;


    @Autowired
    public void buildPipeline(StreamsBuilder sb) {
        JsonSerializer<EffectOrNot> effectSerializer = new JsonSerializer<>();
        JsonSerializer<PurchaseLog> purchaseLogSerializer = new JsonSerializer<>();
        JsonSerializer<WatchingAdLog> watchingAdLogSerializer = new JsonSerializer<>();
        JsonSerializer<PurchaseLogOneProduct> purchaseLogOneProductSerializer = new JsonSerializer<>();

        JsonDeserializer<EffectOrNot> effectDeserializer = new JsonDeserializer<>(EffectOrNot.class);
        JsonDeserializer<PurchaseLog> purchaseLogDeserializer = new JsonDeserializer<>(PurchaseLog.class);
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

        // Stream
        KStream<String, PurchaseLog> purchaseLogKStream = sb.stream("purchaseLog", Consumed.with(Serdes.String(), purchaseLogSerde));
        purchaseLogKStream.filter((key, value) -> value.getPrice() < 1000000L);

        purchaseLogKStream.foreach((k, v) -> {
            for(String prodId : v.getProductId()) {
                if(v.getPrice() < 1000000L) {
                    PurchaseLogOneProduct purchaseLogOneProductModel = new PurchaseLogOneProduct();
                    purchaseLogOneProductModel.setUserId(v.getUserId());
                    purchaseLogOneProductModel.setProductId(prodId);
                    purchaseLogOneProductModel.setOrderId(v.getOrderId());
                    purchaseLogOneProductModel.setPrice(v.getPrice());

                    producerService.sendJoineMsg("oneProduct", purchaseLogOneProductModel);
                }
            }
        });

        // KTable
        KTable<String, PurchaseLogOneProduct> purchaseLogOneProductKTable = sb.stream("purchaseLogOneProduct", Consumed.with(Serdes.String(), purchaseLogOneProductSerde))
                .selectKey((k, v) -> v.getUserId() + "_" + v.getProductId())
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

    }

}
