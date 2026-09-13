package com.joao.RMAFlow.mapper;

import com.joao.RMAFlow.dto.request.TesteRequestDTO;
import com.joao.RMAFlow.dto.response.TesteResponseDTO;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.Teste;
import com.joao.RMAFlow.model.Usuario;

public final class TesteMapper {

    private TesteMapper() {
    }

    /**
     * Equipamento e Usuario (responsavel) ja devem ter sido resolvidos pelo controller.impl
     * antes de chamar este metodo.
     */
    public static Teste toEntity(TesteRequestDTO dto, Equipamento equipamento, Usuario responsavel) {
        return Teste.builder()
                .equipamento(equipamento)
                .responsavel(responsavel)
                .dataTeste(dto.dataTeste())
                .resultado(dto.resultado())
                .observacoes(dto.observacoes())
                .build();
    }

    public static TesteResponseDTO toResponseDTO(Teste teste) {
        return new TesteResponseDTO(
                teste.getId(),
                EquipamentoMapper.toResumoDTO(teste.getEquipamento()),
                UsuarioMapper.toResumoDTO(teste.getResponsavel()),
                teste.getDataTeste(),
                teste.getResultado(),
                teste.getObservacoes()
        );
    }
}
