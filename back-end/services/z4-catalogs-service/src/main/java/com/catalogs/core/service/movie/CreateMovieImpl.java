package com.catalogs.core.service.movie;

import com.catalogs.core.entity.MovieEntity;
import com.catalogs.core.entity.MovieGenreEntity;
import com.catalogs.core.entity.MovieLanguageEntity;
import com.catalogs.core.entity.ids.MovieGenreId;
import com.catalogs.core.entity.ids.MovieLanguageId;
import com.catalogs.core.entity.mapper.MovieMapper;
import com.catalogs.core.repository.MovieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.shared.core.service.CreateService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.filter.FilterUtil;
import com.shared.utils.response.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service("createMovieImpl")
public class CreateMovieImpl implements CreateService<MovieDto> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public MovieDto create(MovieDto movieDto) {
        this.validateRelationships(movieDto);
        MovieEntity movieEntityCreated = this.createMovie(movieDto);
        return this.toDto(movieDto, movieEntityCreated);
    }

    private void validateRelationships(MovieDto movieDto) {
        Set<Integer> listGenreIds = this.getListGenreIds(movieDto);
        List<GenreDto> listGenresFound = this.findAllGenresByListIds(listGenreIds);
        this.verifyExistenceOfGenres(listGenreIds, listGenresFound);

        Set<Integer> listLanguageIds = getListLanguageIds(movieDto);
        List<LanguageDto> listLanguagesFound = this.findAllLanguagesByListIds(listLanguageIds);
        this.verifyExistenceOfLanguages(listLanguageIds, listLanguagesFound);
    }

    private void verifyExistenceOfGenres(Set<Integer> listGenreIds, List<GenreDto> listGenresFound) {
        listGenreIds.forEach(genreId -> FilterUtil.find(listGenresFound, genreId, ValueEnum.GENRE.getValue()));
    }

    private void verifyExistenceOfLanguages(Set<Integer> listLanguageIds, List<LanguageDto> listLanguageFound) {
        listLanguageIds.forEach(languageId -> FilterUtil.find(listLanguageFound, languageId, ValueEnum.LANGUAGE.getValue()));
    }

    private MovieEntity createMovie(MovieDto movieDto) {
        MovieEntity movieEntity = this.movieMapper.toEntity(movieDto);
        this.movieRepository.save(movieEntity);

        Set<Integer> listGenreIds = this.getListGenreIds(movieDto);
        Set<MovieGenreEntity> listGenresEntities = this.buildListGenresEntities(listGenreIds, movieEntity);
        movieEntity.setListGenres(listGenresEntities);

        Set<LanguageDto> listLanguages = movieDto.getListLanguages();
        Set<MovieLanguageEntity> listLanguagesEntities = this.buildListLanguagesEntities(listLanguages, movieEntity);
        movieEntity.setListLanguages(listLanguagesEntities);

        return this.movieRepository.save(movieEntity);
    }

    private Set<MovieGenreEntity> buildListGenresEntities(Set<Integer> listGenreIds, MovieEntity movieEntity) {
        return listGenreIds.stream().map(genreId -> {
            MovieGenreId id = MovieGenreId.builder()
                    .genreId(genreId)
                    .movieId(movieEntity.getMovieId())
                    .build();

            return MovieGenreEntity.builder()
                    .id(id)
                    .movie(movieEntity)
                    .build();
        }).collect(Collectors.toSet());
    }

    private Set<MovieLanguageEntity> buildListLanguagesEntities(Set<LanguageDto> listLanguages, MovieEntity movieEntity) {
        return listLanguages.stream().map(language -> {
            MovieLanguageId id = MovieLanguageId.builder()
                    .languageId(language.getLanguageId())
                    .audioFormat(language.getAudioFormat())
                    .movieId(movieEntity.getMovieId())
                    .build();

            return MovieLanguageEntity.builder()
                    .id(id)
                    .movie(movieEntity)
                    .build();
        }).collect(Collectors.toSet());
    }

    private MovieDto toDto(MovieDto movieDto, MovieEntity movieEntityCreated) {
        MovieDto movieDtoCreated = this.movieMapper.toDto(movieEntityCreated);
        this.setDataListGenres(movieDto, movieDtoCreated);
        this.setDataListLanguages(movieDto, movieDtoCreated);
        return movieDtoCreated;
    }

    private void setDataListGenres(MovieDto movieDto, MovieDto movieDtoCreated) {
        Set<Integer> listGenreIds = this.getListGenreIds(movieDto);
        List<GenreDto> listGenresDtos = this.findAllGenresByListIds(listGenreIds);
        movieDtoCreated.setListGenres(new HashSet<>(listGenresDtos));
    }

    private Set<Integer> getListGenreIds(MovieDto movieDto) {
        return movieDto.getListGenres()
                .stream().map(GenreDto::getGenreId)
                .collect(Collectors.toSet());
    }

    private List<GenreDto> findAllGenresByListIds(Set<Integer> listGenreIds) {
        ResponseDto response = this.genreClient.findAllByListIds(listGenreIds);
        return FeignUtil.extractsDataList(response, GenreDto.class, ValueEnum.LIST_GENRE.getValue());
    }

    private void setDataListLanguages(MovieDto movieDto, MovieDto movieDtoCreated) {
        Set<Integer> listLanguagesIds = this.getListLanguageIds(movieDto);
        List<LanguageDto> listLanguagesDtos = this.findAllLanguagesByListIds(listLanguagesIds);
        listLanguagesDtos = this.getListLanguagesFull(movieDto, listLanguagesDtos);
        movieDtoCreated.setListLanguages(new HashSet<>(listLanguagesDtos));
    }

    private Set<Integer> getListLanguageIds(MovieDto movieDto) {
        return movieDto.getListLanguages().stream()
                .map(LanguageDto::getLanguageId)
                .collect(Collectors.toSet());
    }

    private List<LanguageDto> findAllLanguagesByListIds(Set<Integer> listLanguageIds) {
        ResponseDto response = this.languageClient.findAllByListIds(listLanguageIds);
        return FeignUtil.extractsDataList(response, LanguageDto.class, ValueEnum.LIST_LANGUAGE.getValue());
    }

    private List<LanguageDto> getListLanguagesFull(MovieDto movieDto, List<LanguageDto> listLanguagesDtos) {
        return listLanguagesDtos.stream()
                .peek(languageDto -> this.setComplementaryDataLanguage(languageDto, movieDto))
                .toList();
    }

    private void setComplementaryDataLanguage(LanguageDto languageDto, MovieDto movieDto) {
        LanguageDto languageFound = FilterUtil.find(new ArrayList<>(movieDto.getListLanguages()), languageDto.getLanguageId(), ValueEnum.LANGUAGE.getValue());
        languageDto.setAudioFormat(languageFound.getAudioFormat());
    }

}