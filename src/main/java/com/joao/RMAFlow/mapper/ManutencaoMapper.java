package com.joao.RMAFlow.mapper;

import com.joao.RMAFlow.dto.request.ManutencaoRequestDTO;
import com.joao.RMAFlow.dto.response.ManutencaoResponseDTO;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.Manutencao;

public final class ManutencaoMapper {

    private ManutencaoMapper() {
    }

    /**
     * Equipamento ja deve ter sido resolvido pelo controller.impl antes de chamar este metodo.
     */
    public static Manutencao toEntity(ManutencaoRequestDTO dto, Equipamento equipamento) {
        return Manutencao.builder()
                .equipamento(equipamento)
                .viavel(dto.viavel())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .observacoes(dto.observacoes())
                .build();
    }

    public static ManutencaoResponseDTO toResponseDTO(Manutencao manutencao) {
        return new ManutencaoResponseDTO(
                manutencao.getId(),
                EquipamentoMapper.toResumoDTO(manutencao.getEquipamento()),
                manutencao.isViavel(),
                manutencao.getDataInicio(),
                manutencao.getDataFim(),
                manutencao.getObservacoes()
        );
    }
}
