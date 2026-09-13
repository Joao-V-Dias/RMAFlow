package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.HistoricoStatus;

public interface HistoricoStatusService {

    HistoricoStatus salvar(HistoricoStatus historicoStatus);

    HistoricoStatus buscarPorId(Long id);

    List<HistoricoStatus> listarTodos();

    List<HistoricoStatus> listarPorEquipamento(Long equipamentoId);

    HistoricoStatus atualizar(Long id, HistoricoStatus historicoStatus);

    void excluir(Long id);
}
