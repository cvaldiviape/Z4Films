package com.shared.dto.external.studio;

import jakarta.persistence.Column;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class StudioDto {

    private Integer studioId;
    private String name;
    private Integer foundedYear;
    private String description;
    private Boolean active;
    private Integer countryId;

}