package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.Manutencao;

public interface ManutencaoService {

    Manutencao salvar(Manutencao manutencao);

    Manutencao buscarPorId(Long id);

    List<Manutencao> listarTodos();

    Manutencao atualizar(Long id, Manutencao manutencao);

    void excluir(Long id);
}
