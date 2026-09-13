package com.joao.RMAFlow.dto.request;

import java.math.BigDecimal;

import com.joao.RMAFlow.model.enums.TipoNotaFiscal;

import jakarta.validation.constraints.NotNull;

public record NotaFiscalRequestDTO(
        @NotNull Long equipamentoId,
        @NotNull Long parceiroId,
        @NotNull TipoNotaFiscal tipo,
        BigDecimal valor
) {
}
