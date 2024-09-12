package com.shared.core.service.impl;

import com.shared.core.service.FindByIdService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericFindByIdService<ENTITY, DTO, ID> implements FindByIdService<DTO, ID> {

    public abstract DTO toDto(ENTITY entity);

    public abstract ENTITY findEntityById(ID id);

    @Override
    public DTO findById(ID id) {
        ENTITY entity = this.findEntityById(id);
        return this.toDto(entity);
    }

}