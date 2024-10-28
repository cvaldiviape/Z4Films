package com.studios.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "studios")
public class StudioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio_id")
    private Integer studioId;
    @Column(name = "name")
    private String name;
    @Column(name = "founded_year")
    private Integer foundedYear;
    @Column(name = "description")
    private String description;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "country_id")
    private Integer countryId;

}