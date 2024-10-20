package com.catalogs.core.repository;

import com.catalogs.core.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {

}