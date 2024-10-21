package com.catalogs.core.entity;

import com.shared.dto.custom.MediaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Incluye la lógica de la clase padre
@Entity
@Table(name = "series")
public class SerieEntity extends MediaEntity<SerieGenreEntity, SerieLanguageEntity> {

    @Id
    @Column(name = "serie_id")
    private UUID serieId;
    @OneToMany(mappedBy = "serie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<SerieGenreEntity> listGenres = new HashSet<>();
    @OneToMany(mappedBy = "serie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<SerieLanguageEntity> listLanguages = new HashSet<>();

    @Override
    public Set<SerieGenreEntity> getListGenresCustom() {
        return this.listGenres;
    }

    @Override
    public void setListGenresCustom(Set<SerieGenreEntity> listGenres) {
        this.listGenres = listGenres;
    }

    @Override
    public Set<SerieLanguageEntity> getListLanguagesCustom() {
        return this.listLanguages;
    }

    @Override
    public void setListLanguagesCustom(Set<SerieLanguageEntity> listLanguages) {
        this.listLanguages = listLanguages;
    }

}