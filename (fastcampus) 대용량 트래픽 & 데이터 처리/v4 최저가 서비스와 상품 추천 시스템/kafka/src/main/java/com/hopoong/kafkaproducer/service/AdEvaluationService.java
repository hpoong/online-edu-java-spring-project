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

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdEvaluationService {

    // 광고이력 : KTable, 구매이력 : KStream

    private final ProducerService producerService;


    // kafka-console-producer --broker-list localhost:9092 --topic adLog
    // kafka-console-producer --broker-list localhost:9092 --topic purchaseLog
    // kafka-console-consumer --bootstrap-server localhost:9092 --topic purchaseLogOneProduct --from-beginning
    // kafka-console-consumer --bootstrap-server localhost:9092 --topic AdEvaluationComplete --from-beginning
    @Autowired
    public void buildPipeline(StreamsBuilder sb) {
        
       try {
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
           KTable<String, WatchingAdLog> adTable = sb.stream("adLog", Consumed.with(Serdes.String(), watchingAdLogSerde))
               .peek((k, v) -> System.out.println("adLog Received data ::::::  " + v))
               .selectKey((k,v) -> v.getUserId() + "_" + v.getProductId())
               .toTable(Materialized.<String, WatchingAdLog, KeyValueStore<Bytes, byte[]>>as("adStore")
                       .withKeySerde(Serdes.String())
                       .withValueSerde(watchingAdLogSerde)
               );



           // KTable
           KStream<String, PurchaseLog> purchaseLogKStream = sb.stream("purchaseLog", Consumed.with(Serdes.String(), purchaseLogSerde));
           purchaseLogKStream.peek((k, v) -> System.out.println("purchaseLog Received data :::::: " + v));
           purchaseLogKStream.foreach((k, v) -> {
               for(Map<String, String> productInfoMapData : v.getProductInfo()) {
                   if(Integer.valueOf(productInfoMapData.get("price")) < 1000000L) {
                       PurchaseLogOneProduct purchaseLogOneProductModel = new PurchaseLogOneProduct();
                       purchaseLogOneProductModel.setUserId(v.getUserId());
                       purchaseLogOneProductModel.setProductId(productInfoMapData.get("productId"));
                       purchaseLogOneProductModel.setOrderId(v.getOrderId());
                       purchaseLogOneProductModel.setPrice(productInfoMapData.get("price"));
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

               Map<String, String> tmpMap = new HashMap<>();
               tmpMap.put("price", rightValue.getPrice());
               tmpMap.put("productId", rightValue.getProductId());
               returnValue.setProductInfo(tmpMap);
               return returnValue;
           };

           adTable.join(purchaseLogOneProductKTable,tableStreamJoiner)
                   .toStream().to("AdEvaluationComplete", Produced.with(Serdes.String(), effectOrNotSerde));


           /*
                입력:
                    adLog 토픽에서 광고이력(WatchingAdLog) 데이터를 가져옴
                    purchaseLog 토픽에서 구매이력(PurchaseLog) 데이터를 가져옴
                처리:
                    purchaseLog 에서 price가 100만원 미만인 항목만 골라 purchaseLogOneProduct 토픽으로 전송
                조인:
                    adLog 과 purchaseLogOneProduct 데이터를 userId와 productId로 조인하여 EffectOrNot 데이터를 생성하고, 결과를 AdEvaluationComplete 토픽에 전송
            */


       } catch (Exception e) {
           e.printStackTrace();
       }

    }

}
