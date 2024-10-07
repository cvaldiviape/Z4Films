package com.shared.enums;

import lombok.Getter;

@Getter
public enum ValueEnum {
    NAME("nombre"),
    CODE("código"),
    DESCRIPTION("descripción"),
    DNI("dni"),
    EMAIL("email"),
    CATEGORY("Categoria"),
    COUNTRY("Pais"),
    GENRE("Gémero"),
    LANGUAGE("Lenguaje"),
    AUDIENCE("Tipo de audiencia"),
    TYPE_STATUS_FILM_SHOW("Tipo estado de pelicula"),
    PRODUCT("Producto"),
    ROOM("Sala"),
    SEAT("Asiento"),
    ESTABLISHMENT("Establecimiento"),
    DEPARTMENT("Departamento"),
    DISTRICT("Distrito"),
    PROVINCE("Province"),
    STUDIO("Estudio"),
    MOVIE("Pelicula"),
    SERIE("Serie"),
    CUSTOMER("Cliente"),
    LIST_CATEGORY("Lista de categorias"),
    LIST_COUNTRY("Lista de paises"),
    LIST_GENRE("Lista de géneros"),
    LIST_LANGUAGE("Lista de lenguajes"),
    LIST_AUDIENCE("Lista de tipos de audiencias"),
    LIST_TYPE_STATUS_FILM_SHOW("Lista de tipos de estado de peliculas"),
    LIST_PRODUCT("Lista de productos"),
    LIST_ROOM("Lista de salsa"),
    LIST_SEAT("Lista de asientos"),
    LIST_ESTABLISHMENT("Lista de establecimientos"),
    LIST_DEPARTMENT("Lista de departamentos"),
    LIST_DISTRICT("Lista de distritos"),
    LIST_PROVINCE("Lista de provincias"),
    LIST_STUDIO("Lista de estudios"),
    LIST_MOVIE("Lista de peliculas"),
    LIST_SERIE("Lista de peliculas");

    private final String value;

    private ValueEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
