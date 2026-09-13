package com.joao.RMAFlow.mapper;

import com.joao.RMAFlow.dto.request.UsuarioRequestDTO;
import com.joao.RMAFlow.dto.response.UsuarioResponseDTO;
import com.joao.RMAFlow.dto.response.UsuarioResumoDTO;
import com.joao.RMAFlow.model.Usuario;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    /**
     * Senha copiada em texto puro; o hash continua sendo responsabilidade do service, como
     * definido em RMAFLOW_AUTH.md.
     */
    public static Usuario toEntity(UsuarioRequestDTO dto) {
        return Usuario.builder()
                .nome(dto.nome())
                .login(dto.login())
                .senha(dto.senha())
                .telefone(dto.telefone())
                .endereco(dto.endereco())
                .perfil(dto.perfil())
                .build();
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getTelefone(),
                usuario.getEndereco(),
                usuario.getPerfil(),
                usuario.isAtivo()
        );
    }

    public static UsuarioResumoDTO toResumoDTO(Usuario usuario) {
        return new UsuarioResumoDTO(usuario.getId(), usuario.getNome());
    }
}
