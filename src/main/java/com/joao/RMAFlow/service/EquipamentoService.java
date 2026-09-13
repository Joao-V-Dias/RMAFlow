package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.enums.StatusEquipamento;

public interface EquipamentoService {

    Equipamento salvar(Equipamento equipamento);

    Equipamento buscarPorId(Long id);

    Equipamento buscarPorNumeroSerie(String numeroSerie);

    List<Equipamento> buscarPorStatus(StatusEquipamento status);

    List<Equipamento> listarTodos();

    Equipamento atualizar(Long id, Equipamento equipamento);

    void excluir(Long id);

    /**
     * Atualiza o status do equipamento e registra a alteracao em HistoricoStatus.
     */
    Equipamento alterarStatus(Long equipamentoId, StatusEquipamento novoStatus, Long usuarioId);
}
