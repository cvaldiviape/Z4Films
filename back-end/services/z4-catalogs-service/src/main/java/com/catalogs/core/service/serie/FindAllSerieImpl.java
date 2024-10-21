package com.catalogs.core.service.serie;

import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.SerieGenreEntity;
import com.catalogs.core.entity.SerieLanguageEntity;
import com.catalogs.core.entity.mapper.SerieMapper;
import com.catalogs.core.repository.SerieRepository;
import com.catalogs.external.client.GenreClient;
import com.catalogs.external.client.LanguageClient;
import com.catalogs.utils.MediaGenericFindAllService;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("findAllSerieImpl")
public class FindAllSerieImpl extends MediaGenericFindAllService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> {

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
    public Set<Integer> getListGenresIds(SerieEntity entity) {
        return entity.getListGenres().stream()
                .map((genre) -> genre.getId().getGenreId())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getListLanguageIds(SerieEntity entity) {
        return entity.getListLanguages().stream()
                .map((language) -> language.getId().getLanguageId())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GenreDto> getListGenres(SerieEntity entity) {
        return entity.getListGenres().stream()
                .map((genreEntity) -> {
                    return GenreDto.builder()
                            .genreId(genreEntity.getId().getGenreId())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<LanguageDto> getListLanguages(SerieEntity entity) {
        return entity.getListLanguages().stream()
                .map((languageEntity) -> {
                    return LanguageDto.builder()
                            .languageId(languageEntity.getId().getLanguageId())
                            .audioFormat(languageEntity.getId().getAudioFormat())
                            .build();
                })
                .collect(Collectors.toSet());
    }

}