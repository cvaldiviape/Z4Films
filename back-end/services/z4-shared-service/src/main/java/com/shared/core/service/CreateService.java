package com.shared.core.service;

@FunctionalInterface
public interface CreateService<DTO> {

    DTO create(DTO dto);

}