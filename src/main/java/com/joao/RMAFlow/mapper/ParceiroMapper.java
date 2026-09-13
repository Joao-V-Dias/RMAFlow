package com.joao.RMAFlow.mapper;

import com.joao.RMAFlow.dto.request.ParceiroRequestDTO;
import com.joao.RMAFlow.dto.response.ParceiroResponseDTO;
import com.joao.RMAFlow.dto.response.ParceiroResumoDTO;
import com.joao.RMAFlow.model.Parceiro;

public final class ParceiroMapper {

    private ParceiroMapper() {
    }

    public static Parceiro toEntity(ParceiroRequestDTO dto) {
        return Parceiro.builder()
                .nome(dto.nome())
                .cpfCnpj(dto.cpfCnpj())
                .endereco(dto.endereco())
                .telefone(dto.telefone())
                .email(dto.email())
                .build();
    }

    public static ParceiroResponseDTO toResponseDTO(Parceiro parceiro) {
        return new ParceiroResponseDTO(
                parceiro.getId(),
                parceiro.getNome(),
                parceiro.getCpfCnpj(),
                parceiro.getEndereco(),
                parceiro.getTelefone(),
                parceiro.getEmail()
        );
    }

    public static ParceiroResumoDTO toResumoDTO(Parceiro parceiro) {
        return new ParceiroResumoDTO(parceiro.getId(), parceiro.getNome());
    }
}
