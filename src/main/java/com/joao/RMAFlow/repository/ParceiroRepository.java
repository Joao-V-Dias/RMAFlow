package com.joao.RMAFlow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joao.RMAFlow.model.Parceiro;

public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {

    Optional<Parceiro> findByCpfCnpj(String cpfCnpj);
}
