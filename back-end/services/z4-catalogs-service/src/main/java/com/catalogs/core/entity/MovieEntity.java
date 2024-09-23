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
    @OneToMany(mappedBy = "movie")
    private Set<MovieGenreEntity> listGenres = new HashSet<>();
    @OneToMany(mappedBy = "movie")
    private Set<MovieLanguageEntity> listLanguages = new HashSet<>();

}