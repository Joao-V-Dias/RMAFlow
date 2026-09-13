package com.joao.RMAFlow.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Carrega o Usuario autenticado a partir do UsuarioRepository, mapeando o PerfilUsuario para uma
 * authority no formato ROLE_<PERFIL>. Usuarios com ativo = false sao marcados como desabilitados,
 * o que faz o DaoAuthenticationProvider recusar o login com DisabledException.
 */
@Service
@RequiredArgsConstructor
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado: " + login));

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .disabled(!usuario.isAtivo())
                .authorities(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().name()))
                .build();
    }
}
