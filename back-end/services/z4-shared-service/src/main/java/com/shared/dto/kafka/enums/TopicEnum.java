package com.shared.dto.kafka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopicEnum {

    MOVIE("movie-topic"),
    SERIE("serie-topic");

    private final String name;

}