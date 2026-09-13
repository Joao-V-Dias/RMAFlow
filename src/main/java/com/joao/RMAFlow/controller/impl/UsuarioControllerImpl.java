package com.joao.RMAFlow.controller.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joao.RMAFlow.controller.UsuarioController;
import com.joao.RMAFlow.dto.request.UsuarioRequestDTO;
import com.joao.RMAFlow.dto.response.UsuarioResponseDTO;
import com.joao.RMAFlow.mapper.UsuarioMapper;
import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

    private final UsuarioService usuarioService;

    @Override
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos().stream()
                .map(UsuarioMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(UsuarioMapper.toResponseDTO(usuarioService.buscarPorId(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario salvo = usuarioService.salvar(UsuarioMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toResponseDTO(salvo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario atualizado = usuarioService.atualizar(id, UsuarioMapper.toEntity(dto));
        return ResponseEntity.ok(UsuarioMapper.toResponseDTO(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<UsuarioResponseDTO> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(UsuarioMapper.toResponseDTO(usuarioService.inativar(id)));
    }
}
