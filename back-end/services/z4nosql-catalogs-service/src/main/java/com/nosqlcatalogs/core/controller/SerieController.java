package com.nosqlcatalogs.core.controller;

import com.nosqlcatalogs.core.entity.SerieEntity;
import com.nosqlcatalogs.core.service.SerieService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/nosql/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public Flowable<SerieEntity> getAll() {
        return serieService.getAllSeries();
    }

    @GetMapping("/{id}")
    public Maybe<SerieEntity> getById(@PathVariable String id) {
        return serieService.getSerieById(id);
    }

    @PostMapping
    public Single<SerieEntity> create(@RequestBody SerieEntity movie) {
        return serieService.createSerie(movie);
    }

    @DeleteMapping("/{id}")
    public Maybe<Void> deleteById(@PathVariable String id) {
        return serieService.deleteMovieById(id);
    }

}