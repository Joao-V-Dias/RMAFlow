package com.joao.RMAFlow.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.NotaFiscal;
import com.joao.RMAFlow.model.Parceiro;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.model.enums.StatusNotaFiscal;
import com.joao.RMAFlow.model.enums.TipoNotaFiscal;
import com.joao.RMAFlow.repository.EquipamentoRepository;
import com.joao.RMAFlow.repository.NotaFiscalRepository;
import com.joao.RMAFlow.repository.ParceiroRepository;
import com.joao.RMAFlow.service.EmailService;
import com.joao.RMAFlow.service.NotaFiscalService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaFiscalServiceImpl implements NotaFiscalService {

    private static final List<StatusEquipamento> STATUS_FINAIS =
            List.of(StatusEquipamento.VENDIDO, StatusEquipamento.DESCARTADO, StatusEquipamento.SUCATA);

    private final NotaFiscalRepository notaFiscalRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final ParceiroRepository parceiroRepository;
    private final EmailService emailService;

    @Override
    public NotaFiscal salvar(NotaFiscal notaFiscal) {
        return notaFiscalRepository.save(notaFiscal);
    }

    @Override
    public NotaFiscal buscarPorId(Long id) {
        return notaFiscalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota fiscal nao encontrada: " + id));
    }

    @Override
    public List<NotaFiscal> listarTodos() {
        return notaFiscalRepository.findAll();
    }

    @Override
    public NotaFiscal atualizar(Long id, NotaFiscal notaFiscal) {
        NotaFiscal existente = buscarPorId(id);
        existente.setEquipamento(notaFiscal.getEquipamento());
        existente.setParceiro(notaFiscal.getParceiro());
        existente.setTipo(notaFiscal.getTipo());
        existente.setValor(notaFiscal.getValor());
        existente.setDataEmissao(notaFiscal.getDataEmissao());
        existente.setStatus(notaFiscal.getStatus());
        return notaFiscalRepository.save(existente);
    }

    @Override
    public void excluir(Long id) {
        notaFiscalRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO')")
    public List<Equipamento> listarEquipamentosPendentesDeNotaFiscal() {
        return equipamentoRepository.findByStatusInAndNotaFiscalIsNull(STATUS_FINAIS);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO')")
    public NotaFiscal solicitarEmissao(Long equipamentoId, TipoNotaFiscal tipo, Long parceiroDestinatarioId, BigDecimal valor) {
        Equipamento equipamento = equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento nao encontrado: " + equipamentoId));
        Parceiro parceiro = parceiroRepository.findById(parceiroDestinatarioId)
                .orElseThrow(() -> new EntityNotFoundException("Parceiro nao encontrado: " + parceiroDestinatarioId));

        NotaFiscal notaFiscal = NotaFiscal.builder()
                .equipamento(equipamento)
                .parceiro(parceiro)
                .tipo(tipo)
                .valor(valor)
                .status(StatusNotaFiscal.PENDENTE)
                .build();

        return notaFiscalRepository.save(notaFiscal);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO')")
    public NotaFiscal emitir(Long notaFiscalId) {
        NotaFiscal notaFiscal = buscarPorId(notaFiscalId);
        notaFiscal.setStatus(StatusNotaFiscal.EMITIDA);
        notaFiscal.setDataEmissao(LocalDateTime.now());
        return notaFiscalRepository.save(notaFiscal);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO')")
    public NotaFiscal enviarPorEmail(Long notaFiscalId) {
        NotaFiscal notaFiscal = buscarPorId(notaFiscalId);
        boolean sucesso = emailService.enviarNotaFiscal(notaFiscal, notaFiscal.getParceiro().getEmail());
        notaFiscal.setStatus(sucesso ? StatusNotaFiscal.ENVIADA : StatusNotaFiscal.ERRO_ENVIO);
        return notaFiscalRepository.save(notaFiscal);
    }
}
