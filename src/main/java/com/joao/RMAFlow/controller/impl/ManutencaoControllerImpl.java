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
import com.joao.RMAFlow.model.Manutencao;
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

    @Override
    @GetMapping
    public ResponseEntity<List<Manutencao>> listar() {
        return ResponseEntity.ok(manutencaoService.listarTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Manutencao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(manutencaoService.buscarPorId(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<Manutencao> criar(@Valid @RequestBody Manutencao manutencao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manutencaoService.salvar(manutencao));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Manutencao> atualizar(@PathVariable Long id, @Valid @RequestBody Manutencao manutencao) {
        return ResponseEntity.ok(manutencaoService.atualizar(id, manutencao));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        manutencaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
