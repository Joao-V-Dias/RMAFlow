package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.model.Usuario;

public interface UsuarioController {

    ResponseEntity<List<Usuario>> listar();

    ResponseEntity<Usuario> buscarPorId(Long id);

    ResponseEntity<Usuario> criar(Usuario usuario);

    ResponseEntity<Usuario> atualizar(Long id, Usuario usuario);

    ResponseEntity<Void> excluir(Long id);

    ResponseEntity<Usuario> inativar(Long id);
}
