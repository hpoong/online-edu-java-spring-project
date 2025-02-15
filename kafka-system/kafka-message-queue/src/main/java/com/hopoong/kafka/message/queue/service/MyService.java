package com.hopoong.kafka.message.queue.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hopoong.kafka.message.queue.model.MyModel;

import java.util.List;

public interface MyService {

    public List<MyModel> findAll();
    public MyModel findById(Integer id);
    public MyModel save(MyModel model) throws JsonProcessingException;
    public void delete(Integer id);
}