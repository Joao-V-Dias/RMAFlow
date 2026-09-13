package com.joao.RMAFlow.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record ManutencaoRequestDTO(
        @NotNull Long equipamentoId,
        boolean viavel,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String observacoes
) {
}
