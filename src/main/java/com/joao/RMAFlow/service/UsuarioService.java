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
}
