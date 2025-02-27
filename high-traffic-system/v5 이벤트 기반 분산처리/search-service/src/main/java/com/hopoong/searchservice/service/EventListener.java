package com.hopoong.searchservice.service;

import blackfriday.protobuf.EdaMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class EventListener {

    private final SearchService searchService;

    @KafkaListener(topics = "product_tags_added")
    public void consumeTagAdded(byte[] message) throws InvalidProtocolBufferException {
        EdaMessage.ProductTags productTags = EdaMessage.ProductTags.parseFrom(message);
        searchService.addTagCache(productTags.getProductId(), productTags.getTagsList());
    }


    @KafkaListener(topics = "product_tags_remove")
    public void consumeTagRemove(byte[] message) throws InvalidProtocolBufferException {
        EdaMessage.ProductTags productTags = EdaMessage.ProductTags.parseFrom(message);
        searchService.removeTagCache(productTags.getProductId(), productTags.getTagsList());
    }

}



