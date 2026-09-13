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
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.service.EquipamentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/equipamentos")
@RequiredArgsConstructor
public class EquipamentoControllerImpl implements EquipamentoController {

    private final EquipamentoService equipamentoService;

    @Override
    @GetMapping
    public ResponseEntity<List<Equipamento>> listar() {
        return ResponseEntity.ok(equipamentoService.listarTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(equipamentoService.buscarPorId(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<Equipamento> criar(@Valid @RequestBody Equipamento equipamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipamentoService.salvar(equipamento));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> atualizar(@PathVariable Long id, @Valid @RequestBody Equipamento equipamento) {
        return ResponseEntity.ok(equipamentoService.atualizar(id, equipamento));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        equipamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
