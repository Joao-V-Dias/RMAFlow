package com.joao.RMAFlow.mapper;

import com.joao.RMAFlow.dto.request.ModeloRequestDTO;
import com.joao.RMAFlow.dto.response.ModeloResponseDTO;
import com.joao.RMAFlow.dto.response.ModeloResumoDTO;
import com.joao.RMAFlow.model.Modelo;

public final class ModeloMapper {

    private ModeloMapper() {
    }

    public static Modelo toEntity(ModeloRequestDTO dto) {
        return Modelo.builder()
                .nome(dto.nome())
                .fabricante(dto.fabricante())
                .tipo(dto.tipo())
                .especificacoes(dto.especificacoes())
                .build();
    }

    public static ModeloResponseDTO toResponseDTO(Modelo modelo) {
        return new ModeloResponseDTO(
                modelo.getId(),
                modelo.getNome(),
                modelo.getFabricante(),
                modelo.getTipo(),
                modelo.getEspecificacoes()
        );
    }

    public static ModeloResumoDTO toResumoDTO(Modelo modelo) {
        return new ModeloResumoDTO(modelo.getId(), modelo.getNome(), modelo.getFabricante());
    }
}
