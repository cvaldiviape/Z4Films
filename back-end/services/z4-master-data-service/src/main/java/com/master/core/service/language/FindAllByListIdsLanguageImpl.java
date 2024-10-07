package com.master.core.service.language;

import com.master.core.entity.LanguageEntity;
import com.master.core.entity.mapper.LanguageMapper;
import com.master.core.repository.LanguageRepository;
import com.shared.core.service.impl.GenericFindAllByListIdsService;
import com.shared.dto.external.master.LanguageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("findAllByListIdsLanguageImpl")
public class FindAllByListIdsLanguageImpl extends GenericFindAllByListIdsService<LanguageEntity, LanguageDto, Integer> {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    public FindAllByListIdsLanguageImpl(LanguageRepository languageRepository, LanguageMapper languageMapper) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
    }

    @Override
    public JpaRepository<LanguageEntity, Integer> getJpaRepository() {
        return this.languageRepository;
    }

    @Override
    public List<LanguageDto> toListDtos(List<LanguageEntity> listEntities) {
        return this.languageMapper.toListDtos(listEntities);
    }

}