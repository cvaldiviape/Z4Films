package com.catalogs.core.service.movie;

import com.catalogs.core.entity.MovieEntity;
import com.catalogs.core.entity.mapper.MovieMapper;
import com.catalogs.core.repository.MovieRepository;
import com.shared.core.service.DeleteService;
import com.shared.core.service.impl.GenericDeleteService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service("deleteMovieImpl")
public class DeleteMovieImpl extends GenericDeleteService<MovieEntity, MovieDto, Integer> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public JpaRepository<MovieEntity, Integer> getJpaRepository() {
        return this.movieRepository;
    }

    @Override
    public MovieDto toDto(MovieEntity movieEntity) {
        return this.movieMapper.toDto(movieEntity);
    }

    @Override
    public MovieEntity findEntityById(Integer idMovie) {
        return this.movieRepository.findById(idMovie)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.MOVIE.getValue()));
    }

}