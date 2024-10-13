package com.studios.core.service.studio;

import com.shared.core.service.impl.GenericCreateService;
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
@Service("createStudioImpl")
public class CreateStudioImpl extends GenericCreateService<StudioEntity, StudioDto, Integer> {

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
    public StudioEntity toEntity(StudioDto studioDto) {
        return this.studioMapper.toEntityIgnoredId(studioDto);
    }

    @Override
    public void verifyUnique(StudioDto studioDto) {
        Boolean existsName = this.studioRepository.existsByName(studioDto.getName());
        ValidateUtil.validateUnique(existsName, ValueEnum.NAME, studioDto.getName());
    }

}