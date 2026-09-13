package com.joao.RMAFlow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.HistoricoStatus;
import com.joao.RMAFlow.repository.HistoricoStatusRepository;
import com.joao.RMAFlow.service.HistoricoStatusService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoStatusServiceImpl implements HistoricoStatusService {

    private final HistoricoStatusRepository historicoStatusRepository;

    @Override
    public HistoricoStatus salvar(HistoricoStatus historicoStatus) {
        return historicoStatusRepository.save(historicoStatus);
    }

    @Override
    public HistoricoStatus buscarPorId(Long id) {
        return historicoStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historico de status nao encontrado: " + id));
    }

    @Override
    public List<HistoricoStatus> listarTodos() {
        return historicoStatusRepository.findAll();
    }

    @Override
    public List<HistoricoStatus> listarPorEquipamento(Long equipamentoId) {
        return historicoStatusRepository.findByEquipamentoIdOrderByDataAlteracaoDesc(equipamentoId);
    }

    @Override
    public HistoricoStatus atualizar(Long id, HistoricoStatus historicoStatus) {
        HistoricoStatus existente = buscarPorId(id);
        existente.setEquipamento(historicoStatus.getEquipamento());
        existente.setUsuario(historicoStatus.getUsuario());
        existente.setStatusAnterior(historicoStatus.getStatusAnterior());
        existente.setStatusNovo(historicoStatus.getStatusNovo());
        return historicoStatusRepository.save(existente);
    }

    @Override
    public void excluir(Long id) {
        historicoStatusRepository.deleteById(id);
    }
}
