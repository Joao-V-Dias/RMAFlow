package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.NotaFiscal;

public interface NotaFiscalService {

    NotaFiscal salvar(NotaFiscal notaFiscal);

    NotaFiscal buscarPorId(Long id);

    List<NotaFiscal> listarTodos();

    NotaFiscal atualizar(Long id, NotaFiscal notaFiscal);

    void excluir(Long id);
}
