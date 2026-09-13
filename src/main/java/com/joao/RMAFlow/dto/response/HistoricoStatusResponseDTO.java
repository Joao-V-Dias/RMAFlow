package com.joao.RMAFlow.dto.response;

import java.time.LocalDateTime;

import com.joao.RMAFlow.model.enums.StatusEquipamento;

public record HistoricoStatusResponseDTO(
        Long id,
        EquipamentoResumoDTO equipamento,
        UsuarioResumoDTO usuario,
        StatusEquipamento statusAnterior,
        StatusEquipamento statusNovo,
        LocalDateTime dataAlteracao
) {
}
