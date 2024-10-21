package com.shared.dto.external.catalog;

import com.shared.dto.external.master.GenreDto;
import com.shared.dto.external.master.LanguageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Incluye la lógica de la clase padre
public class MovieDto extends MediaDto {

    private UUID movieId;
    private LocalTime duration;

}