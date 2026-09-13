package com.joao.RMAFlow.dto.request;

import java.time.LocalDateTime;

import com.joao.RMAFlow.model.enums.ResultadoTeste;

import jakarta.validation.constraints.NotNull;

public record TesteRequestDTO(
        @NotNull Long equipamentoId,
        @NotNull Long responsavelId,
        @NotNull LocalDateTime dataTeste,
        @NotNull ResultadoTeste resultado,
        String observacoes
) {
}
