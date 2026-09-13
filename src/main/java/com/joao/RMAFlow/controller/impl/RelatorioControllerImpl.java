package com.joao.RMAFlow.controller.impl;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joao.RMAFlow.controller.RelatorioController;
import com.joao.RMAFlow.dto.response.RelatorioResponseDTO;
import com.joao.RMAFlow.service.RelatorioService;

import lombok.RequiredArgsConstructor;

/**
 * Restricao de perfil (ADMIN, FINANCEIRO) aplicada em RelatorioServiceImpl via @PreAuthorize,
 * seguindo o mesmo padrao ja adotado em UsuarioServiceImpl (RMAFLOW_AUTH.md).
 */
@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioControllerImpl implements RelatorioController {

    private final RelatorioService relatorioService;

    @Override
    @GetMapping
    public ResponseEntity<RelatorioResponseDTO> gerarRelatorioConsolidado(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(relatorioService.gerarRelatorioConsolidado(inicio, fim));
    }
}
