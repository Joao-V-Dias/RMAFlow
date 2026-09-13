package com.joao.RMAFlow.service;

import java.util.List;
import java.util.Optional;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.model.enums.TipoNotaFiscal;

public interface EquipamentoService {

    Equipamento salvar(Equipamento equipamento);

    Equipamento buscarPorId(Long id);

    Equipamento buscarPorNumeroSerie(String numeroSerie);

    List<Equipamento> buscarPorStatus(StatusEquipamento status);

    List<Equipamento> listarTodos();

    Equipamento atualizar(Long id, Equipamento equipamento);

    void excluir(Long id);

    /**
     * Atualiza o status do equipamento e registra a alteracao em HistoricoStatus.
     */
    Equipamento alterarStatus(Long equipamentoId, StatusEquipamento novoStatus, Long usuarioId);

    /**
     * Cobre o &lt;&lt;include&gt;&gt; "Buscar informacoes do equipamento" do RF1. Busca interna
     * (o RMAFlow nao integra com sistemas de terceiros): se ja existir um Equipamento com esse
     * numeroSerie, retorna-o (contendo Modelo e Parceiro da ultima vez) para pre-preencher o
     * cadastro de entrada; vazio se nao encontrar, e o cadastro segue manual.
     */
    Optional<Equipamento> buscarInformacoesPorNumeroSerie(String numeroSerie);

    /**
     * Cobre "Registrar destino de equipamento obsoleto" (RF2). So pode ser chamado se
     * Equipamento.obsoleto == true. destino == DESCARTE leva o status a DESCARTADO; destino ==
     * VENDA leva o status a VENDIDO. Registra HistoricoStatus via alterarStatus.
     */
    Equipamento registrarDestinoObsoleto(Long equipamentoId, TipoNotaFiscal destino, Long parceiroDestinatarioId, Long usuarioId);

    /**
     * Cobre "Registrar disponibilizacao de equipamento" (RF7). So pode ser chamado com o
     * equipamento ja em DISPONIVEL (resultado de teste aprovado). Apenas confirma a
     * disponibilizacao e registra o HistoricoStatus correspondente.
     */
    Equipamento disponibilizarEquipamento(Long equipamentoId, Long usuarioId);
}
