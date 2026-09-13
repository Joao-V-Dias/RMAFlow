package com.joao.RMAFlow.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.repository.UsuarioRepository;

/**
 * Prova, contra um banco real (H2), que a query derivada findByLogin realmente funciona -
 * um mock de UsuarioRepository nunca poderia garantir isso.
 */
@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryIT {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void findByLoginRetornaUsuarioQuandoExisteEVazioQuandoNaoExiste() {
        Usuario usuario = Usuario.builder()
                .nome("Fulano")
                .login("fulano.integracao")
                .senha("hash-qualquer")
                .perfil(PerfilUsuario.ESTOQUE)
                .build();
        usuarioRepository.save(usuario);

        Optional<Usuario> encontrado = usuarioRepository.findByLogin("fulano.integracao");
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getLogin()).isEqualTo("fulano.integracao");

        Optional<Usuario> naoEncontrado = usuarioRepository.findByLogin("login-que-nao-existe");
        assertThat(naoEncontrado).isEmpty();
    }
}
