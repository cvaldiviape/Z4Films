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
import com.catalogs.utils.MediaGenericUpdateService;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service("updateSerieImpl")
public class UpdateSerieImpl extends MediaGenericUpdateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> {

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
    public void updateEntityFromDtoIgnoredId(SerieDto dto, SerieEntity entity) {
        this.serieMapper.updateEntityFromDtoIgnoredId(dto, entity);
    }

    @Override
    public SerieEntity findEntityById(UUID serieId) {
        return this.serieRepository.findById(serieId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.SERIE.getValue()));
    }

    @Override
    public Set<SerieGenreEntity> buildListGenresEntities(Set<Integer> listGenreIds, SerieEntity entity) {
        return listGenreIds.stream().map(genreId -> {
            SerieGenreId id = SerieGenreId.builder()
                    .genreId(genreId)
                    .serieId(entity.getSerieId())
                    .build();

            return SerieGenreEntity.builder()
                    .id(id)
                    .serie(entity)
                    .build();
        }).collect(Collectors.toSet());
    }

    @Override
    public Set<SerieLanguageEntity> buildListLanguagesEntities(Set<LanguageDto> listLanguages, SerieEntity entity) {
        return listLanguages.stream().map(language -> {
            SerieLanguageId id = SerieLanguageId.builder()
                    .languageId(language.getLanguageId())
                    .audioFormat(language.getAudioFormat())
                    .serieId(entity.getSerieId())
                    .build();

            return SerieLanguageEntity.builder()
                    .id(id)
                    .serie(entity)
                    .build();
        }).collect(Collectors.toSet());
    }

}