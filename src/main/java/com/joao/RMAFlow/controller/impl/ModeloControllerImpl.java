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

import com.joao.RMAFlow.controller.ModeloController;
import com.joao.RMAFlow.dto.request.ModeloRequestDTO;
import com.joao.RMAFlow.dto.response.ModeloResponseDTO;
import com.joao.RMAFlow.mapper.ModeloMapper;
import com.joao.RMAFlow.model.Modelo;
import com.joao.RMAFlow.service.ModeloService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): a tabela de perfis do RMAFLOW_AUTH.md nao define restricao especifica
 * para o cadastro de Modelo; por ora, consulta e escrita ficam liberadas a qualquer usuario
 * autenticado, revisar quando a regra de negocio correspondente for definida.
 */
@RestController
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
public class ModeloControllerImpl implements ModeloController {

    private final ModeloService modeloService;

    @Override
    @GetMapping
    public ResponseEntity<List<ModeloResponseDTO>> listar() {
        List<ModeloResponseDTO> modelos = modeloService.listarTodos().stream()
                .map(ModeloMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(modelos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ModeloResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ModeloMapper.toResponseDTO(modeloService.buscarPorId(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<ModeloResponseDTO> criar(@Valid @RequestBody ModeloRequestDTO dto) {
        Modelo salvo = modeloService.salvar(ModeloMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ModeloMapper.toResponseDTO(salvo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ModeloRequestDTO dto) {
        Modelo atualizado = modeloService.atualizar(id, ModeloMapper.toEntity(dto));
        return ResponseEntity.ok(ModeloMapper.toResponseDTO(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        modeloService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
