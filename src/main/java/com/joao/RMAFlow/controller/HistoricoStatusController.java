package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.response.HistoricoStatusResponseDTO;

public interface HistoricoStatusController {

    ResponseEntity<List<HistoricoStatusResponseDTO>> listar();

    ResponseEntity<HistoricoStatusResponseDTO> buscarPorId(Long id);

    ResponseEntity<Void> excluir(Long id);
}
