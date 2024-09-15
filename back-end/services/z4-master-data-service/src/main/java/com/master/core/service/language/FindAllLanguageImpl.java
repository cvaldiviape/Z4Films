package com.master.core.service.language;

import com.master.core.entity.LanguageEntity;
import com.master.core.entity.mapper.LanguageMapper;
import com.master.core.repository.LanguageRepository;
import com.shared.core.service.impl.GenericFindAllService;
import com.shared.dto.external.master.LanguageDto;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service("findAllLanguageImpl")
public class FindAllLanguageImpl extends GenericFindAllService<LanguageEntity, LanguageDto, Integer> {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    public JpaRepository<LanguageEntity, Integer> getJpaRepository() {
        return this.languageRepository;
    }

    @Override
    public List<LanguageDto> toListDtos(List<LanguageEntity> listEntities) {
        return this.languageMapper.toListDtos(listEntities);
    }

}