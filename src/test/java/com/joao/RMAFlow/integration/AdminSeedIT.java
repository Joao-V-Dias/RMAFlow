package com.joao.RMAFlow.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.repository.UsuarioRepository;

/**
 * Sobe o contexto completo (AdminSeeder e um CommandLineRunner real, nao mockado) contra um H2
 * vazio e prova que o seed do ADMIN esta de fato conectado ao ciclo de vida do Spring - o teste
 * de unidade equivalente (AdminSeederTest) ja prova a logica isolada, mockada.
 */
@SpringBootTest
@ActiveProfiles("test")
class AdminSeedIT {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void contextoDeveNascerComExatamenteUmAdmin() {
        List<Usuario> admins = usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getPerfil() == PerfilUsuario.ADMIN)
                .toList();

        assertThat(admins).hasSize(1);
    }
}
