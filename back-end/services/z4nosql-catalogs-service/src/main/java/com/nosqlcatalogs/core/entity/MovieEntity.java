package com.nosqlcatalogs.core.entity;

import com.shared.dto.external.catalog.MediaDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Incluye la lógica de la clase padre
@Document(collection = "movies")
public class MovieEntity extends MediaDto {

    @Id
    private UUID movieId;
    private LocalTime duration;

}