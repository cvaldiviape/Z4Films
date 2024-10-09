package com.catalogs.core.controller;

import com.catalogs.core.controller.interfaces.ISerieController;
import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.SerieGenreEntity;
import com.catalogs.core.entity.SerieLanguageEntity;
import com.catalogs.utils.MediaGenericCreateService;
import com.catalogs.utils.MediaGenericFindAllService;
import com.catalogs.utils.MediaGenericFindByIdService;
import com.catalogs.utils.MediaGenericUpdateService;
import com.shared.core.service.*;
import com.shared.core.service.impl.GenericDeleteService;
import com.shared.dto.external.catalog.SerieDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/series")
public class SerieController implements ISerieController<SerieDto, Integer> {

    private final MediaGenericFindAllService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> findAllService;
    private final MediaGenericFindByIdService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> findByIdService;
    private final MediaGenericCreateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> createService;
    private final MediaGenericUpdateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> updateService;
    private final GenericDeleteService<SerieEntity, SerieDto, Integer> deleteService;

    public SerieController(
            @Qualifier("findAllSerieImpl") MediaGenericFindAllService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> findAllService,
            @Qualifier("findByIdSerieImpl") MediaGenericFindByIdService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> findByIdService,
            @Qualifier("createSerieImpl") MediaGenericCreateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> createService,
            @Qualifier("updateSerieImpl") MediaGenericUpdateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> updateService,
            @Qualifier("deleteSerieImpl") GenericDeleteService<SerieEntity, SerieDto, Integer> deleteService) {
        this.findAllService = findAllService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public MediaGenericFindAllService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public MediaGenericFindByIdService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public MediaGenericCreateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> getCreateService() {
        return this.createService;
    }

    @Override
    public MediaGenericUpdateService<SerieGenreEntity, SerieLanguageEntity, SerieEntity, SerieDto, Integer> getUpdateService() {
        return this.updateService;
    }

    @Override
    public GenericDeleteService<SerieEntity, SerieDto, Integer> getDeleteService() {
        return this.deleteService;
    }

}