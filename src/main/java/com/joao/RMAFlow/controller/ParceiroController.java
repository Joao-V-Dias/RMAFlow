package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.model.Parceiro;

public interface ParceiroController {

    ResponseEntity<List<Parceiro>> listar();

    ResponseEntity<Parceiro> buscarPorId(Long id);

    ResponseEntity<Parceiro> criar(Parceiro parceiro);

    ResponseEntity<Parceiro> atualizar(Long id, Parceiro parceiro);

    ResponseEntity<Void> excluir(Long id);
}
