package com.joao.RMAFlow.service;

import java.math.BigDecimal;
import java.util.List;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.NotaFiscal;
import com.joao.RMAFlow.model.enums.TipoNotaFiscal;

public interface NotaFiscalService {

    NotaFiscal salvar(NotaFiscal notaFiscal);

    NotaFiscal buscarPorId(Long id);

    List<NotaFiscal> listarTodos();

    NotaFiscal atualizar(Long id, NotaFiscal notaFiscal);

    void excluir(Long id);

    /**
     * Alimenta a tela "Solicitar Emissao de Nota Fiscal" (RF8): todo Equipamento com status final
     * (VENDIDO, DESCARTADO ou SUCATA) que ainda nao tem nenhuma NotaFiscal vinculada.
     */
    List<Equipamento> listarEquipamentosPendentesDeNotaFiscal();

    /**
     * Cria a NotaFiscal com status = PENDENTE (RF8, primeira parte do &lt;&lt;include&gt;&gt;
     * Emitir nota fiscal).
     */
    NotaFiscal solicitarEmissao(Long equipamentoId, TipoNotaFiscal tipo, Long parceiroDestinatarioId, BigDecimal valor);

    /**
     * Muda o status para EMITIDA e preenche dataEmissao (RF10, primeira parte).
     */
    NotaFiscal emitir(Long notaFiscalId);

    /**
     * Cobre o &lt;&lt;extend&gt;&gt; "Enviar por e-mail" (RF10, segunda parte). Delega para o
     * EmailService; sucesso leva o status a ENVIADA, falha a ERRO_ENVIO (permitindo reenvio manual
     * chamando o metodo de novo).
     */
    NotaFiscal enviarPorEmail(Long notaFiscalId);
}
