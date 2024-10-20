package com.catalogs.core.service.serie;

import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.SerieGenreEntity;
import com.catalogs.core.entity.SerieLanguageEntity;
import com.catalogs.core.entity.mapper.SerieMapper;
import com.catalogs.core.repository.SerieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.catalogs.utils.MediaGenericFindByIdService;
import com.shared.core.service.FindByIdService;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.FeignUtil;
import com.shared.utils.ValidateUtil;
import com.shared.utils.response.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findByIdSerieImpl")
public class FindByIsSerieImpl extends MediaGenericFindByIdService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> {

    private final SerieRepository serieRepository;
    private final SerieMapper serieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;

    @Override
    public JpaRepository<SerieEntity, UUID> getJpaRepository() {
        return this.serieRepository;
    }

    @Override
    public GenreClient getGenreClient() {
        return this.genreClient;
    }

    @Override
    public LanguageClient getLanguageClient() {
        return this.languageClient;
    }

    @Override
    public SerieDto toDto(SerieEntity entity) {
        return this.serieMapper.toDto(entity);
    }

    @Override
    public SerieEntity findEntityById(UUID serieId) {
        return this.serieRepository.findById(serieId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.SERIE.getValue()));
    }

    @Override
    public void setComplementaryDataLanguage(SerieEntity entity, LanguageDto languageDto) {
        SerieLanguageEntity languageFound = this.findLanguage(entity, languageDto);
//        languageDto.setAudioFormat(languageFound.getId().getAudioFormat());
    }

    private SerieLanguageEntity findLanguage(SerieEntity entity, LanguageDto languageDto) {
        return entity.getListLanguages().stream()
                .filter((languageEntity) -> languageEntity.getId().getLanguageId().equals(languageDto.getLanguageId()))
                .findFirst()
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.LANGUAGE.getValue()));
    }

    @Override
    public Set<Integer> getListGenreIds(SerieEntity entity) {
        return entity.getListGenres().stream()
                .map((genreEntity) -> genreEntity.getId().getGenreId())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getListLanguageIds(SerieEntity entity) {
        return entity.getListLanguages().stream()
                .map((genreEntity) -> genreEntity.getId().getLanguageId())
                .collect(Collectors.toSet());
    }

}