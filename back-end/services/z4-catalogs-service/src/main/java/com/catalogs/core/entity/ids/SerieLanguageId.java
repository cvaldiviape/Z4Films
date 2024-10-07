package com.catalogs.core.entity.ids;

import com.shared.enums.AudioFormatEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class SerieLanguageId {

    @Column(name = "serie_id")
    private Integer serieId;
    @Column(name = "language_id")
    private Integer languageId;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AudioFormatEnum type;  // Enum for language type (SUBTITLED, DUBBED, ORIGINAL)

}