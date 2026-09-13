package com.joao.RMAFlow.mapper;

import com.joao.RMAFlow.dto.request.EquipamentoRequestDTO;
import com.joao.RMAFlow.dto.response.EquipamentoResponseDTO;
import com.joao.RMAFlow.dto.response.EquipamentoResumoDTO;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.Modelo;
import com.joao.RMAFlow.model.Parceiro;

public final class EquipamentoMapper {

    private EquipamentoMapper() {
    }

    /**
     * Modelo e Parceiro ja devem ter sido resolvidos (via os respectivos Services) pelo
     * controller.impl antes de chamar este metodo.
     */
    public static Equipamento toEntity(EquipamentoRequestDTO dto, Modelo modelo, Parceiro parceiro) {
        return Equipamento.builder()
                .numeroSerie(dto.numeroSerie())
                .modelo(modelo)
                .parceiro(parceiro)
                .build();
    }

    public static EquipamentoResponseDTO toResponseDTO(Equipamento equipamento) {
        return new EquipamentoResponseDTO(
                equipamento.getId(),
                equipamento.getNumeroSerie(),
                ModeloMapper.toResumoDTO(equipamento.getModelo()),
                ParceiroMapper.toResumoDTO(equipamento.getParceiro()),
                equipamento.getStatus(),
                equipamento.isObsoleto(),
                equipamento.getDataEntrada()
        );
    }

    public static EquipamentoResumoDTO toResumoDTO(Equipamento equipamento) {
        return new EquipamentoResumoDTO(
                equipamento.getId(),
                equipamento.getNumeroSerie(),
                equipamento.getModelo().getNome()
        );
    }
}
