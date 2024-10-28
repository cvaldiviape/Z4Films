package com.catalogs.core.service.movie;

import com.catalogs.core.entity.MovieEntity;
import com.catalogs.core.entity.MovieGenreEntity;
import com.catalogs.core.entity.MovieLanguageEntity;
import com.catalogs.core.entity.mapper.MovieMapper;
import com.catalogs.core.repository.MovieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.catalogs.utils.MediaGenericFindByIdService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findByIdMovieImpl")
public class FindByIdMovieImpl extends MediaGenericFindByIdService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, UUID> {

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
    public MovieEntity findEntityById(UUID movieId) {
        return this.movieRepository.findById(movieId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.MOVIE.getValue()));
    }

    @Override
    public void setComplementaryDataLanguage(MovieEntity entity, LanguageDto languageDto) {
        MovieLanguageEntity languageFound = this.findLanguage(entity, languageDto);
        languageDto.setAudioFormat(languageFound.getId().getAudioFormat());
    }

    private MovieLanguageEntity findLanguage(MovieEntity entity, LanguageDto languageDto) {
        return entity.getListLanguages().stream()
                .filter((languageEntity) -> languageEntity.getId().getLanguageId().equals(languageDto.getLanguageId()))
                .findFirst()
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.LANGUAGE.getValue()));
    }

    @Override
    public Set<Integer> getListGenreIds(MovieEntity entity) {
        return entity.getListGenres().stream()
                .map((genreEntity) -> genreEntity.getId().getGenreId())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getListLanguageIds(MovieEntity entity) {
        return entity.getListLanguages().stream()
                .map((genreEntity) -> genreEntity.getId().getLanguageId())
                .collect(Collectors.toSet());
    }

}