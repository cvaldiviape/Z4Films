package com.catalogs.core.entity;

import com.shared.dto.custom.MediaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Incluye la lógica de la clase padre
@Entity
@Table(name = "movies")
public class MovieEntity extends MediaEntity<MovieGenreEntity, MovieLanguageEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "duration", nullable = false)
    private LocalTime duration;
    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<MovieGenreEntity> listGenres = new HashSet<>();
    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<MovieLanguageEntity> listLanguages = new HashSet<>();

    @Override
    public Set<MovieGenreEntity> getListGenresCustom() {
        return this.listGenres;
    }

    @Override
    public void setListGenresCustom(Set<MovieGenreEntity> listGenres) {
        this.listGenres = listGenres;
    }

    @Override
    public Set<MovieLanguageEntity> getListLanguagesCustom() {
        return this.listLanguages;
    }

    @Override
    public void setListLanguagesCustom(Set<MovieLanguageEntity> listLanguages) {
        this.listLanguages = listLanguages;
    }

}