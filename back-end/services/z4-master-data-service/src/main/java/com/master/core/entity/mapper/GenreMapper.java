package com.master.core.entity.mapper;

import com.master.core.entity.GenreEntity;
import com.shared.dto.external.master.GenreDto;
import org.mapstruct.*;
import java.util.List;

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
    List<GenreDto> toListDtos(List<GenreEntity> listEntities);
    @Named("GenreMapper.toListEntities")
    List<GenreEntity> toListEntities(List<GenreDto> listDtos);
    @Named("GenreMapper.updateEntityFromDto")
    void updateEntityFromDto(GenreDto dto, @MappingTarget GenreEntity entity);
    @Named("GenreMapper.updateEntityFromDtoIgnoredId")
    @Mapping(target = "genreId", ignore = true)
    void updateEntityFromDtoIgnoredId(GenreDto dto, @MappingTarget GenreEntity entity);

}