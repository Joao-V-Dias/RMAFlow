package com.joao.RMAFlow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.Teste;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.repository.TesteRepository;
import com.joao.RMAFlow.service.EquipamentoService;
import com.joao.RMAFlow.service.TesteService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TesteServiceImpl implements TesteService {

    private final TesteRepository testeRepository;
    private final EquipamentoService equipamentoService;

    @Override
    public Teste salvar(Teste teste) {
        return testeRepository.save(teste);
    }

    @Override
    public Teste buscarPorId(Long id) {
        return testeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teste nao encontrado: " + id));
    }

    @Override
    public List<Teste> listarTodos() {
        return testeRepository.findAll();
    }

    @Override
    public Teste atualizar(Long id, Teste teste) {
        Teste existente = buscarPorId(id);
        existente.setEquipamento(teste.getEquipamento());
        existente.setResponsavel(teste.getResponsavel());
        existente.setDataTeste(teste.getDataTeste());
        existente.setResultado(teste.getResultado());
        existente.setObservacoes(teste.getObservacoes());
        return testeRepository.save(existente);
    }

    @Override
    public void excluir(Long id) {
        testeRepository.deleteById(id);
    }

    @Override
    public Teste registrarResultado(Teste teste, Boolean manutencaoViavel) {
        Teste salvo = testeRepository.save(teste);

        StatusEquipamento novoStatus = switch (salvo.getResultado()) {
            case APROVADO -> StatusEquipamento.DISPONIVEL;
            case REPROVADO -> Boolean.TRUE.equals(manutencaoViavel)
                    ? StatusEquipamento.EM_MANUTENCAO
                    : StatusEquipamento.SUCATA;
        };

        equipamentoService.alterarStatus(
                salvo.getEquipamento().getId(),
                novoStatus,
                salvo.getResponsavel().getId());

        return salvo;
    }
}
