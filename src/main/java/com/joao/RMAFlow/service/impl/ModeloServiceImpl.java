package com.joao.RMAFlow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.Modelo;
import com.joao.RMAFlow.repository.ModeloRepository;
import com.joao.RMAFlow.service.ModeloService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModeloServiceImpl implements ModeloService {

    private final ModeloRepository modeloRepository;

    @Override
    public Modelo salvar(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    @Override
    public Modelo buscarPorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Modelo nao encontrado: " + id));
    }

    @Override
    public List<Modelo> listarTodos() {
        return modeloRepository.findAll();
    }

    @Override
    public Modelo atualizar(Long id, Modelo modelo) {
        Modelo existente = buscarPorId(id);
        existente.setNome(modelo.getNome());
        existente.setFabricante(modelo.getFabricante());
        existente.setTipo(modelo.getTipo());
        existente.setEspecificacoes(modelo.getEspecificacoes());
        return modeloRepository.save(existente);
    }

    @Override
    public void excluir(Long id) {
        modeloRepository.deleteById(id);
    }
}
