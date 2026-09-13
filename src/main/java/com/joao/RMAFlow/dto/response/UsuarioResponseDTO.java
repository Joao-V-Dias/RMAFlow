package com.joao.RMAFlow.dto.response;

import com.joao.RMAFlow.model.enums.PerfilUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String login,
        String telefone,
        String endereco,
        PerfilUsuario perfil,
        boolean ativo
) {
}
