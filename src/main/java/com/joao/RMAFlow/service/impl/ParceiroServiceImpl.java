package com.joao.RMAFlow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.Parceiro;
import com.joao.RMAFlow.repository.ParceiroRepository;
import com.joao.RMAFlow.service.ParceiroService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParceiroServiceImpl implements ParceiroService {

    private final ParceiroRepository parceiroRepository;

    @Override
    public Parceiro salvar(Parceiro parceiro) {
        return parceiroRepository.save(parceiro);
    }

    @Override
    public Parceiro buscarPorId(Long id) {
        return parceiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parceiro nao encontrado: " + id));
    }

    @Override
    public Parceiro buscarPorCpfCnpj(String cpfCnpj) {
        return parceiroRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new EntityNotFoundException("Parceiro nao encontrado para o CPF/CNPJ: " + cpfCnpj));
    }

    @Override
    public List<Parceiro> listarTodos() {
        return parceiroRepository.findAll();
    }

    @Override
    public Parceiro atualizar(Long id, Parceiro parceiro) {
        Parceiro existente = buscarPorId(id);
        existente.setNome(parceiro.getNome());
        existente.setCpfCnpj(parceiro.getCpfCnpj());
        existente.setEndereco(parceiro.getEndereco());
        existente.setTelefone(parceiro.getTelefone());
        existente.setEmail(parceiro.getEmail());
        return parceiroRepository.save(existente);
    }

    @Override
    public void excluir(Long id) {
        parceiroRepository.deleteById(id);
    }
}
