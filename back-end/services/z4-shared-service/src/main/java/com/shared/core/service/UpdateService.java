package com.shared.core.service;

@FunctionalInterface
public interface UpdateService<DTO, ID> {

    DTO update(ID id, DTO dto);

}