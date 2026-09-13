package com.joao.RMAFlow.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.joao.RMAFlow.dto.response.RelatorioResponseDTO;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.repository.EquipamentoRepository;
import com.joao.RMAFlow.service.RelatorioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelatorioServiceImpl implements RelatorioService {

    private final EquipamentoRepository equipamentoRepository;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO')")
    public RelatorioResponseDTO gerarRelatorioConsolidado(LocalDate periodoInicio, LocalDate periodoFim) {
        LocalDateTime inicio = periodoInicio.atStartOfDay();
        LocalDateTime fim = periodoFim.atTime(LocalTime.MAX);

        List<Equipamento> equipamentos = equipamentoRepository.findByDataEntradaBetween(inicio, fim);

        Map<StatusEquipamento, Long> totalPorStatus = equipamentos.stream()
                .collect(Collectors.groupingBy(Equipamento::getStatus, Collectors.counting()));

        return new RelatorioResponseDTO(equipamentos.size(), totalPorStatus, LocalDateTime.now());
    }
}
