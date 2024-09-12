package com.master.core.service.genre;

import com.master.core.entity.GenreEntity;
import com.master.core.entity.mapper.GenreMapper;
import com.master.core.repository.GenreRepository;
import com.shared.core.service.impl.GenericFindByIdService;
import com.shared.dto.external.master.GenreDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("findByIdGenreImpl")
public class FindByIdGenreImpl extends GenericFindByIdService<GenreEntity, GenreDto, Integer> {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreDto toDto(GenreEntity genreEntity) {
        return this.genreMapper.toDto(genreEntity);
    }

    @Override
    public GenreEntity findEntityById(Integer genreId) {
        return this.genreRepository.findById(genreId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.GENRE.getValue(), genreId));
    }

}