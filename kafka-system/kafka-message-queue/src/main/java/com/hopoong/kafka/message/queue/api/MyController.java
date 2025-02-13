package com.hopoong.kafka.message.queue.api;

import com.hopoong.kafka.message.queue.model.MyMessage;
import com.hopoong.kafka.message.queue.producer.MyProducer;
import com.hopoong.kafka.message.queue.producer.MySecondProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MyController {

    private int messageIndex = 0;
    private int secondIndex = 0;

    private final MyProducer myProducer;
    private final MySecondProducer mySecondProducer;

    @PostMapping("/message")
    void message() {
        var data = MyMessage.builder()
                .age(messageIndex)
                .id(messageIndex).build();
        messageIndex++;
        myProducer.sendMessage(data);
    }

    @PostMapping("/second-message")
    void secondmessage() {
        String message = "message :::: " + secondIndex;
        mySecondProducer.sendMessageWithKey(String.valueOf(secondIndex), message);
        secondIndex++;
    }


}
