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
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class MovieLanguageId {

    @Column(name = "movie_id")
    private UUID movieId;
    @Column(name = "language_id")
    private Integer languageId;
    @Enumerated(EnumType.STRING)
    @Column(name = "audio_format")
    private AudioFormatEnum audioFormat;  // Enum for language type (SUBTITLED, DUBBED, ORIGINAL)

}