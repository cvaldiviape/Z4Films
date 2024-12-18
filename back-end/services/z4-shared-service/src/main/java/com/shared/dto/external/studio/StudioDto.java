package com.shared.dto.external.studio;

import com.shared.dto.external.master.CountryDto;
import lombok.*;

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
    private CountryDto country;

}