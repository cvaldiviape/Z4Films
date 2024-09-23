package com.catalogs.core.entity;

import com.catalogs.core.entity.ids.SerieLanguageId;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "series_languages")
public class SerieLanguageEntity {

    @EmbeddedId
    private SerieLanguageId id; // Clave compuesta que incluye serieId y genreId
    @ManyToOne
    @MapsId("serieId") // Vincula el campo serieId en SerieGenreId con SerieEntity
    @JoinColumn(name = "serie_id")
    private SerieEntity serie; // Relación con la entidad SerieEntity

}