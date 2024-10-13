package com.studios.core.service.studio;

import com.shared.core.service.impl.GenericFindAllService;
import com.shared.dto.external.master.CountryDto;
import com.shared.dto.external.studio.StudioDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.filter.FilterUtil;
import com.shared.utils.response.ResponseDto;
import com.studios.core.entity.StudioEntity;
import com.studios.core.entity.mapper.StudioMapper;
import com.studios.core.repository.StudioRepository;
import com.studios.external.client.CountryClient;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findAllStudioImpl")
public class FindAllStudioImpl extends GenericFindAllService<StudioEntity, StudioDto, Integer> {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;
    private final CountryClient countryClient;

    @Override
    public JpaRepository<StudioEntity, Integer> getJpaRepository() {
        return this.studioRepository;
    }

    @Override
    public List<StudioDto> toListDtos(List<StudioEntity> listEntities) {
        List<StudioDto> listDtos = this.studioMapper.toListDtos(listEntities);
        this.setComplementaryData(listDtos);
        return listDtos;
    }

    private void setComplementaryData(List<StudioDto> listDtos) {
        Set<Integer> listCounrtryIds = getListCountryIds(listDtos);
        List<CountryDto> listCountries = findAllCountriesByListIds(listCounrtryIds);

        for (StudioDto dto : listDtos) {
            CountryDto country = FilterUtil.find(listCountries, dto.getCountryId(), ValueEnum.COUNTRY.getValue());
            dto.setCountry(country);
        }
    }

    private Set<Integer> getListCountryIds(List<StudioDto> listDtos) {
        return listDtos.stream()
                .map(StudioDto::getCountryId)
                .collect(Collectors.toSet());
    }

    private List<CountryDto> findAllCountriesByListIds(Set<Integer> listCounrtryIds) {
        ResponseDto response = countryClient.findAllByListIds(listCounrtryIds);
        return FeignUtil.extractsDataList(response, CountryDto.class, ValueEnum.LIST_COUNTRY.getValue());
    }

}