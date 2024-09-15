package com.master.core.entity.mapper;

import com.master.core.entity.LanguageEntity;
import com.shared.dto.external.master.LanguageDto;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LanguageMapper {

    @Named("LanguageMapper.toDto")
    public LanguageDto toDto(LanguageEntity entity);
    @Named("LanguageMapper.toEntity")
    public LanguageEntity toEntity(LanguageDto dto);
    @Named("LanguageMapper.toEntityIgnoredId")
    @Mapping(target = "languageId", ignore = true)
    public LanguageEntity toEntityIgnoredId(LanguageDto dto);
    @Named("LanguageMapper.toListDtos")
    List<LanguageDto> toListDtos(List<LanguageEntity> listEntities);
    @Named("LanguageMapper.toListEntities")
    List<LanguageEntity> toListEntities(List<LanguageDto> listDtos);
    @Named("LanguageMapper.updateEntityFromDto")
    void updateEntityFromDto(LanguageDto dto, @MappingTarget LanguageEntity entity);
    @Named("LanguageMapper.updateEntityFromDtoIgnoredId")
    @Mapping(target = "languageId", ignore = true)
    void updateEntityFromDtoIgnoredId(LanguageDto dto, @MappingTarget LanguageEntity entity);

}