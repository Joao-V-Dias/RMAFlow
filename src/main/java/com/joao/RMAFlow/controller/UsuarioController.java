package com.joao.RMAFlow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.joao.RMAFlow.dto.request.UsuarioRequestDTO;
import com.joao.RMAFlow.dto.response.UsuarioResponseDTO;

public interface UsuarioController {

    ResponseEntity<List<UsuarioResponseDTO>> listar();

    ResponseEntity<UsuarioResponseDTO> buscarPorId(Long id);

    ResponseEntity<UsuarioResponseDTO> criar(UsuarioRequestDTO dto);

    ResponseEntity<UsuarioResponseDTO> atualizar(Long id, UsuarioRequestDTO dto);

    ResponseEntity<Void> excluir(Long id);

    ResponseEntity<UsuarioResponseDTO> inativar(Long id);
}
