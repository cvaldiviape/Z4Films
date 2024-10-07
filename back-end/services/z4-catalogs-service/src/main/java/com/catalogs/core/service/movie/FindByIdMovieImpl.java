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
import com.shared.core.service.FindByIdService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.ValidateUtil;
import com.shared.utils.filter.FilterUtil;
import com.shared.utils.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findByIdMovieImpl")
public class FindByIdMovieImpl implements FindByIdService<MovieDto, Integer> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public MovieDto findById(Integer idMovie) {
        MovieEntity movieEntity = this.findMovieById(idMovie);
        return this.toDto(movieEntity);
    }

    private MovieEntity findMovieById(Integer idMovie) {
        return this.movieRepository.findById(idMovie)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.MOVIE.getValue()));
    }

    private MovieDto toDto(MovieEntity movieEntity) {
        MovieDto movieDto = this.movieMapper.toDto(movieEntity);
       this. setDataListGenres(movieEntity, movieDto);
        this.setDataListLanguages(movieEntity, movieDto);
        return movieDto;
    }

    private void setDataListGenres(MovieEntity movieEntity, MovieDto movieDto) {
        Set<Integer> listGenreIds = this.getListGenreIds(movieEntity);
        List<GenreDto> listGenresDtos = this.findAllGenresByListIds(listGenreIds);
        movieDto.setListGenres(new HashSet<>(listGenresDtos));
    }

    private void setDataListLanguages(MovieEntity movieEntity, MovieDto movieDto) {
        Set<Integer> listLanguagesIds = this.getListLanguageIds(movieEntity);
        List<LanguageDto> listLanguagesDtos = this.findAllLanguagesByListIds(listLanguagesIds);
        listLanguagesDtos = getListLanguagesFull(movieEntity, listLanguagesDtos);
        movieDto.setListLanguages(new HashSet<>(listLanguagesDtos));
    }

    private List<LanguageDto> getListLanguagesFull(MovieEntity movieEntity, List<LanguageDto> listLanguagesDtos) {
        return listLanguagesDtos.stream()
                .peek((languageDto -> this.setComplementaryDataLanguage(movieEntity, languageDto)))
                .toList();
    }

    private void setComplementaryDataLanguage(MovieEntity movieEntity, LanguageDto languageDto) {
        MovieLanguageEntity languageFound = this.findLanguage(movieEntity, languageDto);
        languageDto.setAudioFormat(languageFound.getId().getAudioFormat());
    }

    private MovieLanguageEntity findLanguage(MovieEntity movieEntity, LanguageDto languageDto) {
        return movieEntity.getListLanguages().stream()
                .filter((languageEntity) -> languageEntity.getId().getLanguageId().equals(languageDto.getLanguageId()))
                .findFirst()
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.LANGUAGE.getValue()));
    }

    private Set<Integer> getListGenreIds(MovieEntity movieEntity) {
        return movieEntity.getListGenres().stream()
                .map((genreEntity) -> genreEntity.getId().getGenreId())
                .collect(Collectors.toSet());
    }

    private List<GenreDto> findAllGenresByListIds(Set<Integer> listGenreIds) {
        ResponseDto response = this.genreClient.findAllByListIds(listGenreIds);
        return FeignUtil.extractsDataList(response, GenreDto.class, ValueEnum.LIST_GENRE.getValue());
    }

    private Set<Integer> getListLanguageIds(MovieEntity movieEntity) {
        return movieEntity.getListLanguages().stream()
                .map((genreEntity) -> genreEntity.getId().getLanguageId())
                .collect(Collectors.toSet());
    }

    private List<LanguageDto> findAllLanguagesByListIds(Set<Integer> listLanguageIds) {
        ResponseDto response = this.languageClient.findAllByListIds(listLanguageIds);
        return FeignUtil.extractsDataList(response, LanguageDto.class, ValueEnum.LIST_LANGUAGE.getValue());
    }

}