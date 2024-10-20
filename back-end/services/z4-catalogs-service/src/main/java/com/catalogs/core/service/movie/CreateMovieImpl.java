package com.catalogs.core.service.movie;

import com.catalogs.core.entity.*;
import com.catalogs.core.entity.ids.MovieGenreId;
import com.catalogs.core.entity.ids.MovieLanguageId;
import com.catalogs.core.entity.mapper.MovieMapper;
import com.catalogs.core.repository.MovieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.catalogs.utils.MediaGenericCreateService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.external.master.LanguageDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service("createMovieImpl")
public class CreateMovieImpl extends MediaGenericCreateService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, UUID> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public JpaRepository<MovieEntity, UUID> getJpaRepository() {
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
    public MovieEntity toEntity(MovieDto dto) {
        return this.movieMapper.toEntity(dto);
    }

    @Override
    public void generateId(MovieEntity entity) {
        UUID uuid = UUID.randomUUID();
        entity.setMovieId(uuid);
    }

    @Override
    public Set<MovieGenreEntity> buildListGenresEntities(Set<Integer> listGenreIds, MovieEntity entity) {
        return listGenreIds.stream().map(genreId -> {
            MovieGenreId id = MovieGenreId.builder()
                    .genreId(genreId)
                    .movieId(entity.getMovieId())
                    .build();

            return MovieGenreEntity.builder()
                    .id(id)
                    .movie(entity)
                    .build();
        }).collect(Collectors.toSet());
    }

    @Override
    public Set<MovieLanguageEntity> buildListLanguagesEntities(Set<LanguageDto> listLanguages, MovieEntity entity) {
        return listLanguages.stream().map(language -> {
            MovieLanguageId id = MovieLanguageId.builder()
                    .languageId(language.getLanguageId())
                    .audioFormat(language.getAudioFormat())
                    .movieId(entity.getMovieId())
                    .build();

            return MovieLanguageEntity.builder()
                    .id(id)
                    .movie(entity)
                    .build();
        }).collect(Collectors.toSet());
    }

}