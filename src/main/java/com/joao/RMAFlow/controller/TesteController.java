package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.request.TesteRequestDTO;
import com.joao.RMAFlow.dto.response.TesteResponseDTO;

public interface TesteController {

    ResponseEntity<List<TesteResponseDTO>> listar();

    ResponseEntity<TesteResponseDTO> buscarPorId(Long id);

    ResponseEntity<TesteResponseDTO> criar(TesteRequestDTO dto);

    ResponseEntity<TesteResponseDTO> atualizar(Long id, TesteRequestDTO dto);

    ResponseEntity<Void> excluir(Long id);
}
