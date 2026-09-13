package com.joao.RMAFlow.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void salvarDeveArmazenarSenhaComoHashNuncaEmTextoPuro() {
        String senhaOriginal = "minhaSenha123";
        String senhaHash = "$2a$10$hashSimuladoParaTeste";

        Usuario usuario = Usuario.builder()
                .nome("Fulano")
                .login("fulano")
                .senha(senhaOriginal)
                .perfil(PerfilUsuario.ESTOQUE)
                .build();

        when(passwordEncoder.encode(senhaOriginal)).thenReturn(senhaHash);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        usuarioService.salvar(usuario);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());

        assertThat(captor.getValue().getSenha())
                .isNotEqualTo(senhaOriginal)
                .isEqualTo(senhaHash);
    }
}
