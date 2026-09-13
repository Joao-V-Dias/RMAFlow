package com.joao.RMAFlow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.repository.UsuarioRepository;
import com.joao.RMAFlow.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado: " + id));
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado para o login: " + login));
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario existente = buscarPorId(id);
        existente.setNome(usuario.getNome());
        existente.setLogin(usuario.getLogin());
        existente.setSenha(usuario.getSenha());
        existente.setTelefone(usuario.getTelefone());
        existente.setEndereco(usuario.getEndereco());
        existente.setPerfil(usuario.getPerfil());
        return usuarioRepository.save(existente);
    }

    @Override
    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}
