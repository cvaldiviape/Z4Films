package com.studios.core.service.studio;

import com.shared.core.service.impl.GenericUpdateService;
import com.shared.dto.external.studio.StudioDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import com.studios.core.entity.StudioEntity;
import com.studios.core.entity.mapper.StudioMapper;
import com.studios.core.repository.StudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("updateStudioImpl")
public class UpdateStudioImpl extends GenericUpdateService<StudioEntity, StudioDto, Integer> {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;

    @Override
    public JpaRepository<StudioEntity, Integer> getJpaRepository() {
        return this.studioRepository;
    }

    @Override
    public StudioDto toDto(StudioEntity studioEntity) {
        return this.studioMapper.toDto(studioEntity);
    }

    @Override
    public void updateEntityFromDto(StudioDto studioDto, StudioEntity studioEntity) {
        this.studioMapper.updateEntityFromDtoIgnoredId(studioDto, studioEntity);
    }

    @Override
    public StudioEntity findEntityById(Integer studioId) {
        return this.studioRepository.findById(studioId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.STUDIO.getValue(), studioId));
    }

    @Override
    public void verifyUnique(Integer studioId, StudioDto studioDto) {
        Boolean existsName = this.studioRepository.existsByNameAndStudioIdNot(studioDto.getName(), studioId);
        ValidateUtil.validateUnique(existsName, ValueEnum.NAME, studioDto.getName());
    }

}