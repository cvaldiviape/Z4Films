package com.catalogs.core.service.serie;

import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.SerieGenreEntity;
import com.catalogs.core.entity.SerieLanguageEntity;
import com.catalogs.core.entity.ids.SerieGenreId;
import com.catalogs.core.entity.ids.SerieLanguageId;
import com.catalogs.core.entity.mapper.SerieMapper;
import com.catalogs.core.repository.SerieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.shared.core.service.CreateService;
import com.shared.dto.external.catalog.SerieDto;
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
@Service("createSerieImpl")
public class CreateSerieImpl implements CreateService<SerieDto> {

    private final SerieRepository serieRepository;
    private final SerieMapper serieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public SerieDto create(SerieDto serieDto) {
        this.validateRelationships(serieDto);
        SerieEntity serieEntityCreated = this.createSerie(serieDto);
        return this.toDto(serieDto, serieEntityCreated);
    }

    private void validateRelationships(SerieDto serieDto) {
        Set<Integer> listGenreIds = this.getListGenreIds(serieDto);
        List<GenreDto> listGenresFound = this.findAllGenresByListIds(listGenreIds);
        this.verifyExistenceOfGenres(listGenreIds, listGenresFound);

        Set<Integer> listLanguageIds = getListLanguageIds(serieDto);
        List<LanguageDto> listLanguagesFound = this.findAllLanguagesByListIds(listLanguageIds);
        this.verifyExistenceOfLanguages(listLanguageIds, listLanguagesFound);
    }

    private void verifyExistenceOfGenres(Set<Integer> listGenreIds, List<GenreDto> listGenresFound) {
        listGenreIds.forEach(genreId -> FilterUtil.find(listGenresFound, genreId, ValueEnum.GENRE.getValue()));
    }

    private void verifyExistenceOfLanguages(Set<Integer> listLanguageIds, List<LanguageDto> listLanguageFound) {
        listLanguageIds.forEach(languageId -> FilterUtil.find(listLanguageFound, languageId, ValueEnum.LANGUAGE.getValue()));
    }

    private SerieEntity createSerie(SerieDto serieDto) {
        SerieEntity serieEntity = this.serieMapper.toEntity(serieDto);
        this.serieRepository.save(serieEntity);

        Set<Integer> listGenreIds = this.getListGenreIds(serieDto);
        Set<SerieGenreEntity> listGenresEntities = this.buildListGenresEntities(listGenreIds, serieEntity);
        serieEntity.setListGenres(listGenresEntities);

        Set<LanguageDto> listLanguages = serieDto.getListLanguages();
        Set<SerieLanguageEntity> listLanguagesEntities = this.buildListLanguagesEntities(listLanguages, serieEntity);
        serieEntity.setListLanguages(listLanguagesEntities);

        return this.serieRepository.save(serieEntity);
    }

    private Set<SerieGenreEntity> buildListGenresEntities(Set<Integer> listGenreIds, SerieEntity serieEntity) {
        return listGenreIds.stream().map(genreId -> {
            SerieGenreId id = SerieGenreId.builder()
                    .genreId(genreId)
                    .serieId(serieEntity.getSerieId())
                    .build();

            return SerieGenreEntity.builder()
                    .id(id)
                    .serie(serieEntity)
                    .build();
        }).collect(Collectors.toSet());
    }

    private Set<SerieLanguageEntity> buildListLanguagesEntities(Set<LanguageDto> listLanguages, SerieEntity serieEntity) {
        return listLanguages.stream().map(language -> {
            SerieLanguageId id = SerieLanguageId.builder()
                    .languageId(language.getLanguageId())
                    .audioFormat(language.getAudioFormat())
                    .serieId(serieEntity.getSerieId())
                    .build();

            return SerieLanguageEntity.builder()
                    .id(id)
                    .serie(serieEntity)
                    .build();
        }).collect(Collectors.toSet());
    }

    private SerieDto toDto(SerieDto serieDto, SerieEntity serieEntityCreated) {
        SerieDto serieDtoCreated = this.serieMapper.toDto(serieEntityCreated);
        this.setDataListGenres(serieDto, serieDtoCreated);
        this.setDataListLanguages(serieDto, serieDtoCreated);
        return serieDtoCreated;
    }

    private void setDataListGenres(SerieDto serieDto, SerieDto serieDtoCreated) {
        Set<Integer> listGenreIds = this.getListGenreIds(serieDto);
        List<GenreDto> listGenresDtos = this.findAllGenresByListIds(listGenreIds);
        serieDtoCreated.setListGenres(new HashSet<>(listGenresDtos));
    }

    private Set<Integer> getListGenreIds(SerieDto movieDto) {
        return movieDto.getListGenres()
                .stream().map(GenreDto::getGenreId)
                .collect(Collectors.toSet());
    }

    private List<GenreDto> findAllGenresByListIds(Set<Integer> listGenreIds) {
        ResponseDto response = this.genreClient.findAllByListIds(listGenreIds);
        return FeignUtil.extractsDataList(response, GenreDto.class, ValueEnum.LIST_GENRE.getValue());
    }

    private void setDataListLanguages(SerieDto serieDto, SerieDto serieDtoCreated) {
        Set<Integer> listLanguagesIds = this.getListLanguageIds(serieDto);
        List<LanguageDto> listLanguagesDtos = this.findAllLanguagesByListIds(listLanguagesIds);
        listLanguagesDtos = this.getListLanguagesFull(serieDto, listLanguagesDtos);
        serieDtoCreated.setListLanguages(new HashSet<>(listLanguagesDtos));
    }

    private Set<Integer> getListLanguageIds(SerieDto serieDto) {
        return serieDto.getListLanguages().stream()
                .map(LanguageDto::getLanguageId)
                .collect(Collectors.toSet());
    }

    private List<LanguageDto> findAllLanguagesByListIds(Set<Integer> listLanguageIds) {
        ResponseDto response = this.languageClient.findAllByListIds(listLanguageIds);
        return FeignUtil.extractsDataList(response, LanguageDto.class, ValueEnum.LIST_LANGUAGE.getValue());
    }

    private List<LanguageDto> getListLanguagesFull(SerieDto serieDto, List<LanguageDto> listLanguagesDtos) {
        return listLanguagesDtos.stream()
                .peek(languageDto -> this.setComplementaryDataLanguage(languageDto, serieDto))
                .toList();
    }

    private void setComplementaryDataLanguage(LanguageDto languageDto, SerieDto serieDto) {
        LanguageDto languageFound = FilterUtil.find(new ArrayList<>(serieDto.getListLanguages()), languageDto.getLanguageId(), ValueEnum.LANGUAGE.getValue());
        languageDto.setAudioFormat(languageFound.getAudioFormat());
    }


}