package com.joao.RMAFlow.controller.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joao.RMAFlow.controller.ManutencaoController;
import com.joao.RMAFlow.dto.request.ManutencaoRequestDTO;
import com.joao.RMAFlow.dto.response.ManutencaoResponseDTO;
import com.joao.RMAFlow.mapper.ManutencaoMapper;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.Manutencao;
import com.joao.RMAFlow.service.EquipamentoService;
import com.joao.RMAFlow.service.ManutencaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): quando a logica de negocio for alem do CRUD basico, restringir
 * o encaminhamento para manutencao (criar/atualizar) ao perfil ADMIN, RMA.
 */
@RestController
@RequestMapping("/api/manutencoes")
@RequiredArgsConstructor
public class ManutencaoControllerImpl implements ManutencaoController {

    private final ManutencaoService manutencaoService;
    private final EquipamentoService equipamentoService;

    @Override
    @GetMapping
    public ResponseEntity<List<ManutencaoResponseDTO>> listar() {
        List<ManutencaoResponseDTO> manutencoes = manutencaoService.listarTodos().stream()
                .map(ManutencaoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(manutencoes);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ManutencaoMapper.toResponseDTO(manutencaoService.buscarPorId(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<ManutencaoResponseDTO> criar(@Valid @RequestBody ManutencaoRequestDTO dto) {
        Equipamento equipamento = equipamentoService.buscarPorId(dto.equipamentoId());
        Manutencao salvo = manutencaoService.salvar(ManutencaoMapper.toEntity(dto, equipamento));
        return ResponseEntity.status(HttpStatus.CREATED).body(ManutencaoMapper.toResponseDTO(salvo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ManutencaoRequestDTO dto) {
        Equipamento equipamento = equipamentoService.buscarPorId(dto.equipamentoId());
        Manutencao atualizado = manutencaoService.atualizar(id, ManutencaoMapper.toEntity(dto, equipamento));
        return ResponseEntity.ok(ManutencaoMapper.toResponseDTO(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        manutencaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
