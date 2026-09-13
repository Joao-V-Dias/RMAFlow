package com.joao.RMAFlow.controller.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joao.RMAFlow.controller.TesteController;
import com.joao.RMAFlow.dto.request.TesteRequestDTO;
import com.joao.RMAFlow.dto.response.TesteResponseDTO;
import com.joao.RMAFlow.mapper.TesteMapper;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.Teste;
import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.service.EquipamentoService;
import com.joao.RMAFlow.service.TesteService;
import com.joao.RMAFlow.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): quando a logica de negocio for alem do CRUD basico, restringir
 * o registro de teste (criar/atualizar) ao perfil ADMIN, RMA.
 */
@RestController
@RequestMapping("/api/testes")
@RequiredArgsConstructor
public class TesteControllerImpl implements TesteController {

    private final TesteService testeService;
    private final EquipamentoService equipamentoService;
    private final UsuarioService usuarioService;

    @Override
    @GetMapping
    public ResponseEntity<List<TesteResponseDTO>> listar() {
        List<TesteResponseDTO> testes = testeService.listarTodos().stream()
                .map(TesteMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(testes);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TesteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(TesteMapper.toResponseDTO(testeService.buscarPorId(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<TesteResponseDTO> criar(@Valid @RequestBody TesteRequestDTO dto) {
        Equipamento equipamento = equipamentoService.buscarPorId(dto.equipamentoId());
        Usuario responsavel = usuarioService.buscarPorId(dto.responsavelId());
        Teste salvo = testeService.salvar(TesteMapper.toEntity(dto, equipamento, responsavel));
        return ResponseEntity.status(HttpStatus.CREATED).body(TesteMapper.toResponseDTO(salvo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TesteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TesteRequestDTO dto) {
        Equipamento equipamento = equipamentoService.buscarPorId(dto.equipamentoId());
        Usuario responsavel = usuarioService.buscarPorId(dto.responsavelId());
        Teste atualizado = testeService.atualizar(id, TesteMapper.toEntity(dto, equipamento, responsavel));
        return ResponseEntity.ok(TesteMapper.toResponseDTO(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        testeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
