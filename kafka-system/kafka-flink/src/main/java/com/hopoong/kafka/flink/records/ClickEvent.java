package com.hopoong.kafka.flink.records;


import lombok.Data;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Data
public class ClickEvent {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss:SSS")
    private Date timestamp;
    private String page;
}