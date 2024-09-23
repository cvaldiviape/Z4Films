package com.catalogs.core.entity;

import com.catalogs.core.entity.ids.MovieGenreId;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movies_genres")
public class MovieGenreEntity {

    @EmbeddedId
    private MovieGenreId id; // Clave compuesta que incluye movieId y genreId
    @ManyToOne
    @MapsId("movieId") // Vincula el campo movieId en MovieGenreId con MovieEntity
    @JoinColumn(name = "movie_id")
    private MovieEntity movie; // Relación con la entidad MovieEntity

}