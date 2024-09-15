package com.master.core.service.genre;

import com.master.core.entity.GenreEntity;
import com.master.core.entity.mapper.GenreMapper;
import com.master.core.repository.GenreRepository;
import com.shared.core.service.impl.GenericFindAllService;
import com.shared.dto.external.master.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service("findAllGenreImpl")
public class FindAllGenreImpl extends GenericFindAllService<GenreEntity, GenreDto, Integer> {

    private final GenreRepository countryRepository;
    private final GenreMapper countryMapper;

    @Override
    public JpaRepository<GenreEntity, Integer> getJpaRepository() {
        return this.countryRepository;
    }

    @Override
    public List<GenreDto> toListDtos(List<GenreEntity> listEntities) {
        return this.countryMapper.toListDtos(listEntities);
    }

}