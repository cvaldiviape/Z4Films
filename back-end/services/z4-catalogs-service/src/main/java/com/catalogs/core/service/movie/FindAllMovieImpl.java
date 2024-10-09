package com.catalogs.core.service.movie;

import com.catalogs.core.entity.MovieEntity;
import com.catalogs.core.entity.MovieGenreEntity;
import com.catalogs.core.entity.MovieLanguageEntity;
import com.catalogs.core.entity.mapper.MovieMapper;
import com.catalogs.core.repository.MovieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.catalogs.utils.MediaGenericFindAllService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findAllMovieImpl")
public class FindAllMovieImpl extends MediaGenericFindAllService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public JpaRepository<MovieEntity, Integer> getJpaRepository() {
        return this.movieRepository;
    }

    @Override
    public GenreClient getGenreClient() {
        return this.genreClient;
    }

    @Override
    public LanguageClient getLanguageClient() {
        return this.languageClient;
    }

    @Override
    public MovieDto toDto(MovieEntity entity) {
        return this.movieMapper.toDto(entity);
    }

    @Override
    public Set<Integer> getListGenresIds(MovieEntity entity) {
        return entity.getListGenres().stream()
                .map((language) -> language.getId().getGenreId())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getListLanguageIds(MovieEntity entity) {
        return entity.getListLanguages().stream()
                .map((language) -> language.getId().getLanguageId())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GenreDto> getListGenres(MovieEntity entity) {
        return entity.getListGenres().stream()
                .map((genreEntity) -> {
                    return GenreDto.builder()
                            .genreId(genreEntity.getId().getGenreId())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<LanguageDto> getListLanguages(MovieEntity entity) {
        return entity.getListLanguages().stream()
                .map((languageEntity) -> {
                    return LanguageDto.builder()
                            .languageId(languageEntity.getId().getLanguageId())
                            .audioFormat(languageEntity.getId().getAudioFormat())
                            .build();
                })
                .collect(Collectors.toSet());
    }

}