package com.nosqlcatalogs.core.controller;

import com.nosqlcatalogs.core.entity.MovieEntity;
import com.nosqlcatalogs.core.service.MovieService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/nosql/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public Flowable<MovieEntity> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Maybe<MovieEntity> getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public Single<MovieEntity> createMovie(@RequestBody MovieEntity movie) {
        return movieService.createMovie(movie);
    }

    @DeleteMapping("/{id}")
    public Maybe<Void> deleteMovieById(@PathVariable String id) {
        return movieService.deleteMovieById(id);
    }

}