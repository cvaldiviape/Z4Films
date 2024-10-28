package com.nosqlcatalogs.core.repository;

import com.nosqlcatalogs.core.entity.SerieEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SerieReactiveRepository extends ReactiveMongoRepository<SerieEntity, String> {

}