package com.joao.RMAFlow.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.HistoricoStatus;
import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.repository.EquipamentoRepository;
import com.joao.RMAFlow.repository.HistoricoStatusRepository;
import com.joao.RMAFlow.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class EquipamentoServiceImplTest {

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private HistoricoStatusRepository historicoStatusRepository;

    @InjectMocks
    private EquipamentoServiceImpl equipamentoService;

    @Test
    void alterarStatusDeveAtualizarStatusECriarHistoricoStatus() {
        Long equipamentoId = 1L;
        Long usuarioId = 2L;

        Equipamento equipamento = Equipamento.builder()
                .id(equipamentoId)
                .numeroSerie("SN-001")
                .status(StatusEquipamento.RECEBIDO)
                .build();

        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nome("Responsavel")
                .login("responsavel")
                .perfil(PerfilUsuario.RMA)
                .build();

        when(equipamentoRepository.findById(equipamentoId)).thenReturn(Optional.of(equipamento));
        when(equipamentoRepository.save(any(Equipamento.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        Equipamento resultado = equipamentoService.alterarStatus(equipamentoId, StatusEquipamento.EM_TESTE, usuarioId);

        assertThat(resultado.getStatus()).isEqualTo(StatusEquipamento.EM_TESTE);

        ArgumentCaptor<HistoricoStatus> captor = ArgumentCaptor.forClass(HistoricoStatus.class);
        verify(historicoStatusRepository).save(captor.capture());

        HistoricoStatus historico = captor.getValue();
        assertThat(historico.getStatusAnterior()).isEqualTo(StatusEquipamento.RECEBIDO);
        assertThat(historico.getStatusNovo()).isEqualTo(StatusEquipamento.EM_TESTE);
        assertThat(historico.getUsuario()).isEqualTo(usuario);
        assertThat(historico.getDataAlteracao()).isNotNull();
    }
}
