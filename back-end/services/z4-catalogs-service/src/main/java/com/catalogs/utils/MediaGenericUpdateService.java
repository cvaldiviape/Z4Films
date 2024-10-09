package com.catalogs.utils;

import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.shared.core.service.UpdateService;
import com.shared.dto.custom.MediaEntity;
import com.shared.dto.external.catalog.MediaDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.filter.FilterUtil;
import com.shared.utils.response.ResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
public abstract class MediaGenericUpdateService <GENRE_ENTITY, LANGUAGE_ENTITY,
        ENTITY extends MediaEntity<GENRE_ENTITY, LANGUAGE_ENTITY>,
        DTO extends MediaDto, ID>
        implements UpdateService<DTO, ID> {

    public abstract JpaRepository<ENTITY, ID> getJpaRepository();
    public abstract GenreClient getGenreClient();
    public abstract LanguageClient getLanguageClient();
    public abstract DTO toDto(ENTITY entity);
    public abstract void updateEntityFromDtoIgnoredId(DTO dto, ENTITY entity);
    public abstract ENTITY findEntityById(ID id);
    public abstract Set<GENRE_ENTITY> buildListGenresEntities(Set<Integer> listGenreIds, ENTITY entity);
    public abstract Set<LANGUAGE_ENTITY> buildListLanguagesEntities(Set<LanguageDto> listLanguages, ENTITY entity);

    @Override
    public DTO update(ID id, DTO dto) {
        this.validateRelationships(dto);
        ENTITY entityUpdated = this.updateEntity(id, dto);
        return this.toDtoCustom(dto, entityUpdated);
    }

    private void validateRelationships(DTO dto) {
        Set<Integer> listGenreIds = this.getListGenreIds(dto);
        List<GenreDto> listGenresFound = this.findAllGenresByListIds(listGenreIds);
        this.verifyExistenceOfGenres(listGenreIds, listGenresFound);

        Set<Integer> listLanguageIds = getListLanguageIds(dto);
        List<LanguageDto> listLanguagesFound = this.findAllLanguagesByListIds(listLanguageIds);
        this.verifyExistenceOfLanguages(listLanguageIds, listLanguagesFound);
    }

    private void verifyExistenceOfGenres(Set<Integer> listGenreIds, List<GenreDto> listGenresFound) {
        listGenreIds.forEach(genreId -> FilterUtil.find(listGenresFound, genreId, ValueEnum.GENRE.getValue()));
    }

    private void verifyExistenceOfLanguages(Set<Integer> listLanguageIds, List<LanguageDto> listLanguageFound) {
        listLanguageIds.forEach(languageId -> FilterUtil.find(listLanguageFound, languageId, ValueEnum.LANGUAGE.getValue()));
    }

    private ENTITY updateEntity(ID id, DTO dto) {
        ENTITY entity = this.findEntityById(id);

        this.updateEntityFromDtoIgnoredId(dto, entity);

        Set<Integer> listGenreIds = this.getListGenreIds(dto);
        Set<GENRE_ENTITY> listGenresEntities = this.buildListGenresEntities(listGenreIds, entity);
        entity.setGenres(listGenresEntities);

        Set<LanguageDto> listLanguages = dto.getListLanguages();
        Set<LANGUAGE_ENTITY> listLanguagesEntities = this.buildListLanguagesEntities(listLanguages, entity);
        entity.setLanguages(listLanguagesEntities);

        return this.getJpaRepository().save(entity);
    }

    private DTO toDtoCustom(DTO dto, ENTITY entityUpdated) {
        DTO dtoUpdated = this.toDto(entityUpdated);
        this.setDataListGenres(dto, dtoUpdated);
        this.setDataListLanguages(dto, dtoUpdated);
        return dtoUpdated;
    }

    private void setDataListGenres(DTO dto, DTO dtoUpdated) {
        Set<Integer> listGenreIds = this.getListGenreIds(dto);
        List<GenreDto> listGenresDtos = this.findAllGenresByListIds(listGenreIds);
        dtoUpdated.setListGenres(new HashSet<>(listGenresDtos));
    }

    private Set<Integer> getListGenreIds(DTO dto) {
        return dto.getListGenres()
                .stream().map(GenreDto::getGenreId)
                .collect(Collectors.toSet());
    }

    private List<GenreDto> findAllGenresByListIds(Set<Integer> listGenreIds) {
        ResponseDto response = this.getGenreClient().findAllByListIds(listGenreIds);
        return FeignUtil.extractsDataList(response, GenreDto.class, ValueEnum.LIST_GENRE.getValue());
    }

    private void setDataListLanguages(DTO dto, DTO dtoUpdated) {
        Set<Integer> listLanguagesIds = this.getListLanguageIds(dto);
        List<LanguageDto> listLanguagesDtos = this.findAllLanguagesByListIds(listLanguagesIds);
        listLanguagesDtos = getListLanguagesFull(dto, listLanguagesDtos);
        dtoUpdated.setListLanguages(new HashSet<>(listLanguagesDtos));
    }

    private Set<Integer> getListLanguageIds(DTO dto) {
        return dto.getListLanguages().stream()
                .map(LanguageDto::getLanguageId)
                .collect(Collectors.toSet());
    }

    private List<LanguageDto> findAllLanguagesByListIds(Set<Integer> listLanguageIds) {
        ResponseDto response = this.getLanguageClient().findAllByListIds(listLanguageIds);
        return FeignUtil.extractsDataList(response, LanguageDto.class, ValueEnum.LIST_LANGUAGE.getValue());
    }


    private List<LanguageDto> getListLanguagesFull(DTO dto, List<LanguageDto> listLanguagesDtos) {
        return listLanguagesDtos.stream()
                .peek(languageDto -> this.setComplementaryDataLanguage(languageDto, dto))
                .toList();
    }

    private void setComplementaryDataLanguage(LanguageDto languageDto, DTO dto) {
        LanguageDto languageFound = FilterUtil.find(new ArrayList<>(dto.getListLanguages()), languageDto.getLanguageId(), ValueEnum.LANGUAGE.getValue());
        languageDto.setAudioFormat(languageFound.getAudioFormat());
    }

}