package com.joao.RMAFlow.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "modelo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String fabricante;

    @NotBlank
    @Column(nullable = false)
    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String especificacoes;

    @ToString.Exclude
    @OneToMany(mappedBy = "modelo")
    @Builder.Default
    private List<Equipamento> equipamentos = new ArrayList<>();
}
