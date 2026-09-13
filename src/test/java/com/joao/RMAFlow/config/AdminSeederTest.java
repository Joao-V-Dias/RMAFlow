package com.joao.RMAFlow.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.repository.UsuarioRepository;

/**
 * Nomeado a partir da classe de producao real (AdminSeeder), que implementa exatamente a mesma
 * regra descrita em RMAFLOW_TESTES_UNITARIOS.md para o item 3 (AdminSeedRunnerTest).
 */
@ExtendWith(MockitoExtension.class)
class AdminSeederTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminSeeder adminSeeder;

    @Test
    void deveCriarAdminQuandoNaoExiste() throws Exception {
        ReflectionTestUtils.setField(adminSeeder, "adminLogin", "admin");
        ReflectionTestUtils.setField(adminSeeder, "adminSenhaPadrao", "admin123");

        when(usuarioRepository.existsByPerfil(PerfilUsuario.ADMIN)).thenReturn(false);
        when(passwordEncoder.encode("admin123")).thenReturn("hash-simulado");

        adminSeeder.run();

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void naoDeveCriarAdminQuandoJaExiste() throws Exception {
        when(usuarioRepository.existsByPerfil(PerfilUsuario.ADMIN)).thenReturn(true);

        adminSeeder.run();

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
