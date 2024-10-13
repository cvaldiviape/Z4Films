package com.master.core.service.country;

import com.master.core.entity.CountryEntity;
import com.master.core.entity.mapper.CountryMapper;
import com.master.core.repository.CountryRepository;
import com.shared.core.service.impl.GenericDeleteService;
import com.shared.dto.external.master.CountryDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("deleteCountryImpl")
public class DeleteCountryImpl extends GenericDeleteService<CountryEntity, CountryDto, Integer> {

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
    public CountryEntity findEntityById(Integer countryId) {
        return this.countryRepository.findById(countryId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.COUNTRY.getValue(), countryId));
    }

}