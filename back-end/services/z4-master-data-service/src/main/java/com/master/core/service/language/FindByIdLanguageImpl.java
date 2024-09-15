package com.master.core.service.language;

import com.master.core.entity.LanguageEntity;
import com.master.core.entity.mapper.LanguageMapper;
import com.master.core.repository.LanguageRepository;
import com.shared.core.service.impl.GenericFindByIdService;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("findByIdLanguageImpl")
public class FindByIdLanguageImpl extends GenericFindByIdService<LanguageEntity, LanguageDto, Integer> {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    public LanguageDto toDto(LanguageEntity languageEntity) {
        return this.languageMapper.toDto(languageEntity);
    }

    @Override
    public LanguageEntity findEntityById(Integer languageId) {
        return this.languageRepository.findById(languageId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.LANGUAGE.getValue(), languageId));
    }

}