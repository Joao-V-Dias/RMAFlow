package com.joao.RMAFlow.mapper;

import com.joao.RMAFlow.dto.response.HistoricoStatusResponseDTO;
import com.joao.RMAFlow.model.HistoricoStatus;

public final class HistoricoStatusMapper {

    private HistoricoStatusMapper() {
    }

    public static HistoricoStatusResponseDTO toResponseDTO(HistoricoStatus historicoStatus) {
        return new HistoricoStatusResponseDTO(
                historicoStatus.getId(),
                EquipamentoMapper.toResumoDTO(historicoStatus.getEquipamento()),
                UsuarioMapper.toResumoDTO(historicoStatus.getUsuario()),
                historicoStatus.getStatusAnterior(),
                historicoStatus.getStatusNovo(),
                historicoStatus.getDataAlteracao()
        );
    }
}
