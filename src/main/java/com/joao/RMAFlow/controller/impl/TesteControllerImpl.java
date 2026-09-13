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

import com.joao.RMAFlow.controller.TesteController;
import com.joao.RMAFlow.model.Teste;
import com.joao.RMAFlow.service.TesteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): quando a logica de negocio for alem do CRUD basico, restringir
 * o registro de teste (criar/atualizar) ao perfil ADMIN, RMA.
 */
@RestController
@RequestMapping("/api/testes")
@RequiredArgsConstructor
public class TesteControllerImpl implements TesteController {

    private final TesteService testeService;

    @Override
    @GetMapping
    public ResponseEntity<List<Teste>> listar() {
        return ResponseEntity.ok(testeService.listarTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Teste> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(testeService.buscarPorId(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<Teste> criar(@Valid @RequestBody Teste teste) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testeService.salvar(teste));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Teste> atualizar(@PathVariable Long id, @Valid @RequestBody Teste teste) {
        return ResponseEntity.ok(testeService.atualizar(id, teste));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        testeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
