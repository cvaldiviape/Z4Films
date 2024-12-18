package com.nosqlcatalogs.core.service;

import com.nosqlcatalogs.core.entity.SerieEntity;
import com.nosqlcatalogs.core.repository.SerieReactiveRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SerieService {

    @Autowired
    private SerieReactiveRepository serieReactiveRepository;

    public Flowable<SerieEntity> getAllSeries() {
        Flux<SerieEntity> monoMovie = this.serieReactiveRepository.findAll();
        return Flowable.fromPublisher(monoMovie);
    }

    public Maybe<SerieEntity> getSerieById(String id) {
        Mono<SerieEntity> monoMovie = this.serieReactiveRepository.findById(id);
        return Maybe.fromPublisher(monoMovie);
    }

    public Single<SerieEntity> createSerie(SerieEntity movie) {
        Mono<SerieEntity> monoMovie = this.serieReactiveRepository.save(movie);
        return Single.fromPublisher(monoMovie);
    }

    public Maybe<Void> deleteMovieById(String id) {
        Mono<Void> monoMovie = this.serieReactiveRepository.deleteById(id);
        return Maybe.fromPublisher(monoMovie);
    }

}