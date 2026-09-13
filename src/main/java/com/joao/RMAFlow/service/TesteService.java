package com.joao.RMAFlow.service;

import java.util.List;

import com.joao.RMAFlow.model.Teste;

public interface TesteService {

    Teste salvar(Teste teste);

    Teste buscarPorId(Long id);

    List<Teste> listarTodos();

    Teste atualizar(Long id, Teste teste);

    void excluir(Long id);

    /**
     * Salva o resultado do teste e, com base nele, direciona o status do Equipamento associado
     * (RF3-RF6), via EquipamentoService.alterarStatus: APROVADO -> DISPONIVEL; REPROVADO com
     * manutencaoViavel = true -> EM_MANUTENCAO; REPROVADO com manutencaoViavel = false (ou null)
     * -> SUCATA. manutencaoViavel e ignorado quando o resultado e APROVADO.
     */
    Teste registrarResultado(Teste teste, Boolean manutencaoViavel);
}
