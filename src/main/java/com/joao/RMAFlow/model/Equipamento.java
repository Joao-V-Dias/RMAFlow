package com.joao.RMAFlow.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.joao.RMAFlow.model.enums.StatusEquipamento;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "equipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String numeroSerie;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parceiro_id", nullable = false)
    private Parceiro parceiro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private StatusEquipamento status = StatusEquipamento.RECEBIDO;

    @Builder.Default
    @Column(nullable = false)
    private boolean obsoleto = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataEntrada;

    @ToString.Exclude
    @OneToMany(mappedBy = "equipamento")
    @Builder.Default
    private List<Teste> testes = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "equipamento")
    @Builder.Default
    private List<Manutencao> manutencoes = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "equipamento")
    @Builder.Default
    private List<HistoricoStatus> historicoStatus = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "equipamento")
    private NotaFiscal notaFiscal;

    @PrePersist
    protected void prePersist() {
        if (dataEntrada == null) {
            dataEntrada = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusEquipamento.RECEBIDO;
        }
    }
}
