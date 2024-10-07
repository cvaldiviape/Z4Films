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
public class MovieEntity extends MediaEntity {

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

    public void setGenres(Set<MovieGenreEntity> listGenres) {
        if (this.listGenres == null) {
            this.listGenres = listGenres;
        } else {
            this.listGenres.retainAll(listGenres);
            this.listGenres.addAll(listGenres);
        }
    }

    public void setLanguages(Set<MovieLanguageEntity> listLanguages) {
        if (this.listLanguages == null) {
            this.listLanguages = listLanguages;
        } else {
            this.listLanguages.retainAll(listLanguages);
            this.listLanguages.addAll(listLanguages);
        }
    }

}