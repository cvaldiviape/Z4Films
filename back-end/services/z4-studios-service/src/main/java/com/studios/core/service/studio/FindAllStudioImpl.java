package com.studios.core.service.studio;

import com.shared.core.service.impl.GenericFindAllService;
import com.shared.dto.external.studio.StudioDto;
import com.studios.core.entity.StudioEntity;
import com.studios.core.entity.mapper.StudioMapper;
import com.studios.core.repository.StudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service("findAllStudioImpl")
public class FindAllStudioImpl extends GenericFindAllService<StudioEntity, StudioDto, Integer> {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;

    @Override
    public JpaRepository<StudioEntity, Integer> getJpaRepository() {
        return this.studioRepository;
    }

    @Override
    public List<StudioDto> toListDtos(List<StudioEntity> listEntities) {
        return this.studioMapper.toListDtos(listEntities);
    }

}