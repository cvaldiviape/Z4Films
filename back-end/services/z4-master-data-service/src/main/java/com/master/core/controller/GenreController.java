package com.master.core.controller;

import com.master.core.controller.interfaces.IGenreController;
import com.master.core.entity.GenreEntity;
import com.shared.core.service.*;
import com.shared.core.service.impl.*;
import com.shared.dto.external.master.GenreDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/genres")
public class GenreController implements IGenreController<GenreDto, Integer> {

    private final GenericFindAllService<GenreEntity, GenreDto, Integer> findAllService;
    private final GenericFindAllByListIdsService<GenreEntity, GenreDto, Integer> findAllByListIdsService;
    private final GenericFindByIdService<GenreEntity, GenreDto, Integer> findByIdService;
    private final GenericCreateService<GenreEntity, GenreDto, Integer> createService;
    private final GenericUpdateService<GenreEntity, GenreDto, Integer> updateService;
    private final GenericDeleteService<GenreEntity, GenreDto, Integer> deleteService;

    public GenreController(
            @Qualifier("findAllGenreImpl") GenericFindAllService<GenreEntity, GenreDto, Integer> findAllService,
            @Qualifier("findAllByListIdsGenreImpl") GenericFindAllByListIdsService<GenreEntity, GenreDto, Integer> findAllByListIdsService,
            @Qualifier("findByIdGenreImpl") GenericFindByIdService<GenreEntity, GenreDto, Integer> findByIdService,
            @Qualifier("createGenreImpl") GenericCreateService<GenreEntity, GenreDto, Integer> createService,
            @Qualifier("updateGenreImpl") GenericUpdateService<GenreEntity, GenreDto, Integer> updateService,
            @Qualifier("deleteGenreImpl") GenericDeleteService<GenreEntity, GenreDto, Integer> deleteService) {
        this.findAllService = findAllService;
        this.findAllByListIdsService = findAllByListIdsService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public FindAllService<GenreDto> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public FindAllByListIdsService<GenreDto, Integer> getFindAllByListIdsService() {
        return this.findAllByListIdsService;
    }

    @Override
    public FindByIdService<GenreDto, Integer> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public CreateService<GenreDto> getCreateService() {
        return this.createService;
    }

    @Override
    public UpdateService<GenreDto, Integer> getUpdateService() {
        return this.updateService;
    }

    @Override
    public DeleteService<GenreDto, Integer> getDeleteService() {
        return this.deleteService;
    }

}