package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.request.ParceiroRequestDTO;
import com.joao.RMAFlow.dto.response.ParceiroResponseDTO;

public interface ParceiroController {

    ResponseEntity<List<ParceiroResponseDTO>> listar();

    ResponseEntity<ParceiroResponseDTO> buscarPorId(Long id);

    ResponseEntity<ParceiroResponseDTO> criar(ParceiroRequestDTO dto);

    ResponseEntity<ParceiroResponseDTO> atualizar(Long id, ParceiroRequestDTO dto);

    ResponseEntity<Void> excluir(Long id);
}
