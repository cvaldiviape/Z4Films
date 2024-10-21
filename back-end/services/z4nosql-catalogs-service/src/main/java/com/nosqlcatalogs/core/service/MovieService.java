package com.nosqlcatalogs.core.service;

import com.nosqlcatalogs.core.entity.MovieEntity;
import com.nosqlcatalogs.core.repository.MovieRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Flowable<MovieEntity> getAllMovies() {
        Flux<MovieEntity> monoMovie = this.movieRepository.findAll();
        return Flowable.fromPublisher(monoMovie);
    }

    public Maybe<MovieEntity> getMovieById(String id) {
        Mono<MovieEntity> monoMovie = this.movieRepository.findById(id);
        return Maybe.fromPublisher(monoMovie);
    }

    public Single<MovieEntity> createMovie(MovieEntity movie) {
        Mono<MovieEntity> monoMovie = this.movieRepository.save(movie);
        return Single.fromPublisher(monoMovie);
    }

    public Maybe<Void> deleteMovieById(String id) {
        Mono<Void> monoMovie = this.movieRepository.deleteById(id);
        return Maybe.fromPublisher(monoMovie);
    }

}