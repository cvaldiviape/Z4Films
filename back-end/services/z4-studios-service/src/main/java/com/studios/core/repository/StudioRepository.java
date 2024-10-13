package com.studios.core.repository;

import com.studios.core.entity.StudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<StudioEntity, Integer> {

    Boolean existsByName(String name);
    Boolean existsByNameAndStudioIdNot(String name, Integer id);

}