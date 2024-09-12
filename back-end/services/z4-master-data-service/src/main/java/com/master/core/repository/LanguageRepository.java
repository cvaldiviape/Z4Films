package com.master.core.repository;

import com.master.core.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {

    Boolean existsByCode(String code);
    Boolean existsByName(String name);
    Boolean existsByCodeAndLanguageIdNot(String code, Integer id);
    Boolean existsByNameAndLanguageIdNot(String name, Integer id);

}