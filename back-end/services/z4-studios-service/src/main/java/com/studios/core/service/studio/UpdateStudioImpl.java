package com.studios.core.service.studio;

import com.shared.core.service.impl.GenericUpdateService;
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
@Service("updateStudioImpl")
public class UpdateStudioImpl extends GenericUpdateService<StudioEntity, StudioDto, Integer> {

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
    public void updateEntityFromDto(StudioDto studioDto, StudioEntity studioEntity) {
        this.studioMapper.updateEntityFromDtoIgnoredId(studioDto, studioEntity);
    }

    @Override
    public StudioEntity findEntityById(Integer studioId) {
        return this.studioRepository.findById(studioId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.STUDIO.getValue(), studioId));
    }

    @Override
    public void validate(Integer studioId, StudioDto studioDto) {
        Boolean existsName = this.studioRepository.existsByNameAndStudioIdNot(studioDto.getName(), studioId);
        ValidateUtil.validateUnique(existsName, ValueEnum.NAME, studioDto.getName());

        ResponseDto response = countryClient.findById(studioDto.getCountryId());
        FeignUtil.extracstData(response, CountryDto.class, ValueEnum.COUNTRY.getValue());
    }

}