package com.catalogs.core.entity;

import com.catalogs.core.entity.ids.SerieGenreId;
import jakarta.persistence.*;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "series_genres")
public class SerieGenreEntity {

    @EmbeddedId
    private SerieGenreId id; // Clave compuesta que incluye serieId y genreId
    @ManyToOne
    @MapsId("serieId") // Vincula el campo serieId en SerieGenreId con SerieEntity
    @JoinColumn(name = "serie_id")
    private SerieEntity serie; // Relación con la entidad SerieEntity

}