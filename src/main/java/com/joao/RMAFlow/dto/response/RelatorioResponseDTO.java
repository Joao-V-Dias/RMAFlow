package com.joao.RMAFlow.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import com.joao.RMAFlow.model.enums.StatusEquipamento;

/**
 * Agregado calculado, nunca persistido. Nao ha, ainda, servico/controller de relatorios — este
 * DTO existe apenas para fixar o contrato de resposta com antecedencia, conforme RMAFLOW_DTOS.md.
 */
public record RelatorioResponseDTO(
        long totalEquipamentos,
        Map<StatusEquipamento, Long> totalPorStatus,
        LocalDateTime geradoEm
) {
}
