package com.joao.RMAFlow.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.response.RelatorioResponseDTO;

public interface RelatorioController {

    ResponseEntity<RelatorioResponseDTO> gerarRelatorioConsolidado(LocalDate inicio, LocalDate fim);
}
