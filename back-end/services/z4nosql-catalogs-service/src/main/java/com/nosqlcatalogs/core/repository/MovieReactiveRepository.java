package com.nosqlcatalogs.core.repository;

import com.nosqlcatalogs.core.entity.MovieEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieReactiveRepository extends ReactiveMongoRepository<MovieEntity, String> {

}