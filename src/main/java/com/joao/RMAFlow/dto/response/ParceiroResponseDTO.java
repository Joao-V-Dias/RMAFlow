package com.joao.RMAFlow.dto.response;

public record ParceiroResponseDTO(
        Long id,
        String nome,
        String cpfCnpj,
        String endereco,
        String telefone,
        String email
) {
}
