package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.Parceiro;

public interface ParceiroService {

    Parceiro salvar(Parceiro parceiro);

    Parceiro buscarPorId(Long id);

    Parceiro buscarPorCpfCnpj(String cpfCnpj);

    List<Parceiro> listarTodos();

    Parceiro atualizar(Long id, Parceiro parceiro);

    void excluir(Long id);
}
