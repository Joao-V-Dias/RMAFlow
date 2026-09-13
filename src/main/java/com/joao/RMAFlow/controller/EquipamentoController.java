package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.request.EquipamentoRequestDTO;
import com.joao.RMAFlow.dto.response.EquipamentoResponseDTO;

public interface EquipamentoController {

    ResponseEntity<List<EquipamentoResponseDTO>> listar();

    ResponseEntity<EquipamentoResponseDTO> buscarPorId(Long id);

    ResponseEntity<EquipamentoResponseDTO> criar(EquipamentoRequestDTO dto);

    ResponseEntity<EquipamentoResponseDTO> atualizar(Long id, EquipamentoRequestDTO dto);

    ResponseEntity<Void> excluir(Long id);
}
