package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.Teste;

public interface TesteService {

    Teste salvar(Teste teste);

    Teste buscarPorId(Long id);

    List<Teste> listarTodos();

    Teste atualizar(Long id, Teste teste);

    void excluir(Long id);
}
