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
import java.util.UUID;

@Transactional
@AllArgsConstructor
@Service("deleteMovieImpl")
public class DeleteMovieImpl extends GenericDeleteService<MovieEntity, MovieDto, UUID> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public JpaRepository<MovieEntity, UUID> getJpaRepository() {
        return this.movieRepository;
    }

    @Override
    public MovieDto toDto(MovieEntity movieEntity) {
        return this.movieMapper.toDto(movieEntity);
    }

    @Override
    public MovieEntity findEntityById(UUID movieId) {
        return this.movieRepository.findById(movieId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.MOVIE.getValue()));
    }

}