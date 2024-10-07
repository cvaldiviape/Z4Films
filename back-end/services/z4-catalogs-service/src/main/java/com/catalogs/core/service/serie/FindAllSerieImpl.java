package com.catalogs.core.service.serie;

import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.mapper.SerieMapper;
import com.catalogs.core.repository.SerieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.shared.core.service.FindAllService;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.filter.FilterUtil;
import com.shared.utils.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findAllSerieImpl")
public class FindAllSerieImpl implements FindAllService<SerieDto> {

    private final SerieRepository serieRepository;
    private final SerieMapper serieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public List<SerieDto> findAll() {
        List<SerieEntity> listSerieEntities = this.serieRepository.findAll();

        Set<Integer> listGenreIdsFinal = new HashSet<>();
        Set<Integer> listLanguageIdsFinal = new HashSet<>();

        for (SerieEntity serieEntity : listSerieEntities) {
            Set<Integer> listGenreIds = this.getListGenresIds(serieEntity);
            listGenreIdsFinal.addAll(listGenreIds);

            Set<Integer> listLanguageIds = this.getListLanguageIds(serieEntity);
            listLanguageIdsFinal.addAll(listLanguageIds);
        }

        List<GenreDto> listGenres = this.findAllGenres(listGenreIdsFinal);
        List<LanguageDto> listLanguages = this.findAllLanguages(listLanguageIdsFinal);

        return listSerieEntities.stream()
                .map(serieEntity -> {
                    SerieDto serieDto = this.serieMapper.toDto(serieEntity);
                    this.setDataListGenres(serieDto, serieEntity, listGenres);
                    this.setDataListLanguages(serieDto, serieEntity, listLanguages);
                    return serieDto;
                }).collect(Collectors.toList());
    }

    private Set<Integer> getListGenresIds(SerieEntity serieEntity) {
        return serieEntity.getListGenres().stream()
                .map((language) -> language.getId().getGenreId())
                .collect(Collectors.toSet());
    }

    private Set<Integer> getListLanguageIds(SerieEntity serieEntity) {
        return serieEntity.getListLanguages().stream()
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

    private void setDataListGenres(SerieDto serieDto, SerieEntity serieEntity, List<GenreDto> listGenres) {
        Set<GenreDto> listGenreDtos = this.getListGenres(serieEntity);
        listGenreDtos = this.setComplementaryDataListGenres(listGenres, listGenreDtos);
        serieDto.setListGenres(listGenreDtos);
    }

    private Set<GenreDto> getListGenres(SerieEntity serieEntity) {
        return serieEntity.getListGenres().stream()
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

    private void setDataListLanguages(SerieDto serieDto, SerieEntity serieEntity, List<LanguageDto> listLanguages) {
        Set<LanguageDto> listLanguageDtos = this.getListLanguages(serieEntity);
        listLanguageDtos = this.setComplementaryDataListLanguages(listLanguageDtos, listLanguages);
        serieDto.setListLanguages(listLanguageDtos);
    }

    private Set<LanguageDto> getListLanguages(SerieEntity serieEntity) {
        return serieEntity.getListLanguages().stream()
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