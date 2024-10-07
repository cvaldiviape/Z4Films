package com.catalogs.core.controller;

import com.catalogs.core.controller.interfaces.IMovieController;
import com.shared.core.service.*;
import com.shared.dto.external.catalog.MovieDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movies")
public class MovieController implements IMovieController<MovieDto, Integer> {

    private final FindAllService<MovieDto> findAllService;
    private final FindByIdService<MovieDto, Integer> findByIdService;
    private final CreateService<MovieDto> createService;
    private final UpdateService<MovieDto, Integer> updateService;
    private final DeleteService<MovieDto, Integer> deleteService;

    public MovieController(
            @Qualifier("findAllMovieImpl") FindAllService<MovieDto> findAllService,
            @Qualifier("findByIdMovieImpl") FindByIdService<MovieDto, Integer> findByIdService,
            @Qualifier("createMovieImpl") CreateService<MovieDto> createService,
            @Qualifier("updateMovieImpl") UpdateService<MovieDto, Integer> updateService,
            @Qualifier("deleteMovieImpl") DeleteService<MovieDto, Integer> deleteService) {
        this.findAllService = findAllService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public FindAllService<MovieDto> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public FindByIdService<MovieDto, Integer> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public CreateService<MovieDto> getCreateService() {
        return this.createService;
    }

    @Override
    public UpdateService<MovieDto, Integer> getUpdateService() {
        return this.updateService;
    }

    @Override
    public DeleteService<MovieDto, Integer> getDeleteService() {
        return this.deleteService;
    }

}