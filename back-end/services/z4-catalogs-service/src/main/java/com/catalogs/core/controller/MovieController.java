package com.catalogs.core.controller;

import com.catalogs.core.controller.interfaces.IMovieController;
import com.catalogs.core.entity.MovieEntity;
import com.catalogs.core.entity.MovieGenreEntity;
import com.catalogs.core.entity.MovieLanguageEntity;
import com.catalogs.utils.MediaGenericCreateService;
import com.catalogs.utils.MediaGenericFindAllService;
import com.catalogs.utils.MediaGenericFindByIdService;
import com.catalogs.utils.MediaGenericUpdateService;
import com.shared.core.service.*;
import com.shared.core.service.impl.GenericDeleteService;
import com.shared.dto.external.catalog.MovieDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movies")
public class MovieController implements IMovieController<MovieDto, Integer> {

    private final MediaGenericFindAllService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> findAllService;
    private final MediaGenericFindByIdService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> findByIdService;
    private final MediaGenericCreateService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> createService;
    private final MediaGenericUpdateService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> updateService;
    private final GenericDeleteService<MovieEntity, MovieDto, Integer> deleteService;

    public MovieController(
            @Qualifier("findAllMovieImpl") MediaGenericFindAllService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> findAllService,
            @Qualifier("findByIdMovieImpl") MediaGenericFindByIdService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> findByIdService,
            @Qualifier("createMovieImpl") MediaGenericCreateService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> createService,
            @Qualifier("updateMovieImpl") MediaGenericUpdateService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> updateService,
            @Qualifier("deleteMovieImpl") GenericDeleteService<MovieEntity, MovieDto, Integer> deleteService) {
        this.findAllService = findAllService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public MediaGenericFindAllService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public MediaGenericFindByIdService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public MediaGenericCreateService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> getCreateService() {
        return this.createService;
    }

    @Override
    public MediaGenericUpdateService<MovieGenreEntity, MovieLanguageEntity, MovieEntity, MovieDto, Integer> getUpdateService() {
        return this.updateService;
    }

    @Override
    public GenericDeleteService<MovieEntity, MovieDto, Integer> getDeleteService() {
        return this.deleteService;
    }

}