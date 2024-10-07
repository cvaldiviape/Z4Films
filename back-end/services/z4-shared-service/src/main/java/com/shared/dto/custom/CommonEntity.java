package com.shared.dto.custom;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass // se utiliza para crear clases superiores que proporcionan atributos y comportamiento comunes a las clases descendientes, pero que no se mapean directamente a tablas en la base de datos.
public abstract class CommonEntity {

    @Column(length = 6, nullable = false, unique = true)
    private String code;
    @Column(length = 60, nullable = false)
    private String name;

}