package com.catalogs.core.entity.mapper;

import com.catalogs.core.entity.MovieEntity;
import com.shared.dto.external.catalog.MovieDto;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieMapper {

    @Named("MovieMapper.toDto")
    @Mapping(target = "listGenres", ignore = true)
    @Mapping(target = "listLanguages", ignore = true)
    public MovieDto toDto(MovieEntity entity);
    @Named("MovieMapper.toEntity")
    @Mapping(target = "listGenres", ignore = true)
    @Mapping(target = "listLanguages", ignore = true)
    public MovieEntity toEntity(MovieDto dto);
    @Named("MovieMapper.toListDtos")
    @IterableMapping(qualifiedByName = "MovieMapper.toDto")
    List<MovieDto> toListDtos(List<MovieEntity> listEntities);
    @Named("MovieMapper.toListEntities")
    @IterableMapping(qualifiedByName = "MovieMapper.toEntity")
    List<MovieEntity> toListEntities(List<MovieDto> listDtos);
    @Named("MovieMapper.updateEntityFromDtoIgnoredId")
    @Mapping(target = "movieId", ignore = true)
    @Mapping(target = "listGenres", ignore = true)
    @Mapping(target = "listLanguages", ignore = true)
    void updateEntityFromDtoIgnoredId(MovieDto dto, @MappingTarget MovieEntity entity);

}