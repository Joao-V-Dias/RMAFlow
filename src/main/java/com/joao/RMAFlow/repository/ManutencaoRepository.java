package com.joao.RMAFlow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joao.RMAFlow.model.Manutencao;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
}
