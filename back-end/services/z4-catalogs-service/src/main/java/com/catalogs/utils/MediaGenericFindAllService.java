package com.catalogs.utils;

import com.catalogs.core.entity.SerieEntity;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.shared.core.service.FindAllService;
import com.shared.dto.custom.MediaEntity;
import com.shared.dto.external.catalog.MediaDto;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.filter.FilterUtil;
import com.shared.utils.response.ResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class MediaGenericFindAllService<GENRE_ENTITY, LANGUAGE_ENTITY,
        ENTITY extends MediaEntity<GENRE_ENTITY, LANGUAGE_ENTITY>,
        DTO extends MediaDto, ID>
        implements FindAllService<DTO> {

    public abstract JpaRepository<ENTITY, ID> getJpaRepository();
    public abstract GenreClient getGenreClient();
    public abstract LanguageClient getLanguageClient();
    public abstract DTO toDto(ENTITY entity);
    public abstract Set<Integer> getListGenresIds(ENTITY entity);
    public abstract Set<Integer> getListLanguageIds(ENTITY entity);
    public abstract Set<LanguageDto> getListLanguages(ENTITY entity);
    public abstract Set<GenreDto> getListGenres(ENTITY entity);

    @Override
    public List<DTO> findAll() {
        List<ENTITY> listSerieEntities = this.getJpaRepository().findAll();

        Set<Integer> listGenreIdsFinal = new HashSet<>();
        Set<Integer> listLanguageIdsFinal = new HashSet<>();

        this.setListIdsFinal(listSerieEntities, listGenreIdsFinal, listLanguageIdsFinal);

        List<GenreDto> listGenres = this.findAllGenres(listGenreIdsFinal);
        List<LanguageDto> listLanguages = this.findAllLanguages(listLanguageIdsFinal);

        return this.toListDtos(listSerieEntities, listGenres, listLanguages);
    }

    private void setListIdsFinal(List<ENTITY> listSerieEntities, Set<Integer> listGenreIdsFinal, Set<Integer> listLanguageIdsFinal) {
        for (ENTITY serieEntity : listSerieEntities) {
            Set<Integer> listGenreIds = this.getListGenresIds(serieEntity);
            listGenreIdsFinal.addAll(listGenreIds);

            Set<Integer> listLanguageIds = this.getListLanguageIds(serieEntity);
            listLanguageIdsFinal.addAll(listLanguageIds);
        }
    }

    private List<GenreDto> findAllGenres(Set<Integer> listGenreIdsFinal) {
        ResponseDto respose = this.getGenreClient().findAllByListIds(listGenreIdsFinal);
        return FeignUtil.extractsDataList(respose, GenreDto.class);
    }

    private List<LanguageDto> findAllLanguages(Set<Integer> listGenreIdsFinal) {
        ResponseDto respose = this.getLanguageClient().findAllByListIds(listGenreIdsFinal);
        return FeignUtil.extractsDataList(respose, LanguageDto.class);
    }

    private List<DTO> toListDtos(List<ENTITY> listEntities, List<GenreDto> listGenres, List<LanguageDto> listLanguages) {
        return listEntities.stream()
                .map(entity -> {
                    DTO dto = this.toDto(entity);
                    this.setDataListGenres(dto, entity, listGenres);
                    this.setDataListLanguages(dto, entity, listLanguages);
                    return dto;
                }).collect(Collectors.toList());
    }

    private void setDataListGenres(DTO serieDto, ENTITY entity, List<GenreDto> listGenres) {
        Set<GenreDto> listGenreDtos = this.getListGenres(entity);
        listGenreDtos = this.setComplementaryDataListGenres(listGenres, listGenreDtos);
        serieDto.setListGenres(listGenreDtos);
    }

    private Set<GenreDto> setComplementaryDataListGenres(List<GenreDto> listGenres, Set<GenreDto> listGenreDtos) {
        return listGenreDtos.stream()
                .peek((genreDto -> {
                    GenreDto genreFound = FilterUtil.find(listGenres, genreDto.getGenreId(), ValueEnum.GENRE.getValue());
                    genreDto.setName(genreFound.getName());
                    genreDto.setCode(genreFound.getCode());
                })).collect(Collectors.toSet());
    }

    private void setDataListLanguages(DTO dto, ENTITY entity, List<LanguageDto> listLanguages) {
        Set<LanguageDto> listLanguageDtos = this.getListLanguages(entity);
        listLanguageDtos = this.setComplementaryDataListLanguages(listLanguageDtos, listLanguages);
        dto.setListLanguages(listLanguageDtos);
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