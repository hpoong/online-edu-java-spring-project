package com.hopoong.kafka.message.queue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hopoong.kafka.message.queue.domain.MyEntity;
import com.hopoong.kafka.message.queue.domain.MyJpaRepository;
import com.hopoong.kafka.message.queue.model.MyModel;
import com.hopoong.kafka.message.queue.model.MyModelConverter;
import com.hopoong.kafka.message.queue.model.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyServiceImpl implements MyService {

    private final MyJpaRepository myJpaRepository;

    @Override
    public List<MyModel> findAll() {
        List<MyEntity> entities = myJpaRepository.findAll();
        return entities.stream().map(MyModelConverter::toModel).toList();
    }

    @Override
    public MyModel findById(Integer id) {
        Optional<MyEntity> entity = myJpaRepository.findById(id);
        return entity.map(MyModelConverter::toModel).orElse(null);
    }


    @Override
    @Transactional
    public MyModel save(MyModel model) throws JsonProcessingException {
        OperationType operationType = model.getId() == null ? OperationType.CREATE : OperationType.UPDATE;
        MyEntity entity = myJpaRepository.save(MyModelConverter.toEntity(model));
        MyModel resultModel = MyModelConverter.toModel(entity);
        return resultModel;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        myJpaRepository.deleteById(id);
    }
}