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
import com.joao.RMAFlow.model.Modelo;
import com.joao.RMAFlow.service.ModeloService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
public class ModeloControllerImpl implements ModeloController {

    private final ModeloService modeloService;

    @Override
    @GetMapping
    public ResponseEntity<List<Modelo>> listar() {
        return ResponseEntity.ok(modeloService.listarTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Modelo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.buscarPorId(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<Modelo> criar(@Valid @RequestBody Modelo modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.salvar(modelo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Modelo> atualizar(@PathVariable Long id, @Valid @RequestBody Modelo modelo) {
        return ResponseEntity.ok(modeloService.atualizar(id, modelo));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        modeloService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
