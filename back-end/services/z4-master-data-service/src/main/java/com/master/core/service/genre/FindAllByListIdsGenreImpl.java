package com.master.core.service.genre;

import com.master.core.entity.GenreEntity;
import com.master.core.entity.mapper.GenreMapper;
import com.master.core.repository.GenreRepository;
import com.shared.core.service.impl.GenericFindAllByListIdsService;
import com.shared.dto.external.master.GenreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("findAllByListIdsGenreImpl")
public class FindAllByListIdsGenreImpl extends GenericFindAllByListIdsService<GenreEntity, GenreDto, Integer> {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public FindAllByListIdsGenreImpl(GenreRepository roomRespository, GenreMapper roomMapper) {
        this.genreRepository = roomRespository;
        this.genreMapper = roomMapper;
    }

    @Override
    public JpaRepository<GenreEntity, Integer> getJpaRepository() {
        return this.genreRepository;
    }

    @Override
    public List<GenreDto> toListDtos(List<GenreEntity> listEntities) {
        return this.genreMapper.toListDtos(listEntities);
    }

}