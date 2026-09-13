package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.model.HistoricoStatus;

public interface HistoricoStatusController {

    ResponseEntity<List<HistoricoStatus>> listar();

    ResponseEntity<HistoricoStatus> buscarPorId(Long id);

    ResponseEntity<HistoricoStatus> criar(HistoricoStatus historicoStatus);

    ResponseEntity<HistoricoStatus> atualizar(Long id, HistoricoStatus historicoStatus);

    ResponseEntity<Void> excluir(Long id);
}
