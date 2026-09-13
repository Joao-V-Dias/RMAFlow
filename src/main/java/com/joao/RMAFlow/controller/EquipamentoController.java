package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.model.Equipamento;

public interface EquipamentoController {

    ResponseEntity<List<Equipamento>> listar();

    ResponseEntity<Equipamento> buscarPorId(Long id);

    ResponseEntity<Equipamento> criar(Equipamento equipamento);

    ResponseEntity<Equipamento> atualizar(Long id, Equipamento equipamento);

    ResponseEntity<Void> excluir(Long id);
}
