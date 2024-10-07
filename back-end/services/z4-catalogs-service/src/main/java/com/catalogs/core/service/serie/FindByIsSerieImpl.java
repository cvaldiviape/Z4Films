package com.catalogs.core.service.serie;

import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.SerieLanguageEntity;
import com.catalogs.core.entity.mapper.SerieMapper;
import com.catalogs.core.repository.SerieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.shared.core.service.FindByIdService;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.ValidateUtil;
import com.shared.utils.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findByIdSerieImpl")
public class FindByIsSerieImpl implements FindByIdService<SerieDto, Integer> {

    private final SerieRepository serieRepository;
    private final SerieMapper serieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public SerieDto findById(Integer idSerie) {
        SerieEntity serieEntity = this.findSerieById(idSerie);
        return this.toDto(serieEntity);
    }

    private SerieEntity findSerieById(Integer idSerie) {
        return this.serieRepository.findById(idSerie)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.SERIE.getValue()));
    }

    private SerieDto toDto(SerieEntity serieEntity) {
        SerieDto serieDto = this.serieMapper.toDto(serieEntity);
        this.setDataListGenres(serieEntity, serieDto);
        this.setDataListLanguages(serieEntity, serieDto);
        return serieDto;
    }

    private void setDataListGenres(SerieEntity serieEntity, SerieDto serieDto) {
        Set<Integer> listGenreIds = this.getListGenreIds(serieEntity);
        List<GenreDto> listGenresDtos = this.findAllGenresByListIds(listGenreIds);
        serieDto.setListGenres(new HashSet<>(listGenresDtos));
    }

    private void setDataListLanguages(SerieEntity serieEntity, SerieDto serieDto) {
        Set<Integer> listLanguagesIds = this.getListLanguageIds(serieEntity);
        List<LanguageDto> listLanguagesDtos = this.findAllLanguagesByListIds(listLanguagesIds);
        listLanguagesDtos = getListLanguagesFull(serieEntity, listLanguagesDtos);
        serieDto.setListLanguages(new HashSet<>(listLanguagesDtos));
    }

    private List<LanguageDto> getListLanguagesFull(SerieEntity serieEntity, List<LanguageDto> listLanguagesDtos) {
        return listLanguagesDtos.stream()
                .peek((languageDto -> this.setComplementaryDataLanguage(serieEntity, languageDto)))
                .toList();
    }

    private void setComplementaryDataLanguage(SerieEntity serieEntity, LanguageDto languageDto) {
        SerieLanguageEntity languageFound = this.findLanguage(serieEntity, languageDto);
        languageDto.setAudioFormat(languageFound.getId().getAudioFormat());
    }

    private SerieLanguageEntity findLanguage(SerieEntity serieEntity, LanguageDto languageDto) {
        return serieEntity.getListLanguages().stream()
                .filter((languageEntity) -> languageEntity.getId().getLanguageId().equals(languageDto.getLanguageId()))
                .findFirst()
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.LANGUAGE.getValue()));
    }

    private Set<Integer> getListGenreIds(SerieEntity serieEntity) {
        return serieEntity.getListGenres().stream()
                .map((genreEntity) -> genreEntity.getId().getGenreId())
                .collect(Collectors.toSet());
    }

    private List<GenreDto> findAllGenresByListIds(Set<Integer> listGenreIds) {
        ResponseDto response = this.genreClient.findAllByListIds(listGenreIds);
        return FeignUtil.extractsDataList(response, GenreDto.class, ValueEnum.LIST_GENRE.getValue());
    }

    private Set<Integer> getListLanguageIds(SerieEntity serieEntity) {
        return serieEntity.getListLanguages().stream()
                .map((genreEntity) -> genreEntity.getId().getLanguageId())
                .collect(Collectors.toSet());
    }

    private List<LanguageDto> findAllLanguagesByListIds(Set<Integer> listLanguageIds) {
        ResponseDto response = this.languageClient.findAllByListIds(listLanguageIds);
        return FeignUtil.extractsDataList(response, LanguageDto.class, ValueEnum.LIST_LANGUAGE.getValue());
    }

}