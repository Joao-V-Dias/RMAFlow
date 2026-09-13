package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.Modelo;

public interface ModeloService {

    Modelo salvar(Modelo modelo);

    Modelo buscarPorId(Long id);

    List<Modelo> listarTodos();

    Modelo atualizar(Long id, Modelo modelo);

    void excluir(Long id);
}
