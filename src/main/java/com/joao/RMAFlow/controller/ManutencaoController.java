package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.model.Manutencao;

public interface ManutencaoController {

    ResponseEntity<List<Manutencao>> listar();

    ResponseEntity<Manutencao> buscarPorId(Long id);

    ResponseEntity<Manutencao> criar(Manutencao manutencao);

    ResponseEntity<Manutencao> atualizar(Long id, Manutencao manutencao);

    ResponseEntity<Void> excluir(Long id);
}
