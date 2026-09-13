package com.joao.RMAFlow.dto.request;

import com.joao.RMAFlow.model.enums.PerfilUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotBlank String nome,
        @NotBlank String login,
        String senha,
        String telefone,
        String endereco,
        @NotNull PerfilUsuario perfil
) {
}
