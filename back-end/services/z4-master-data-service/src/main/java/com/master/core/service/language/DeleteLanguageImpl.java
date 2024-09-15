package com.master.core.service.language;

import com.master.core.entity.LanguageEntity;
import com.master.core.entity.mapper.LanguageMapper;
import com.master.core.repository.LanguageRepository;
import com.shared.core.service.impl.GenericDeleteService;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("deleteLanguageImpl")
public class DeleteLanguageImpl extends GenericDeleteService<LanguageEntity, LanguageDto, Integer> {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    public JpaRepository<LanguageEntity, Integer> getJpaRepository() {
        return this.languageRepository;
    }

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