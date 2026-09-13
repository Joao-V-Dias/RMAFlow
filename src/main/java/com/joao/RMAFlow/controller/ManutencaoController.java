package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.request.ManutencaoRequestDTO;
import com.joao.RMAFlow.dto.response.ManutencaoResponseDTO;

public interface ManutencaoController {

    ResponseEntity<List<ManutencaoResponseDTO>> listar();

    ResponseEntity<ManutencaoResponseDTO> buscarPorId(Long id);

    ResponseEntity<ManutencaoResponseDTO> criar(ManutencaoRequestDTO dto);

    ResponseEntity<ManutencaoResponseDTO> atualizar(Long id, ManutencaoRequestDTO dto);

    ResponseEntity<Void> excluir(Long id);
}
