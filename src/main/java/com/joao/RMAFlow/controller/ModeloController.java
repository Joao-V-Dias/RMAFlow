package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.request.ModeloRequestDTO;
import com.joao.RMAFlow.dto.response.ModeloResponseDTO;

public interface ModeloController {

    ResponseEntity<List<ModeloResponseDTO>> listar();

    ResponseEntity<ModeloResponseDTO> buscarPorId(Long id);

    ResponseEntity<ModeloResponseDTO> criar(ModeloRequestDTO dto);

    ResponseEntity<ModeloResponseDTO> atualizar(Long id, ModeloRequestDTO dto);

    ResponseEntity<Void> excluir(Long id);
}
