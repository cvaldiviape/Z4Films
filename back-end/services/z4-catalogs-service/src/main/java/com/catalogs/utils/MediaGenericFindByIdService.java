package com.catalogs.utils;

import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.shared.core.service.FindByIdService;
import com.shared.dto.custom.MediaEntity;
import com.shared.dto.external.catalog.MediaDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.response.ResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MediaGenericFindByIdService<GENRE_ENTITY, LANGUAGE_ENTITY,
        ENTITY extends MediaEntity<GENRE_ENTITY, LANGUAGE_ENTITY>,
        DTO extends MediaDto, ID>
        implements FindByIdService<DTO, ID> {

    public abstract JpaRepository<ENTITY, ID> getJpaRepository();
    public abstract GenreClient getGenreClient();
    public abstract LanguageClient getLanguageClient();
    public abstract DTO toDto(ENTITY entity);
    public abstract ENTITY findEntityById(ID id);
    public abstract void setComplementaryDataLanguage(ENTITY entity, LanguageDto languageDto);
    public abstract Set<Integer> getListGenreIds(ENTITY entity);
    public abstract Set<Integer> getListLanguageIds(ENTITY entity);

    @Override
    public DTO findById(ID id) {
        ENTITY entity = this.findEntityById(id);
        return this.toDtoCustom(entity);
    }

    private DTO toDtoCustom(ENTITY entity) {
        DTO dto = this.toDto(entity);
        this. setDataListGenres(entity, dto);
        this.setDataListLanguages(entity, dto);
        return dto;
    }

    private void setDataListGenres(ENTITY entity, DTO dto) {
        Set<Integer> listGenreIds = this.getListGenreIds(entity);
        List<GenreDto> listGenresDtos = this.findAllGenresByListIds(listGenreIds);
        dto.setListGenres(new HashSet<>(listGenresDtos));
    }

    private void setDataListLanguages(ENTITY entity, DTO dto) {
        Set<Integer> listLanguagesIds = this.getListLanguageIds(entity);
        List<LanguageDto> listLanguagesDtos = this.findAllLanguagesByListIds(listLanguagesIds);
        listLanguagesDtos = getListLanguagesFull(entity, listLanguagesDtos);
        dto.setListLanguages(new HashSet<>(listLanguagesDtos));
    }

    private List<LanguageDto> getListLanguagesFull(ENTITY entity, List<LanguageDto> listLanguagesDtos) {
        return listLanguagesDtos.stream()
                .peek((languageDto -> this.setComplementaryDataLanguage(entity, languageDto)))
                .toList();
    }

    private List<GenreDto> findAllGenresByListIds(Set<Integer> listGenreIds) {
        ResponseDto response = this.getGenreClient().findAllByListIds(listGenreIds);
        return FeignUtil.extractsDataList(response, GenreDto.class, ValueEnum.LIST_GENRE.getValue());
    }

    private List<LanguageDto> findAllLanguagesByListIds(Set<Integer> listLanguageIds) {
        ResponseDto response = this.getLanguageClient().findAllByListIds(listLanguageIds);
        return FeignUtil.extractsDataList(response, LanguageDto.class, ValueEnum.LIST_LANGUAGE.getValue());
    }

}