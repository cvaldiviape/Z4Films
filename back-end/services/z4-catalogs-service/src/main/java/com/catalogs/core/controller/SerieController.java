package com.catalogs.core.controller;

import com.catalogs.core.controller.interfaces.ISerieController;
import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.SerieGenreEntity;
import com.catalogs.core.entity.SerieLanguageEntity;
import com.catalogs.utils.MediaGenericCreateService;
import com.catalogs.utils.MediaGenericFindAllService;
import com.catalogs.utils.MediaGenericFindByIdService;
import com.catalogs.utils.MediaGenericUpdateService;
import com.shared.core.service.impl.GenericDeleteService;
import com.shared.dto.external.catalog.SerieDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/series")
public class SerieController implements ISerieController<SerieDto, UUID> {

    private final MediaGenericFindAllService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> findAllService;
    private final MediaGenericFindByIdService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> findByIdService;
    private final MediaGenericCreateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> createService;
    private final MediaGenericUpdateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> updateService;
    private final GenericDeleteService<SerieEntity, SerieDto, UUID> deleteService;

    public SerieController(
            @Qualifier("findAllSerieImpl") MediaGenericFindAllService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> findAllService,
            @Qualifier("findByIdSerieImpl") MediaGenericFindByIdService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> findByIdService,
            @Qualifier("createSerieImpl") MediaGenericCreateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> createService,
            @Qualifier("updateSerieImpl") MediaGenericUpdateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> updateService,
            @Qualifier("deleteSerieImpl") GenericDeleteService<SerieEntity, SerieDto, UUID> deleteService) {
        this.findAllService = findAllService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public MediaGenericFindAllService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public MediaGenericFindByIdService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public MediaGenericCreateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> getCreateService() {
        return this.createService;
    }

    @Override
    public MediaGenericUpdateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, UUID> getUpdateService() {
        return this.updateService;
    }

    @Override
    public GenericDeleteService<SerieEntity, SerieDto, UUID> getDeleteService() {
        return this.deleteService;
    }

}