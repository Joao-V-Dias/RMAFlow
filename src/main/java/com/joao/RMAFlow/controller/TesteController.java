package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.model.Teste;

public interface TesteController {

    ResponseEntity<List<Teste>> listar();

    ResponseEntity<Teste> buscarPorId(Long id);

    ResponseEntity<Teste> criar(Teste teste);

    ResponseEntity<Teste> atualizar(Long id, Teste teste);

    ResponseEntity<Void> excluir(Long id);
}
