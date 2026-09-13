package com.joao.RMAFlow.model;

import java.time.LocalDateTime;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "historico_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private StatusEquipamento statusAnterior;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEquipamento statusNovo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataAlteracao;

    @PrePersist
    protected void prePersist() {
        if (dataAlteracao == null) {
            dataAlteracao = LocalDateTime.now();
        }
    }
}
