package com.shared.core.service.impl;

import com.shared.core.service.UpdateService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericUpdateService<ENTITY, DTO, ID> implements UpdateService<DTO, ID> {

    public abstract JpaRepository<ENTITY, ID> getJpaRepository();

    public abstract DTO toDto(ENTITY entity);

    public abstract void updateEntityFromDto(DTO dto, ENTITY entity);

    public abstract ENTITY findEntityById(ID id);

    public abstract void verifyUnique(ID id, DTO dto);

    @Override
    public DTO update(ID id, DTO dto) {
        ENTITY entity = this.findEntityById(id);
        this.verifyUnique(id, dto);
        this.updateEntityFromDto(dto, entity);
        ENTITY entityUpdated = this.getJpaRepository().save(entity);
        return this.toDto(entityUpdated);
    }

}