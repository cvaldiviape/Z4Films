package com.shared.dto.external.catalog;

import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import com.shared.dto.external.studio.StudioDto;
import com.shared.enums.AudienceEnum;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class MediaDto {

    private String name;
    private String synopsis;
    private Integer year;
    private String urlImage;
    private AudienceEnum audience;
    private Integer studioId;
    private StudioDto studio;
    private Set<GenreDto> listGenres = new HashSet<>();
    private Set<LanguageDto> listLanguages = new HashSet<>();

}