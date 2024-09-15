package com.shared.core.service;

@FunctionalInterface
public interface FindByIdService<DTO, ID> {

    DTO findById(ID id);

}