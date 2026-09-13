package com.joao.RMAFlow.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.joao.RMAFlow.model.enums.StatusNotaFiscal;
import com.joao.RMAFlow.model.enums.TipoNotaFiscal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nota_fiscal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id", nullable = false, unique = true)
    private Equipamento equipamento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parceiro_id", nullable = false)
    private Parceiro parceiro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotaFiscal tipo;

    @Column(precision = 12, scale = 2)
    private BigDecimal valor;

    private LocalDateTime dataEmissao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private StatusNotaFiscal status = StatusNotaFiscal.PENDENTE;
}
