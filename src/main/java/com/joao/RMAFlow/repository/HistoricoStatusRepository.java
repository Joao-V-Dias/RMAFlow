package com.joao.RMAFlow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joao.RMAFlow.model.HistoricoStatus;

public interface HistoricoStatusRepository extends JpaRepository<HistoricoStatus, Long> {

    List<HistoricoStatus> findByEquipamentoIdOrderByDataAlteracaoDesc(Long equipamentoId);
}
