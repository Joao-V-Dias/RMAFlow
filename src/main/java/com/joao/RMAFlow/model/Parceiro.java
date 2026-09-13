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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "parceiro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String cpfCnpj;

    @NotBlank
    @Column(nullable = false)
    private String endereco;

    private String telefone;

    @Email
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "parceiro")
    @Builder.Default
    private List<Equipamento> equipamentos = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "parceiro")
    @Builder.Default
    private List<NotaFiscal> notasFiscais = new ArrayList<>();
}
