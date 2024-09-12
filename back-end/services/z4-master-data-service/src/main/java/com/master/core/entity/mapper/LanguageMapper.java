package com.master.core.entity.mapper;

import com.master.core.entity.LanguageEntity;
import com.shared.dto.external.master.LanguageDto;
import org.mapstruct.*;
import java.util.Collection;

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
    Collection<LanguageDto> toListDtos(Collection<LanguageEntity> listEntities);
    @Named("LanguageMapper.toListEntities")
    Collection<LanguageEntity> toListEntities(Collection<LanguageDto> listDtos);
    @Named("LanguageMapper.updateEntityFromDto")
    void updateEntityFromDto(LanguageDto dto, @MappingTarget LanguageEntity entity);
    @Named("LanguageMapper.updateEntityFromDtoIgnoredId")
    @Mapping(target = "languageId", ignore = true)
    void updateEntityFromDtoIgnoredId(LanguageDto dto, @MappingTarget LanguageEntity entity);

}