package com.joao.RMAFlow.dto.response;

import java.time.LocalDateTime;

import com.joao.RMAFlow.model.enums.ResultadoTeste;

public record TesteResponseDTO(
        Long id,
        EquipamentoResumoDTO equipamento,
        UsuarioResumoDTO responsavel,
        LocalDateTime dataTeste,
        ResultadoTeste resultado,
        String observacoes
) {
}
