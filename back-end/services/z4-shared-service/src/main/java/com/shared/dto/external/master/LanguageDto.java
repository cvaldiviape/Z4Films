package com.shared.dto.external.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shared.dto.custom.CommonDto;
import com.shared.enums.AudioFormatEnum;
import com.shared.utils.filter.Searchable;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Incluye la lógica de la clase padre
public class LanguageDto extends CommonDto implements Searchable<Integer> {

    private Integer languageId;
    private AudioFormatEnum audioFormat;

    @JsonIgnore
    @Override
    public Integer getSearcheableField() {
        return this.languageId;
    }

}