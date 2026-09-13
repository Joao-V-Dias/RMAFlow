package com.joao.RMAFlow.dto.response;

public record ModeloResponseDTO(
        Long id,
        String nome,
        String fabricante,
        String tipo,
        String especificacoes
) {
}
