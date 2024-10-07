package com.catalogs.core.service.movie;

import com.catalogs.core.entity.MovieEntity;
import com.catalogs.core.entity.mapper.MovieMapper;
import com.catalogs.core.repository.MovieRepository;
import com.shared.core.service.DeleteService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service("deleteMovieImpl")
public class DeleteMovieImpl implements DeleteService<MovieDto, Integer> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieDto delete(Integer idMovie) {
        MovieEntity movieEntity = this.findMovieById(idMovie);
        this.movieRepository.delete(movieEntity);
        return this.movieMapper.toDto(movieEntity);
    }

    private MovieEntity findMovieById(Integer idMovie) {
        return this.movieRepository.findById(idMovie)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.MOVIE.getValue()));
    }

}