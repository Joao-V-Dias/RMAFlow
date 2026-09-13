package com.joao.RMAFlow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.NotaFiscal;
import com.joao.RMAFlow.repository.NotaFiscalRepository;
import com.joao.RMAFlow.service.NotaFiscalService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaFiscalServiceImpl implements NotaFiscalService {

    private final NotaFiscalRepository notaFiscalRepository;

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
}
