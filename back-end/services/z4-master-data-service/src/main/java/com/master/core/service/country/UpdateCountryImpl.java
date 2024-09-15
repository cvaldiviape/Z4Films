package com.master.core.service.country;

import com.master.core.entity.CountryEntity;
import com.master.core.entity.mapper.CountryMapper;
import com.master.core.repository.CountryRepository;
import com.shared.core.service.impl.GenericUpdateService;
import com.shared.dto.external.master.CountryDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("updateCountryImpl")
public class UpdateCountryImpl extends GenericUpdateService<CountryEntity, CountryDto, Integer> {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public JpaRepository<CountryEntity, Integer> getJpaRepository() {
        return this.countryRepository;
    }

    @Override
    public CountryDto toDto(CountryEntity countryEntity) {
        return this.countryMapper.toDto(countryEntity);
    }

    @Override
    public void updateEntityFromDto(CountryDto countryDto, CountryEntity countryEntity) {
        this.countryMapper.updateEntityFromDtoIgnoredId(countryDto, countryEntity);
    }

    @Override
    public CountryEntity findEntityById(Integer countryId) {
        return this.countryRepository.findById(countryId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.COUNTRY.getValue(), countryId));
    }

    @Override
    public void verifyUnique(Integer countryId, CountryDto countryDto) {
        Boolean existsCode = this.countryRepository.existsByCodeAndCountryIdNot(countryDto.getCode(), countryId);
        Boolean existsName = this.countryRepository.existsByNameAndCountryIdNot(countryDto.getName(), countryId);
        ValidateUtil.validateUnique(existsCode, ValueEnum.CODE, countryDto.getCode());
        ValidateUtil.validateUnique(existsName, ValueEnum.NAME, countryDto.getName());
    }

}