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
import com.catalogs.external.client.StudioClient;
import com.catalogs.kafka.publisher.SeriePublisher;
import com.catalogs.utils.MediaGenericCreateService;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.external.master.LanguageDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service("createSerieImpl")
public class CreateSerieImpl extends MediaGenericCreateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> {

    private final SerieRepository serieRepository;
    private final SerieMapper serieMapper;
    private final GenreClient genreClient;
    private final LanguageClient languageClient;
    private final StudioClient studioClient;
    private final SeriePublisher serieProducer;

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
    public StudioClient getStudioClient() {
        return this.studioClient;
    }

    @Override
    public void sendEventToKafka(SerieDto dtoCustom) {
        this.serieProducer.send(dtoCustom);
    }

    @Override
    public SerieDto toDto(SerieEntity entity) {
       return this.serieMapper.toDto(entity);
    }

    @Override
    public SerieEntity toEntity(SerieDto dto) {
        return this.serieMapper.toEntity(dto);
    }

    @Override
    public void generateId(SerieEntity entity) {
        UUID uuid = UUID.randomUUID();
        entity.setSerieId(uuid);
    }

    @Override
    public Set<SerieGenreEntity> buildListGenresEntities(Set<Integer> listGenreIds, SerieEntity serieEntity) {
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