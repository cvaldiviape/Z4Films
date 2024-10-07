package com.catalogs.core.entity;

import com.catalogs.core.entity.ids.MovieLanguageId;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movies_languages")
public class MovieLanguageEntity {

    @EmbeddedId
    private MovieLanguageId id; // Clave compuesta que incluye movieId y genreId
    @EqualsAndHashCode.Exclude // Excluye `movie` del cálculo de `hashCode` y `equals` para evitar recursividad
    @ManyToOne
    @MapsId("movieId") // Vincula el campo movieId en MovieGenreId con MovieEntity
    @JoinColumn(name = "movie_id")
    private MovieEntity movie; // Relación con la entidad MovieEntity

}