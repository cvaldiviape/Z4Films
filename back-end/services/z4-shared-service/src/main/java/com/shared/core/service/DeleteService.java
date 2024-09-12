package com.shared.core.service;

@FunctionalInterface
public interface DeleteService<DTO, ID> {

    DTO delete(ID id);

}