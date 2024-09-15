package com.master.core.repository;

import com.master.core.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    Boolean existsByCode(String code);
    Boolean existsByName(String name);
    Boolean existsByCodeAndGenreIdNot(String code, Integer id);
    Boolean existsByNameAndGenreIdNot(String name, Integer id);

}