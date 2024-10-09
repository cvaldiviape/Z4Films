package com.shared.dto.custom;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class MediaEntity<GENRE, LANGUAGE> {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "synopsis", nullable = false)
    private String synopsis;
    @Column(name = "year", nullable = false)
    private Integer year;
    @Column(name = "url_image", nullable = false)
    private String urlImage;
    @Column(name = "audience_id", nullable = false)
    private Integer audienceId;
    @Column(name = "studio_id", nullable = false)
    private Integer studioId;

    public abstract Set<GENRE> getListGenresCustom();
    public abstract void setListGenresCustom(Set<GENRE> listGenres);
    public abstract Set<LANGUAGE> getListLanguagesCustom();
    public abstract void setListLanguagesCustom(Set<LANGUAGE> listLanguages);

    public void setGenres(Set<GENRE> listGenres) {
        if (this.getListGenresCustom() == null) {
            this.setListGenresCustom(listGenres);
        } else {
            this.getListGenresCustom().retainAll(listGenres);
            this.getListGenresCustom().addAll(listGenres);
        }
    }

    public void setLanguages(Set<LANGUAGE> listLanguages) {
        if (this.getListLanguagesCustom() == null) {
            this.setListLanguagesCustom(listLanguages);
        } else {
            this.getListLanguagesCustom().retainAll(listLanguages);
            this.getListLanguagesCustom().addAll(listLanguages);
        }
    }

}