package com.joao.RMAFlow.dto.response;

import java.time.LocalDateTime;

public record ManutencaoResponseDTO(
        Long id,
        EquipamentoResumoDTO equipamento,
        boolean viavel,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String observacoes
) {
}
