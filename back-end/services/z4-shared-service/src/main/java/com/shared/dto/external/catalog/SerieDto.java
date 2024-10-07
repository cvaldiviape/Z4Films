package com.shared.dto.external.catalog;

import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Incluye la lógica de la clase padre
public class SerieDto extends MediaDto {

    private Integer serieId;
    private Set<GenreDto> listGenres = new HashSet<>();
    private Set<LanguageDto> listLanguages = new HashSet<>();

}