package com.hopoong.kafka.message.queue.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyJpaRepository extends JpaRepository<MyEntity, Integer> { }