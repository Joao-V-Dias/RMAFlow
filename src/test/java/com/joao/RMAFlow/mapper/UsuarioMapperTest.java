package com.joao.RMAFlow.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.RecordComponent;

import org.junit.jupiter.api.Test;

import com.joao.RMAFlow.dto.response.UsuarioResponseDTO;
import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;

class UsuarioMapperTest {

    @Test
    void toResponseDTONuncaDeveExporSenha() {
        String senha = "senhaSecretaDoUsuario";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("Fulano")
                .login("fulano")
                .senha(senha)
                .perfil(PerfilUsuario.ESTOQUE)
                .ativo(true)
                .build();

        UsuarioResponseDTO dto = UsuarioMapper.toResponseDTO(usuario);

        RecordComponent[] componentes = UsuarioResponseDTO.class.getRecordComponents();
        assertThat(componentes)
                .extracting(RecordComponent::getName)
                .noneMatch(nome -> nome.toLowerCase().contains("senha"));

        assertThat(dto.toString()).doesNotContain(senha);
    }
}
