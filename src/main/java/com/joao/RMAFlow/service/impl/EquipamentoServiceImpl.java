package com.joao.RMAFlow.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.HistoricoStatus;
import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.model.enums.TipoNotaFiscal;
import com.joao.RMAFlow.repository.EquipamentoRepository;
import com.joao.RMAFlow.repository.HistoricoStatusRepository;
import com.joao.RMAFlow.repository.ParceiroRepository;
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
    private final ParceiroRepository parceiroRepository;

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
                .dataAlteracao(LocalDateTime.now())
                .build();
        historicoStatusRepository.save(historico);

        return atualizado;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','ESTOQUE')")
    public Optional<Equipamento> buscarInformacoesPorNumeroSerie(String numeroSerie) {
        return equipamentoRepository.findByNumeroSerie(numeroSerie);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','ESTOQUE')")
    public Equipamento registrarDestinoObsoleto(Long equipamentoId, TipoNotaFiscal destino, Long parceiroDestinatarioId, Long usuarioId) {
        Equipamento equipamento = buscarPorId(equipamentoId);
        if (!equipamento.isObsoleto()) {
            throw new IllegalStateException("Equipamento nao esta marcado como obsoleto: " + equipamentoId);
        }
        parceiroRepository.findById(parceiroDestinatarioId)
                .orElseThrow(() -> new EntityNotFoundException("Parceiro nao encontrado: " + parceiroDestinatarioId));

        StatusEquipamento novoStatus = switch (destino) {
            case DESCARTE -> StatusEquipamento.DESCARTADO;
            case VENDA -> StatusEquipamento.VENDIDO;
            default -> throw new IllegalArgumentException("Destino invalido para equipamento obsoleto: " + destino);
        };

        return alterarStatus(equipamentoId, novoStatus, usuarioId);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','ESTOQUE')")
    public Equipamento disponibilizarEquipamento(Long equipamentoId, Long usuarioId) {
        Equipamento equipamento = buscarPorId(equipamentoId);
        if (equipamento.getStatus() != StatusEquipamento.DISPONIVEL) {
            throw new IllegalStateException("Equipamento nao esta disponivel para confirmacao de disponibilizacao: " + equipamentoId);
        }
        return alterarStatus(equipamentoId, StatusEquipamento.DISPONIVEL, usuarioId);
    }
}
