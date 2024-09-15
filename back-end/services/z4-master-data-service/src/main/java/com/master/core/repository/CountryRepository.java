package com.master.core.repository;

import com.master.core.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

    Boolean existsByCode(String code);
    Boolean existsByName(String name);
    Boolean existsByCodeAndCountryIdNot(String code, Integer id);
    Boolean existsByNameAndCountryIdNot(String name, Integer id);

}