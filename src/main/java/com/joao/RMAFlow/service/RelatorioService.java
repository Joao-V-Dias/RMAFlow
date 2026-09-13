package com.joao.RMAFlow.service;

import java.time.LocalDate;

import com.joao.RMAFlow.dto.response.RelatorioResponseDTO;

public interface RelatorioService {

    /**
     * Cobre "Gerar Relatorios" (RF9). Sempre calculado na hora a partir dos repositorios
     * existentes - Relatorio nao e uma entidade persistida.
     */
    RelatorioResponseDTO gerarRelatorioConsolidado(LocalDate periodoInicio, LocalDate periodoFim);
}
