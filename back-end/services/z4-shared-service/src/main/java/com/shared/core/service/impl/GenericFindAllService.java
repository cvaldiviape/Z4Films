package com.shared.core.service.impl;

import com.shared.core.service.FindAllService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class GenericFindAllService<ENTITY, DTO, ID> implements FindAllService<DTO> {

    public abstract JpaRepository<ENTITY, ID> getJpaRepository();

    public abstract List<DTO> toListDtos(List<ENTITY> listEntities);

    @Override
    public List<DTO> findAll() {
        List<ENTITY> listEntities = this.getJpaRepository().findAll();
        return this.toListDtos(listEntities);
    }

}