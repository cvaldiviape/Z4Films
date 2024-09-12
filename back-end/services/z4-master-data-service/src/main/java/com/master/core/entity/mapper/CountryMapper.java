package com.master.core.entity.mapper;

import com.master.core.entity.CountryEntity;
import com.shared.dto.external.master.CountryDto;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {

    @Named("CountryMapper.toDto")
    public CountryDto toDto(CountryEntity entity);
    @Named("CountryMapper.toEntity")
    public CountryEntity toEntity(CountryDto dto);
    @Named("CountryMapper.toEntityIgnoredId")
    @Mapping(target = "countryId", ignore = true)
    public CountryEntity toEntityIgnoredId(CountryDto dto);
    @Named("CountryMapper.toListDtos")
    List<CountryDto> toListDtos(List<CountryEntity> listEntities);
    @Named("CountryMapper.toListEntities")
    List<CountryEntity> toListEntities(List<CountryDto> listDtos);
    @Named("CountryMapper.updateEntityFromDto")
    void updateEntityFromDto(CountryDto dto, @MappingTarget CountryEntity entity);
    @Named("CountryMapper.updateEntityFromDtoIgnoredId")
    @Mapping(target = "countryId", ignore = true)
    void updateEntityFromDtoIgnoredId(CountryDto dto, @MappingTarget CountryEntity entity);

}