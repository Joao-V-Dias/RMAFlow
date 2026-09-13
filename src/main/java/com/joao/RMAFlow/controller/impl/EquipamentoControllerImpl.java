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

import com.joao.RMAFlow.controller.EquipamentoController;
import com.joao.RMAFlow.dto.request.EquipamentoRequestDTO;
import com.joao.RMAFlow.dto.response.EquipamentoResponseDTO;
import com.joao.RMAFlow.mapper.EquipamentoMapper;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.Modelo;
import com.joao.RMAFlow.model.Parceiro;
import com.joao.RMAFlow.service.EquipamentoService;
import com.joao.RMAFlow.service.ModeloService;
import com.joao.RMAFlow.service.ParceiroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): quando a logica de negocio for alem do CRUD basico, restringir por perfil:
 * cadastrar entrada / registrar destino obsoleto / disponibilizar equipamento -> ADMIN, ESTOQUE.
 * Consulta (listar/buscarPorId) -> qualquer usuario autenticado (sem restricao adicional).
 */
@RestController
@RequestMapping("/api/equipamentos")
@RequiredArgsConstructor
public class EquipamentoControllerImpl implements EquipamentoController {

    private final EquipamentoService equipamentoService;
    private final ModeloService modeloService;
    private final ParceiroService parceiroService;

    @Override
    @GetMapping
    public ResponseEntity<List<EquipamentoResponseDTO>> listar() {
        List<EquipamentoResponseDTO> equipamentos = equipamentoService.listarTodos().stream()
                .map(EquipamentoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(equipamentos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(EquipamentoMapper.toResponseDTO(equipamentoService.buscarPorId(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<EquipamentoResponseDTO> criar(@Valid @RequestBody EquipamentoRequestDTO dto) {
        Modelo modelo = modeloService.buscarPorId(dto.modeloId());
        Parceiro parceiro = parceiroService.buscarPorId(dto.parceiroId());
        Equipamento salvo = equipamentoService.salvar(EquipamentoMapper.toEntity(dto, modelo, parceiro));
        return ResponseEntity.status(HttpStatus.CREATED).body(EquipamentoMapper.toResponseDTO(salvo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EquipamentoRequestDTO dto) {
        // status/obsoleto nao fazem parte do request (geridos pelo fluxo de negocio via
        // EquipamentoService.alterarStatus) - preserva os valores atuais para nao serem
        // sobrescritos pelo default da entidade recem-montada pelo mapper.
        Equipamento existente = equipamentoService.buscarPorId(id);
        Modelo modelo = modeloService.buscarPorId(dto.modeloId());
        Parceiro parceiro = parceiroService.buscarPorId(dto.parceiroId());

        Equipamento equipamento = EquipamentoMapper.toEntity(dto, modelo, parceiro);
        equipamento.setStatus(existente.getStatus());
        equipamento.setObsoleto(existente.isObsoleto());

        Equipamento atualizado = equipamentoService.atualizar(id, equipamento);
        return ResponseEntity.ok(EquipamentoMapper.toResponseDTO(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        equipamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
