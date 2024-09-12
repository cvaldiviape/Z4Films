package com.master.core.service.country;

import com.master.core.entity.CountryEntity;
import com.master.core.entity.mapper.CountryMapper;
import com.master.core.repository.CountryRepository;
import com.shared.core.service.impl.GenericFindByIdService;
import com.shared.dto.external.master.CountryDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("findByIdCountryImpl")
public class FindByIdCountryImpl extends GenericFindByIdService<CountryEntity, CountryDto, Integer> {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public CountryDto toDto(CountryEntity countryEntity) {
        return this.countryMapper.toDto(countryEntity);
    }

    @Override
    public CountryEntity findEntityById(Integer idCountry) {
        return this.countryRepository.findById(idCountry)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.COUNTRY.getValue(), idCountry));
    }

}