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

import com.joao.RMAFlow.controller.NotaFiscalController;
import com.joao.RMAFlow.model.NotaFiscal;
import com.joao.RMAFlow.service.NotaFiscalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): quando a logica de negocio for alem do CRUD basico, restringir
 * a solicitacao/emissao de nota fiscal (criar/atualizar) ao perfil ADMIN, FINANCEIRO.
 */
@RestController
@RequestMapping("/api/notas-fiscais")
@RequiredArgsConstructor
public class NotaFiscalControllerImpl implements NotaFiscalController {

    private final NotaFiscalService notaFiscalService;

    @Override
    @GetMapping
    public ResponseEntity<List<NotaFiscal>> listar() {
        return ResponseEntity.ok(notaFiscalService.listarTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscal> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notaFiscalService.buscarPorId(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<NotaFiscal> criar(@Valid @RequestBody NotaFiscal notaFiscal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notaFiscalService.salvar(notaFiscal));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<NotaFiscal> atualizar(@PathVariable Long id, @Valid @RequestBody NotaFiscal notaFiscal) {
        return ResponseEntity.ok(notaFiscalService.atualizar(id, notaFiscal));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        notaFiscalService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
