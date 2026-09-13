package com.joao.RMAFlow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

    boolean existsByPerfil(PerfilUsuario perfil);
}
