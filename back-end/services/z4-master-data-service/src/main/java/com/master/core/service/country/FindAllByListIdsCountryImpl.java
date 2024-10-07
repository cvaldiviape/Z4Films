package com.master.core.service.country;

import com.master.core.entity.CountryEntity;
import com.master.core.entity.mapper.CountryMapper;
import com.master.core.repository.CountryRepository;
import com.shared.core.service.impl.GenericFindAllByListIdsService;
import com.shared.dto.external.master.CountryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("findAllByListIdsCountryImpl")
public class FindAllByListIdsCountryImpl extends GenericFindAllByListIdsService<CountryEntity, CountryDto, Integer> {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public FindAllByListIdsCountryImpl(CountryRepository roomRespository, CountryMapper roomMapper) {
        this.countryRepository = roomRespository;
        this.countryMapper = roomMapper;
    }

    @Override
    public JpaRepository<CountryEntity, Integer> getJpaRepository() {
        return this.countryRepository;
    }

    @Override
    public List<CountryDto> toListDtos(List<CountryEntity> listEntities) {
        return this.countryMapper.toListDtos(listEntities);
    }

}