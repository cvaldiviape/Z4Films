package com.master.core.service.genre;

import com.master.core.entity.GenreEntity;
import com.master.core.entity.mapper.GenreMapper;
import com.master.core.repository.GenreRepository;
import com.shared.core.service.impl.GenericCreateService;
import com.shared.dto.external.master.GenreDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("createGenreImpl")
public class CreateGenreImpl extends GenericCreateService<GenreEntity, GenreDto, Integer> {

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
    public GenreEntity toEntity(GenreDto genreDto) {
        return this.genreMapper.toEntityIgnoredId(genreDto);
    }

    @Override
    public void verifyUnique(GenreDto genreDto) {
        Boolean existsCode = this.genreRepository.existsByCode(genreDto.getCode());
        Boolean existsName = this.genreRepository.existsByName(genreDto.getName());
        ValidateUtil.validateUnique(existsCode, ValueEnum.CODE, genreDto.getCode());
        ValidateUtil.validateUnique(existsName, ValueEnum.NAME, genreDto.getName());
    }

}