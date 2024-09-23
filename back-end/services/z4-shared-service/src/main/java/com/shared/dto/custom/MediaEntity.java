package com.shared.dto.custom;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class MediaEntity {

    @Column(name = "name", nullable = false)
    private Integer name;
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

}