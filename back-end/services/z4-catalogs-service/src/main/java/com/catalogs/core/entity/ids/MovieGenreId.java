package com.catalogs.core.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class MovieGenreId {

    @Column(name = "movie_id")
    private UUID movieId;
    @Column(name = "genre_id")
    private Integer genreId;

}