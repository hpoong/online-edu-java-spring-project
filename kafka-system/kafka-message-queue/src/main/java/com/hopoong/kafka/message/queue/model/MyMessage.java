package com.hopoong.kafka.message.queue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyMessage {
    private int id;
    private int age;
    private String name;
    private String content;
}

