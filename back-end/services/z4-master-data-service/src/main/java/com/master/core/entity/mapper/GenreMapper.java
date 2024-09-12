package com.master.core.entity.mapper;

import com.master.core.entity.GenreEntity;
import com.shared.dto.external.master.GenreDto;
import org.mapstruct.*;
import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {

    @Named("GenreMapper.toDto")
    public GenreDto toDto(GenreEntity entity);
    @Named("GenreMapper.toEntity")
    public GenreEntity toEntity(GenreDto dto);
    @Named("GenreMapper.toEntityIgnoredId")
    @Mapping(target = "genreId", ignore = true)
    public GenreEntity toEntityIgnoredId(GenreDto dto);
    @Named("GenreMapper.toListDtos")
    Collection<GenreDto> toListDtos(Collection<GenreEntity> listEntities);
    @Named("GenreMapper.toListEntities")
    Collection<GenreEntity> toListEntities(Collection<GenreDto> listDtos);
    @Named("GenreMapper.updateEntityFromDto")
    void updateEntityFromDto(GenreDto dto, @MappingTarget GenreEntity entity);
    @Named("GenreMapper.updateEntityFromDtoIgnoredId")
    @Mapping(target = "genreId", ignore = true)
    void updateEntityFromDtoIgnoredId(GenreDto dto, @MappingTarget GenreEntity entity);

}