package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.Usuario;

public interface UsuarioService {

    Usuario salvar(Usuario usuario);

    Usuario buscarPorId(Long id);

    Usuario buscarPorLogin(String login);

    List<Usuario> listarTodos();

    Usuario atualizar(Long id, Usuario usuario);

    void excluir(Long id);

    /**
     * Inativa o usuario (bloqueia login) em vez de exclui-lo do banco. Restrito ao perfil ADMIN.
     */
    Usuario inativar(Long id);
}
