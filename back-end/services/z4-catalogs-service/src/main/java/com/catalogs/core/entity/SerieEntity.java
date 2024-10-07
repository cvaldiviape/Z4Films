package com.catalogs.core.entity;

import com.shared.dto.custom.MediaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Incluye la lógica de la clase padre
@Entity
@Table(name = "series")
public class SerieEntity extends MediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serie_id")
    private Integer serieId;
    @OneToMany(mappedBy = "serie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<SerieGenreEntity> listGenres = new HashSet<>();
    @OneToMany(mappedBy = "serie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<SerieLanguageEntity> listLanguages = new HashSet<>();

    public void setGenres(Set<SerieGenreEntity> listGenres) {
        if (this.listGenres == null) {
            this.listGenres = listGenres;
        } else {
            this.listGenres.retainAll(listGenres);
            this.listGenres.addAll(listGenres);
        }
    }

    public void setLanguages(Set<SerieLanguageEntity> listLanguages) {
        if (this.listLanguages == null) {
            this.listLanguages = listLanguages;
        } else {
            this.listLanguages.retainAll(listLanguages);
            this.listLanguages.addAll(listLanguages);
        }
    }

}