package com.studios.core.entity.mapper;

import com.shared.dto.external.studio.StudioDto;
import com.studios.core.entity.StudioEntity;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudioMapper {

    @Named("StudioMapper.toDto")
    public StudioDto toDto(StudioEntity entity);
    @Named("StudioMapper.toEntity")
    public StudioEntity toEntity(StudioDto dto);
    @Named("StudioMapper.toEntityIgnoredId")
    @Mapping(target = "studioId", ignore = true)
    public StudioEntity toEntityIgnoredId(StudioDto dto);
    @Named("StudioMapper.toListDtos")
    List<StudioDto> toListDtos(List<StudioEntity> listEntities);
    @Named("StudioMapper.toListEntities")
    List<StudioEntity> toListEntities(List<StudioDto> listDtos);
    @Named("StudioMapper.updateEntityFromDto")
    void updateEntityFromDto(StudioDto dto, @MappingTarget StudioEntity entity);
    @Named("StudioMapper.updateEntityFromDtoIgnoredId")
    @Mapping(target = "studioId", ignore = true)
    void updateEntityFromDtoIgnoredId(StudioDto dto, @MappingTarget StudioEntity entity);

}
