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
import com.joao.RMAFlow.model.Parceiro;
import com.joao.RMAFlow.service.ParceiroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parceiros")
@RequiredArgsConstructor
public class ParceiroControllerImpl implements ParceiroController {

    private final ParceiroService parceiroService;

    @Override
    @GetMapping
    public ResponseEntity<List<Parceiro>> listar() {
        return ResponseEntity.ok(parceiroService.listarTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Parceiro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(parceiroService.buscarPorId(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<Parceiro> criar(@Valid @RequestBody Parceiro parceiro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parceiroService.salvar(parceiro));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Parceiro> atualizar(@PathVariable Long id, @Valid @RequestBody Parceiro parceiro) {
        return ResponseEntity.ok(parceiroService.atualizar(id, parceiro));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        parceiroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
