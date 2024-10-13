package com.studios.core.service.studio;

import com.shared.core.service.impl.GenericFindByIdService;
import com.shared.dto.external.studio.StudioDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import com.studios.core.entity.StudioEntity;
import com.studios.core.entity.mapper.StudioMapper;
import com.studios.core.repository.StudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("findByIdStudioImpl")
public class FindByIdStudioImpl extends GenericFindByIdService<StudioEntity, StudioDto, Integer> {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;

    @Override
    public StudioDto toDto(StudioEntity studioEntity) {
        return this.studioMapper.toDto(studioEntity);
    }

    @Override
    public StudioEntity findEntityById(Integer studioId) {
        return this.studioRepository.findById(studioId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.STUDIO.getValue(), studioId));
    }

}