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

import com.joao.RMAFlow.controller.NotaFiscalController;
import com.joao.RMAFlow.dto.request.NotaFiscalRequestDTO;
import com.joao.RMAFlow.dto.response.NotaFiscalResponseDTO;
import com.joao.RMAFlow.mapper.NotaFiscalMapper;
import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.NotaFiscal;
import com.joao.RMAFlow.model.Parceiro;
import com.joao.RMAFlow.service.EquipamentoService;
import com.joao.RMAFlow.service.NotaFiscalService;
import com.joao.RMAFlow.service.ParceiroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TODO (RMAFLOW_AUTH): quando a logica de negocio for alem do CRUD basico, restringir
 * a solicitacao/emissao de nota fiscal (criar/atualizar) ao perfil ADMIN, FINANCEIRO.
 */
@RestController
@RequestMapping("/api/notas-fiscais")
@RequiredArgsConstructor
public class NotaFiscalControllerImpl implements NotaFiscalController {

    private final NotaFiscalService notaFiscalService;
    private final EquipamentoService equipamentoService;
    private final ParceiroService parceiroService;

    @Override
    @GetMapping
    public ResponseEntity<List<NotaFiscalResponseDTO>> listar() {
        List<NotaFiscalResponseDTO> notasFiscais = notaFiscalService.listarTodos().stream()
                .map(NotaFiscalMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(notasFiscais);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(NotaFiscalMapper.toResponseDTO(notaFiscalService.buscarPorId(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<NotaFiscalResponseDTO> criar(@Valid @RequestBody NotaFiscalRequestDTO dto) {
        Equipamento equipamento = equipamentoService.buscarPorId(dto.equipamentoId());
        Parceiro parceiro = parceiroService.buscarPorId(dto.parceiroId());
        NotaFiscal salvo = notaFiscalService.salvar(NotaFiscalMapper.toEntity(dto, equipamento, parceiro));
        return ResponseEntity.status(HttpStatus.CREATED).body(NotaFiscalMapper.toResponseDTO(salvo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<NotaFiscalResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody NotaFiscalRequestDTO dto) {
        // dataEmissao/status nao fazem parte do request (geridos pelo fluxo de emissao) - preserva
        // os valores atuais para nao serem sobrescritos pelo default da entidade recem-montada.
        NotaFiscal existente = notaFiscalService.buscarPorId(id);
        Equipamento equipamento = equipamentoService.buscarPorId(dto.equipamentoId());
        Parceiro parceiro = parceiroService.buscarPorId(dto.parceiroId());

        NotaFiscal notaFiscal = NotaFiscalMapper.toEntity(dto, equipamento, parceiro);
        notaFiscal.setDataEmissao(existente.getDataEmissao());
        notaFiscal.setStatus(existente.getStatus());

        NotaFiscal atualizado = notaFiscalService.atualizar(id, notaFiscal);
        return ResponseEntity.ok(NotaFiscalMapper.toResponseDTO(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        notaFiscalService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
