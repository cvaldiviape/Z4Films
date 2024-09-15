package com.shared.core.service;

import java.util.List;

@FunctionalInterface
public interface FindAllService<DTO> {

    List<DTO> findAll();

}