package com.hopoong.paymentservice.service;

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

    private final PaymentService paymentService;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    /*
     *  결제 요청.
     */
    @KafkaListener(topics = "payment_request")
    public void consumePaymentRequest(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.PaymentRequestV1.parseFrom(message);
        log.info("[payment_request] consumed ::::: {}", object);

        var payment = paymentService.processPayment(
                object.getUserId(),
                object.getOrderId(),
                object.getAmountKRW(),
                object.getPaymentMethodId()
        );

        var paymentResultMessage = EdaMessage.PaymentResultV1.newBuilder()
            .setPaymentId(payment.getId())
            .setOrderId(payment.getOrderId())
            .setPaymentStatus(payment.getPaymentStatus().toString())
            .build();


        kafkaTemplate.send("payment_result", paymentResultMessage.toByteArray());
    }
}



