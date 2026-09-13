package com.joao.RMAFlow.dto.response;

import java.time.LocalDateTime;

import com.joao.RMAFlow.model.enums.StatusEquipamento;

public record EquipamentoResponseDTO(
        Long id,
        String numeroSerie,
        ModeloResumoDTO modelo,
        ParceiroResumoDTO parceiro,
        StatusEquipamento status,
        boolean obsoleto,
        LocalDateTime dataEntrada
) {
}
