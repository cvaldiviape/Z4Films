package com.catalogs.core.controller;

import com.catalogs.core.controller.interfaces.ISerieController;
import com.shared.core.service.*;
import com.shared.dto.external.catalog.SerieDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/series")
public class SerieController implements ISerieController<SerieDto, Integer> {

    private final FindAllService<SerieDto> findAllService;
    private final FindByIdService<SerieDto, Integer> findByIdService;
    private final CreateService<SerieDto> createService;
    private final UpdateService<SerieDto, Integer> updateService;
    private final DeleteService<SerieDto, Integer> deleteService;

    public SerieController(
            @Qualifier("findAllSerieImpl") FindAllService<SerieDto> findAllService,
            @Qualifier("findByIdSerieImpl") FindByIdService<SerieDto, Integer> findByIdService,
            @Qualifier("createSerieImpl") CreateService<SerieDto> createService,
            @Qualifier("updateSerieImpl") UpdateService<SerieDto, Integer> updateService,
            @Qualifier("deleteSerieImpl") DeleteService<SerieDto, Integer> deleteService) {
        this.findAllService = findAllService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public FindAllService<SerieDto> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public FindByIdService<SerieDto, Integer> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public CreateService<SerieDto> getCreateService() {
        return this.createService;
    }

    @Override
    public UpdateService<SerieDto, Integer> getUpdateService() {
        return this.updateService;
    }

    @Override
    public DeleteService<SerieDto, Integer> getDeleteService() {
        return this.deleteService;
    }

}