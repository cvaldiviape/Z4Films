package com.shared.dto.external.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
    private Integer audienceId;
    private Integer studioId;

}