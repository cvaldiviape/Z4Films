package com.shared.core.service;
import java.util.Set;
import java.util.List;

@FunctionalInterface
public interface FindAllByListIdsService<DTO, ID> {

    List<DTO> findAllByListIds(Set<ID> listIds);

}