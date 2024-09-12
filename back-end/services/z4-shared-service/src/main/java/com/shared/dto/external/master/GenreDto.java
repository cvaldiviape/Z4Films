package com.shared.dto.external.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shared.dto.custom.CommonDto;
import com.shared.utils.filter.Searchable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Incluye la lógica de la clase padre
public class GenreDto extends CommonDto implements Searchable<Integer> {

    private Integer genreId;

    @JsonIgnore
    @Override
    public Integer getSearcheableField() {
        return this.genreId;
    }

}