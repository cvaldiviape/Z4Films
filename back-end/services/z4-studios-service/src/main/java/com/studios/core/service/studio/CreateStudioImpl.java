package com.studios.core.service.studio;

import com.shared.core.service.impl.GenericCreateService;
import com.shared.dto.external.master.CountryDto;
import com.shared.dto.external.studio.StudioDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.ValidateUtil;
import com.shared.utils.response.ResponseDto;
import com.studios.core.entity.StudioEntity;
import com.studios.core.entity.mapper.StudioMapper;
import com.studios.core.repository.StudioRepository;
import com.studios.external.client.CountryClient;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("createStudioImpl")
public class CreateStudioImpl extends GenericCreateService<StudioEntity, StudioDto, Integer> {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;
    private final CountryClient countryClient;

    @Override
    public JpaRepository<StudioEntity, Integer> getJpaRepository() {
        return this.studioRepository;
    }

    @Override
    public StudioDto toDto(StudioEntity studioEntity) {
        StudioDto studioDto = this.studioMapper.toDto(studioEntity);
        this.setComplementaryData(studioDto);
        return studioDto;
    }

    private void setComplementaryData(StudioDto studioDto) {
        ResponseDto response = countryClient.findById(studioDto.getCountryId());
        CountryDto countryDto = FeignUtil.extracstData(response, CountryDto.class, ValueEnum.COUNTRY.getValue());

        studioDto.setCountry(countryDto);
    }

    @Override
    public StudioEntity toEntity(StudioDto studioDto) {
        return this.studioMapper.toEntityIgnoredId(studioDto);
    }

    @Override
    public void validate(StudioDto studioDto) {
        Boolean existsName = this.studioRepository.existsByName(studioDto.getName());
        ValidateUtil.validateUnique(existsName, ValueEnum.NAME, studioDto.getName());

        ResponseDto response = countryClient.findById(studioDto.getCountryId());
        FeignUtil.extracstData(response, CountryDto.class, ValueEnum.COUNTRY.getValue());
    }

}