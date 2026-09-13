package com.joao.RMAFlow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.HistoricoStatus;
import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.repository.EquipamentoRepository;
import com.joao.RMAFlow.repository.HistoricoStatusRepository;
import com.joao.RMAFlow.repository.UsuarioRepository;
import com.joao.RMAFlow.service.EquipamentoService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipamentoServiceImpl implements EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistoricoStatusRepository historicoStatusRepository;

    @Override
    public Equipamento salvar(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    @Override
    public Equipamento buscarPorId(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento nao encontrado: " + id));
    }

    @Override
    public Equipamento buscarPorNumeroSerie(String numeroSerie) {
        return equipamentoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento nao encontrado para o numero de serie: " + numeroSerie));
    }

    @Override
    public List<Equipamento> buscarPorStatus(StatusEquipamento status) {
        return equipamentoRepository.findByStatus(status);
    }

    @Override
    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll();
    }

    @Override
    public Equipamento atualizar(Long id, Equipamento equipamento) {
        Equipamento existente = buscarPorId(id);
        existente.setNumeroSerie(equipamento.getNumeroSerie());
        existente.setModelo(equipamento.getModelo());
        existente.setParceiro(equipamento.getParceiro());
        existente.setStatus(equipamento.getStatus());
        existente.setObsoleto(equipamento.isObsoleto());
        return equipamentoRepository.save(existente);
    }

    @Override
    public void excluir(Long id) {
        equipamentoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Equipamento alterarStatus(Long equipamentoId, StatusEquipamento novoStatus, Long usuarioId) {
        Equipamento equipamento = buscarPorId(equipamentoId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado: " + usuarioId));

        StatusEquipamento statusAnterior = equipamento.getStatus();
        equipamento.setStatus(novoStatus);
        Equipamento atualizado = equipamentoRepository.save(equipamento);

        HistoricoStatus historico = HistoricoStatus.builder()
                .equipamento(atualizado)
                .usuario(usuario)
                .statusAnterior(statusAnterior)
                .statusNovo(novoStatus)
                .build();
        historicoStatusRepository.save(historico);

        return atualizado;
    }
}
