package com.joao.RMAFlow.mapper;

import com.joao.RMAFlow.dto.request.NotaFiscalRequestDTO;
import com.joao.RMAFlow.dto.response.NotaFiscalResponseDTO;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.NotaFiscal;
import com.joao.RMAFlow.model.Parceiro;

public final class NotaFiscalMapper {

    private NotaFiscalMapper() {
    }

    /**
     * Equipamento e Parceiro (destinatario) ja devem ter sido resolvidos pelo controller.impl
     * antes de chamar este metodo. dataEmissao e status sao geridos pelo fluxo de emissao, nao
     * pelo request.
     */
    public static NotaFiscal toEntity(NotaFiscalRequestDTO dto, Equipamento equipamento, Parceiro parceiro) {
        return NotaFiscal.builder()
                .equipamento(equipamento)
                .parceiro(parceiro)
                .tipo(dto.tipo())
                .valor(dto.valor())
                .build();
    }

    public static NotaFiscalResponseDTO toResponseDTO(NotaFiscal notaFiscal) {
        return new NotaFiscalResponseDTO(
                notaFiscal.getId(),
                EquipamentoMapper.toResumoDTO(notaFiscal.getEquipamento()),
                ParceiroMapper.toResumoDTO(notaFiscal.getParceiro()),
                notaFiscal.getTipo(),
                notaFiscal.getValor(),
                notaFiscal.getDataEmissao(),
                notaFiscal.getStatus()
        );
    }
}
