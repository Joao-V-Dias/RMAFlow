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

import com.joao.RMAFlow.controller.ParceiroController;
import com.joao.RMAFlow.dto.request.ParceiroRequestDTO;
import com.joao.RMAFlow.dto.response.ParceiroResponseDTO;
import com.joao.RMAFlow.mapper.ParceiroMapper;
import com.joao.RMAFlow.model.Parceiro;
import com.joao.RMAFlow.service.ParceiroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): a tabela de perfis do RMAFLOW_AUTH.md nao define restricao especifica
 * para o cadastro de Parceiro; por ora, consulta e escrita ficam liberadas a qualquer usuario
 * autenticado, revisar quando a regra de negocio correspondente for definida.
 */
@RestController
@RequestMapping("/api/parceiros")
@RequiredArgsConstructor
public class ParceiroControllerImpl implements ParceiroController {

    private final ParceiroService parceiroService;

    @Override
    @GetMapping
    public ResponseEntity<List<ParceiroResponseDTO>> listar() {
        List<ParceiroResponseDTO> parceiros = parceiroService.listarTodos().stream()
                .map(ParceiroMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(parceiros);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ParceiroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ParceiroMapper.toResponseDTO(parceiroService.buscarPorId(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<ParceiroResponseDTO> criar(@Valid @RequestBody ParceiroRequestDTO dto) {
        Parceiro salvo = parceiroService.salvar(ParceiroMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ParceiroMapper.toResponseDTO(salvo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ParceiroResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ParceiroRequestDTO dto) {
        Parceiro atualizado = parceiroService.atualizar(id, ParceiroMapper.toEntity(dto));
        return ResponseEntity.ok(ParceiroMapper.toResponseDTO(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        parceiroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
