package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.model.Modelo;

public interface ModeloController {

    ResponseEntity<List<Modelo>> listar();

    ResponseEntity<Modelo> buscarPorId(Long id);

    ResponseEntity<Modelo> criar(Modelo modelo);

    ResponseEntity<Modelo> atualizar(Long id, Modelo modelo);

    ResponseEntity<Void> excluir(Long id);
}
