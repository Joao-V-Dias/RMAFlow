package com.joao.RMAFlow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentoRequestDTO(
        @NotBlank String numeroSerie,
        @NotNull Long modeloId,
        @NotNull Long parceiroId
) {
}
