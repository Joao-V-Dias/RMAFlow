package com.joao.RMAFlow.service;

import com.joao.RMAFlow.model.NotaFiscal;

public interface EmailService {

    /**
     * Envia um e-mail de texto simples confirmando a nota fiscal (sem anexo em PDF). Retorna
     * true/false para o NotaFiscalService decidir entre status ENVIADA/ERRO_ENVIO.
     */
    boolean enviarNotaFiscal(NotaFiscal notaFiscal, String destinatario);
}
