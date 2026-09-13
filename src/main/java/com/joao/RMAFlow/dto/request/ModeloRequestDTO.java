package com.joao.RMAFlow.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ModeloRequestDTO(
        @NotBlank String nome,
        @NotBlank String fabricante,
        @NotBlank String tipo,
        String especificacoes
) {
}
