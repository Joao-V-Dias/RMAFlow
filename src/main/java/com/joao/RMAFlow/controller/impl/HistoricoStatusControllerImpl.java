package com.joao.RMAFlow.controller.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joao.RMAFlow.controller.HistoricoStatusController;
import com.joao.RMAFlow.dto.response.HistoricoStatusResponseDTO;
import com.joao.RMAFlow.mapper.HistoricoStatusMapper;
import com.joao.RMAFlow.service.HistoricoStatusService;

import lombok.RequiredArgsConstructor;

/**
 * Trilha de auditoria gerada automaticamente por EquipamentoService.alterarStatus() - nao existe
 * Request DTO nem endpoint de criacao/edicao aqui, conforme RMAFLOW_DTOS.md. Apenas consulta e
 * exclusao (stub) ficam expostas.
 */
@RestController
@RequestMapping("/api/historico-status")
@RequiredArgsConstructor
public class HistoricoStatusControllerImpl implements HistoricoStatusController {

    private final HistoricoStatusService historicoStatusService;

    @Override
    @GetMapping
    public ResponseEntity<List<HistoricoStatusResponseDTO>> listar() {
        List<HistoricoStatusResponseDTO> historico = historicoStatusService.listarTodos().stream()
                .map(HistoricoStatusMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(historico);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoStatusResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(HistoricoStatusMapper.toResponseDTO(historicoStatusService.buscarPorId(id)));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        historicoStatusService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
