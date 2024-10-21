package com.catalogs.core.repository;

import com.catalogs.core.entity.SerieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SerieRepository extends JpaRepository<SerieEntity, UUID> {

}