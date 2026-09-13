package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.request.NotaFiscalRequestDTO;
import com.joao.RMAFlow.dto.response.NotaFiscalResponseDTO;

public interface NotaFiscalController {

    ResponseEntity<List<NotaFiscalResponseDTO>> listar();

    ResponseEntity<NotaFiscalResponseDTO> buscarPorId(Long id);

    ResponseEntity<NotaFiscalResponseDTO> criar(NotaFiscalRequestDTO dto);

    ResponseEntity<NotaFiscalResponseDTO> atualizar(Long id, NotaFiscalRequestDTO dto);

    ResponseEntity<Void> excluir(Long id);
}
