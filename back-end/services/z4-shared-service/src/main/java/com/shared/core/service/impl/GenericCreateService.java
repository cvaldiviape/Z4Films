package com.shared.core.service.impl;

import com.shared.core.service.CreateService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericCreateService<ENTITY, DTO, ID> implements CreateService<DTO> {

    public abstract JpaRepository<ENTITY, ID> getJpaRepository();

    public abstract DTO toDto(ENTITY entity);

    public abstract ENTITY toEntity(DTO dto);

    public abstract void validate(DTO dto);

    @Override
    public DTO create(DTO dto) {
        this.validate(dto);
        ENTITY entity = this.toEntity(dto);
        ENTITY entityCreated = this.getJpaRepository().save(entity);
        return this.toDto(entityCreated);
    }

}