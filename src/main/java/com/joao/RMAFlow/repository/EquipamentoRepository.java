package com.joao.RMAFlow.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.enums.StatusEquipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    List<Equipamento> findByStatus(StatusEquipamento status);

    Optional<Equipamento> findByNumeroSerie(String numeroSerie);

    List<Equipamento> findByDataEntradaBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Equipamento> findByStatusInAndNotaFiscalIsNull(List<StatusEquipamento> status);
}
