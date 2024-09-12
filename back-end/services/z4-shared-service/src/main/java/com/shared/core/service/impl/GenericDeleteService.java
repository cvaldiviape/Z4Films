package com.shared.core.service.impl;

import com.shared.core.service.DeleteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericDeleteService<ENTITY, DTO, ID> implements DeleteService<DTO, ID> {

    public abstract JpaRepository<ENTITY, ID> getJpaRepository();

    public abstract DTO toDto(ENTITY entity);

    public abstract ENTITY findEntityById(ID id);

    @Override
    public DTO delete(ID id) {
        ENTITY entity = this.findEntityById(id);
        this.getJpaRepository().delete(entity);
        return this.toDto(entity);
    }

}