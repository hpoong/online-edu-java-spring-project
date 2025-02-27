package com.hopoong.catalogservice.cassandra.repository;

import com.hopoong.catalogservice.cassandra.entity.ProductEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CassandraRepository<ProductEntity, Long> {

}
