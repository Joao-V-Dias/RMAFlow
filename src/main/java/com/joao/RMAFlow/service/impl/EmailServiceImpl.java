package com.joao.RMAFlow.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.joao.RMAFlow.model.NotaFiscal;
import com.joao.RMAFlow.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    @Value("${rmaflow.email.remetente}")
    private String remetente;

    @Override
    public boolean enviarNotaFiscal(NotaFiscal notaFiscal, String destinatario) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject("Nota fiscal de " + notaFiscal.getTipo() + " - Equipamento " + notaFiscal.getEquipamento().getNumeroSerie());
        mensagem.setText("""
                Nota fiscal referente ao equipamento de numero de serie %s.

                Tipo: %s
                Destinatario: %s
                """.formatted(notaFiscal.getEquipamento().getNumeroSerie(), notaFiscal.getTipo(), destinatario));

        try {
            mailSender.send(mensagem);
            return true;
        } catch (MailException e) {
            log.error("Falha ao enviar e-mail da nota fiscal {}: {}", notaFiscal.getId(), e.getMessage());
            return false;
        }
    }
}
