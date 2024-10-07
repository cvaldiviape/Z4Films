package com.catalogs.core.entity.mapper;

import com.catalogs.core.entity.SerieEntity;
import com.shared.dto.external.catalog.SerieDto;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SerieMapper {

    @Named("SerieMapper.toDto")
    @Mapping(target = "listGenres", ignore = true)
    @Mapping(target = "listLanguages", ignore = true)
    public SerieDto toDto(SerieEntity entity);
    @Named("SerieMapper.toEntity")
    @Mapping(target = "listGenres", ignore = true)
    @Mapping(target = "listLanguages", ignore = true)
    public SerieEntity toEntity(SerieDto dto);
    @Named("SerieMapper.toListDtos")
    @IterableMapping(qualifiedByName = "SerieMapper.toDto")
    List<SerieDto> toListDtos(List<SerieEntity> listEntities);
    @Named("SerieMapper.toListEntities")
    @IterableMapping(qualifiedByName = "SerieMapper.toEntity")
    List<SerieEntity> toListEntities(List<SerieDto> listDtos);
    @Named("SerieMapper.updateEntityFromDtoIgnoredId")
    @Mapping(target = "serieId", ignore = true)
    @Mapping(target = "listGenres", ignore = true)
    @Mapping(target = "listLanguages", ignore = true)
    void updateEntityFromDtoIgnoredId(SerieDto dto, @MappingTarget SerieEntity entity);

}