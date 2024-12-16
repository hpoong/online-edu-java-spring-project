package com.hopoong.deliveryservice.service;

import blackfriday.protobuf.EdaMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class EventListener {

    private final DeliveryService deliveryService;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    /*
     *  배송 요청.
     */
    @KafkaListener(topics = "delivery_request")
    public void consumeDeliveryRequest(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.DeliveryRequestV1.parseFrom(message);
        log.info("[delivery_request] consumed ::::: {}", object);


        // 배송 등록
        var delivery = deliveryService.processDelivery(
            object.getOrderId(),
            object.getProductName(),
            object.getProductCount(),
            object.getAddress()
        );

        // 배송 상태 변경 되었다고 알려줌?
        var deliveryStatusMassege = EdaMessage.DeliveryStatusUpdateV1.newBuilder()
                        .setOrderId(delivery.getOrderId())
                        .setDeliveryId(delivery.getId())
                        .setDeliveryStatus(delivery.getDeliveryStatus().toString())
                        .build();

        kafkaTemplate.send("delivery_status_update", deliveryStatusMassege.toByteArray());
    }
}



