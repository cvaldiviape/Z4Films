package com.shared.dto.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetailsDto {

    private String errorMessage;
    private String stackTrace; // O puedes usar un array de StackTraceElement si prefieres
    private String originalPayload; // Este es el payload original que causó el error

}