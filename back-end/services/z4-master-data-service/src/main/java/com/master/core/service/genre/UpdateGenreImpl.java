package com.master.core.service.genre;

import com.master.core.entity.GenreEntity;
import com.master.core.entity.mapper.GenreMapper;
import com.master.core.repository.GenreRepository;
import com.shared.core.service.impl.GenericUpdateService;
import com.shared.dto.external.master.GenreDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("updateGenreImpl")
public class UpdateGenreImpl extends GenericUpdateService<GenreEntity, GenreDto, Integer> {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public JpaRepository<GenreEntity, Integer> getJpaRepository() {
        return this.genreRepository;
    }

    @Override
    public GenreDto toDto(GenreEntity genreEntity) {
        return this.genreMapper.toDto(genreEntity);
    }

    @Override
    public void updateEntityFromDto(GenreDto genreDto, GenreEntity genreEntity) {
        this.genreMapper.updateEntityFromDtoIgnoredId(genreDto, genreEntity);
    }

    @Override
    public GenreEntity findEntityById(Integer genreId) {
        return this.genreRepository.findById(genreId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.GENRE.getValue(), genreId));
    }

    @Override
    public void validate(Integer genreId, GenreDto genreDto) {
        Boolean existsCode = this.genreRepository.existsByCodeAndGenreIdNot(genreDto.getCode(), genreId);
        Boolean existsName = this.genreRepository.existsByNameAndGenreIdNot(genreDto.getName(), genreId);
        ValidateUtil.validateUnique(existsCode, ValueEnum.CODE, genreDto.getCode());
        ValidateUtil.validateUnique(existsName, ValueEnum.NAME, genreDto.getName());
    }

}