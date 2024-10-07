package com.catalogs.core.service.movie;

import com.catalogs.core.entity.MovieEntity;
import com.catalogs.core.entity.mapper.MovieMapper;
import com.catalogs.core.repository.MovieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.fasterxml.jackson.databind.util.EnumValues;
import com.shared.core.service.FindAllService;
import com.shared.dto.external.catalog.MovieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.filter.FilterUtil;
import com.shared.utils.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.support.FeignUtils;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findAllMovieImpl")
public class FindAllMovieImpl implements FindAllService<MovieDto> {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public List<MovieDto> findAll() {
        List<MovieEntity> listMovieEntities = this.movieRepository.findAll();

        Set<Integer> listGenreIdsFinal = new HashSet<>();
        Set<Integer> listLanguageIdsFinal = new HashSet<>();

        for (MovieEntity movieEntity : listMovieEntities) {
            Set<Integer> listGenreIds = this.getListGenresIds(movieEntity);
            listGenreIdsFinal.addAll(listGenreIds);

            Set<Integer> listLanguageIds = this.getListLanguageIds(movieEntity);
            listLanguageIdsFinal.addAll(listLanguageIds);
        }

        List<GenreDto> listGenres = this.findAllGenres(listGenreIdsFinal);
        List<LanguageDto> listLanguages = this.findAllLanguages(listLanguageIdsFinal);

        return listMovieEntities.stream()
                .map(movieEntity -> {
                    MovieDto movieDto = this.movieMapper.toDto(movieEntity);
                    this.setDataListGenres(movieDto, movieEntity, listGenres);
                    this.setDataListLanguages(movieDto, movieEntity, listLanguages);
                    return movieDto;
                }).collect(Collectors.toList());
    }

    private Set<Integer> getListGenresIds(MovieEntity movieEntity) {
        return movieEntity.getListGenres().stream()
                .map((language) -> language.getId().getGenreId())
                .collect(Collectors.toSet());
    }

    private Set<Integer> getListLanguageIds(MovieEntity movieEntity) {
        return movieEntity.getListLanguages().stream()
                .map((language) -> language.getId().getLanguageId())
                .collect(Collectors.toSet());
    }

    private List<GenreDto> findAllGenres(Set<Integer> listGenreIdsFinal) {
        ResponseDto respose = this.genreClient.findAllByListIds(listGenreIdsFinal);
        return FeignUtil.extractsDataList(respose, GenreDto.class);
    }

    private List<LanguageDto> findAllLanguages(Set<Integer> listGenreIdsFinal) {
        ResponseDto respose = this.languageClient.findAllByListIds(listGenreIdsFinal);
        return FeignUtil.extractsDataList(respose, LanguageDto.class);
    }

    private void setDataListGenres(MovieDto movieDto, MovieEntity movieEntity, List<GenreDto> listGenres) {
        Set<GenreDto> listGenreDtos = this.getListGenres(movieEntity);
        listGenreDtos = this.setComplementaryDataListGenres(listGenres, listGenreDtos);
        movieDto.setListGenres(listGenreDtos);
    }

    private Set<GenreDto> getListGenres(MovieEntity movieEntity) {
        return movieEntity.getListGenres().stream()
                .map((genreEntity) -> {
                    return GenreDto.builder()
                            .genreId(genreEntity.getId().getGenreId())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private Set<GenreDto> setComplementaryDataListGenres(List<GenreDto> listGenres, Set<GenreDto> listGenreDtos) {
        return listGenreDtos.stream()
                .peek((genreDto -> {
                    GenreDto genreFound = FilterUtil.find(listGenres, genreDto.getGenreId(), ValueEnum.GENRE.getValue());
                    genreDto.setName(genreFound.getName());
                    genreDto.setCode(genreFound.getCode());
                })).collect(Collectors.toSet());
    }

    private void setDataListLanguages(MovieDto movieDto, MovieEntity movieEntity, List<LanguageDto> listLanguages) {
        Set<LanguageDto> listLanguageDtos = this.getListLanguages(movieEntity);
        listLanguageDtos = this.setComplementaryDataListLanguages(listLanguageDtos, listLanguages);
        movieDto.setListLanguages(listLanguageDtos);
    }

    private Set<LanguageDto> getListLanguages(MovieEntity movieEntity) {
        return movieEntity.getListLanguages().stream()
                .map((languageEntity) -> {
                    return LanguageDto.builder()
                            .languageId(languageEntity.getId().getLanguageId())
                            .audioFormat(languageEntity.getId().getAudioFormat())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private Set<LanguageDto> setComplementaryDataListLanguages(Set<LanguageDto> listLanguageDtos, List<LanguageDto> listLanguages) {
        return listLanguageDtos.stream()
                .peek((languageDto -> {
                    LanguageDto languageFound = FilterUtil.find(listLanguages, languageDto.getLanguageId(), ValueEnum.LANGUAGE.getValue());
                    languageDto.setName(languageFound.getName());
                    languageDto.setCode(languageFound.getCode());
                })).collect(Collectors.toSet());
    }

}