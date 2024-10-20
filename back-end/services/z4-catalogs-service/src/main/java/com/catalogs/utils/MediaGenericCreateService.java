package com.catalogs.utils;

import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.shared.core.service.CreateService;
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
public abstract class MediaGenericCreateService<GENRE_ENTITY, LANGUAGE_ENTITY,
        ENTITY extends MediaEntity<GENRE_ENTITY, LANGUAGE_ENTITY>,
        DTO extends MediaDto, ID>
        implements CreateService<DTO> {

    public abstract JpaRepository<ENTITY, ID> getJpaRepository();
    public abstract GenreClient getGenreClient();
    public abstract LanguageClient getLanguageClient();
    public abstract DTO toDto(ENTITY entity);
    public abstract ENTITY toEntity(DTO dto);
    public abstract void generateId(ENTITY entity);
    public abstract Set<GENRE_ENTITY> buildListGenresEntities(Set<Integer> listGenreIds, ENTITY entity);
    public abstract Set<LANGUAGE_ENTITY> buildListLanguagesEntities(Set<LanguageDto> listLanguages, ENTITY entity);

    @Override
    public DTO create(DTO dto) {
        this.validateRelationships(dto);
        ENTITY entityCreated = this.createEntity(dto);
        return this.toDtoCustom(dto, entityCreated);
    }

    private void validateRelationships(DTO dto) {
        Set<Integer> listGenreIds = this.getListGenreIds(dto);
        List<GenreDto> listGenresFound = this.findAllGenresByListIds(listGenreIds);
        this.verifyExistenceOfGenres(listGenreIds, listGenresFound);

        Set<Integer> listLanguageIds = getListLanguageIds(dto);
        List<LanguageDto> listLanguagesFound = this.findAllLanguagesByListIds(listLanguageIds);
        this.verifyExistenceOfLanguages(listLanguageIds, listLanguagesFound);
    }

    private DTO toDtoCustom(DTO dto, ENTITY entityCreated) {
        DTO dtoCreated = this.toDto(entityCreated);
        this.setDataListGenres(dto, dtoCreated);
        this.setDataListLanguages(dto, dtoCreated);
        return dtoCreated;
    }

    private void verifyExistenceOfGenres(Set<Integer> listGenreIds, List<GenreDto> listGenresFound) {
        listGenreIds.forEach(genreId -> FilterUtil.find(listGenresFound, genreId, ValueEnum.GENRE.getValue()));
    }

    private void verifyExistenceOfLanguages(Set<Integer> listLanguageIds, List<LanguageDto> listLanguageFound) {
        listLanguageIds.forEach(languageId -> FilterUtil.find(listLanguageFound, languageId, ValueEnum.LANGUAGE.getValue()));
    }

    private ENTITY createEntity(DTO dto) {
        ENTITY entity = this.toEntity(dto);
        this.generateId(entity);
        this.getJpaRepository().save(entity);

        Set<Integer> listGenreIds = this.getListGenreIds(dto);
        Set<GENRE_ENTITY> listGenresEntities = this.buildListGenresEntities(listGenreIds, entity);
        entity.setListGenresCustom(listGenresEntities);

        Set<LanguageDto> listLanguages = dto.getListLanguages();
        Set<LANGUAGE_ENTITY> listLanguagesEntities = this.buildListLanguagesEntities(listLanguages, entity);
        entity.setListLanguagesCustom(listLanguagesEntities);

        return this.getJpaRepository().save(entity);
    }

    private void setDataListGenres(DTO dto, DTO dtoCreated) {
        Set<Integer> listGenreIds = this.getListGenreIds(dto);
        List<GenreDto> listGenresDtos = this.findAllGenresByListIds(listGenreIds);
        dtoCreated.setListGenres(new HashSet<>(listGenresDtos));
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

    private void setDataListLanguages(DTO dto, DTO dtoCreated) {
        Set<Integer> listLanguagesIds = this.getListLanguageIds(dto);
        List<LanguageDto> listLanguagesDtos = this.findAllLanguagesByListIds(listLanguagesIds);
        listLanguagesDtos = this.getListLanguagesFull(dto, listLanguagesDtos);
        dtoCreated.setListLanguages(new HashSet<>(listLanguagesDtos));
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