package com.joao.RMAFlow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.Manutencao;
import com.joao.RMAFlow.repository.ManutencaoRepository;
import com.joao.RMAFlow.service.ManutencaoService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManutencaoServiceImpl implements ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;

    @Override
    public Manutencao salvar(Manutencao manutencao) {
        return manutencaoRepository.save(manutencao);
    }

    @Override
    public Manutencao buscarPorId(Long id) {
        return manutencaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutencao nao encontrada: " + id));
    }

    @Override
    public List<Manutencao> listarTodos() {
        return manutencaoRepository.findAll();
    }

    @Override
    public Manutencao atualizar(Long id, Manutencao manutencao) {
        Manutencao existente = buscarPorId(id);
        existente.setEquipamento(manutencao.getEquipamento());
        existente.setViavel(manutencao.isViavel());
        existente.setDataInicio(manutencao.getDataInicio());
        existente.setDataFim(manutencao.getDataFim());
        existente.setObservacoes(manutencao.getObservacoes());
        return manutencaoRepository.save(existente);
    }

    @Override
    public void excluir(Long id) {
        manutencaoRepository.deleteById(id);
    }
}
