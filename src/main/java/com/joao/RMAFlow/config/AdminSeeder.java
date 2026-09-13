package com.joao.RMAFlow.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Garante que sempre exista pelo menos um usuario ADMIN, ja que nao ha autocadastro no sistema.
 * Idempotente: so cria o admin padrao se nenhum usuario com perfil ADMIN existir ainda.
 *
 * ATENCAO: a senha padrao criada aqui (ver rmaflow.admin.senha-padrao) deve ser trocada no
 * primeiro acesso — nao ha, nesta etapa, um fluxo de troca obrigatoria de senha.
 */
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${rmaflow.admin.login:admin}")
    private String adminLogin;

    @Value("${rmaflow.admin.senha-padrao:admin123}")
    private String adminSenhaPadrao;

    @Override
    public void run(String... args) {
        if (usuarioRepository.existsByPerfil(PerfilUsuario.ADMIN)) {
            return;
        }

        Usuario admin = Usuario.builder()
                .nome("Administrador")
                .login(adminLogin)
                .senha(passwordEncoder.encode(adminSenhaPadrao))
                .perfil(PerfilUsuario.ADMIN)
                .ativo(true)
                .build();

        usuarioRepository.save(admin);

        log.warn("Usuario ADMIN padrao criado (login: '{}'). Troque a senha padrao no primeiro acesso.", adminLogin);
    }
}
