package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.model.NotaFiscal;

public interface NotaFiscalController {

    ResponseEntity<List<NotaFiscal>> listar();

    ResponseEntity<NotaFiscal> buscarPorId(Long id);

    ResponseEntity<NotaFiscal> criar(NotaFiscal notaFiscal);

    ResponseEntity<NotaFiscal> atualizar(Long id, NotaFiscal notaFiscal);

    ResponseEntity<Void> excluir(Long id);
}
