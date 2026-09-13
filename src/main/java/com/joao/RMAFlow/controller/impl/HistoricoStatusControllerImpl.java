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

import com.joao.RMAFlow.controller.HistoricoStatusController;
import com.joao.RMAFlow.model.HistoricoStatus;
import com.joao.RMAFlow.service.HistoricoStatusService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): trilha de auditoria gerada automaticamente por EquipamentoService.
 * alterarStatus(); os endpoints de escrita aqui sao apenas stub e devem ser revistos (ou
 * removidos) quando essa regra for finalizada. Consulta liberada a qualquer autenticado.
 */
@RestController
@RequestMapping("/api/historico-status")
@RequiredArgsConstructor
public class HistoricoStatusControllerImpl implements HistoricoStatusController {

    private final HistoricoStatusService historicoStatusService;

    @Override
    @GetMapping
    public ResponseEntity<List<HistoricoStatus>> listar() {
        return ResponseEntity.ok(historicoStatusService.listarTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoStatus> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historicoStatusService.buscarPorId(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<HistoricoStatus> criar(@Valid @RequestBody HistoricoStatus historicoStatus) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historicoStatusService.salvar(historicoStatus));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoStatus> atualizar(@PathVariable Long id, @Valid @RequestBody HistoricoStatus historicoStatus) {
        return ResponseEntity.ok(historicoStatusService.atualizar(id, historicoStatus));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        historicoStatusService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
