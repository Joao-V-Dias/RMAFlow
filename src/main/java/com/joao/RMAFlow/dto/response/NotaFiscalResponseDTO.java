package com.joao.RMAFlow.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.joao.RMAFlow.model.enums.StatusNotaFiscal;
import com.joao.RMAFlow.model.enums.TipoNotaFiscal;

public record NotaFiscalResponseDTO(
        Long id,
        EquipamentoResumoDTO equipamento,
        ParceiroResumoDTO parceiro,
        TipoNotaFiscal tipo,
        BigDecimal valor,
        LocalDateTime dataEmissao,
        StatusNotaFiscal status
) {
}
