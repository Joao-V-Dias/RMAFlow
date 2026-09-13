package com.joao.RMAFlow.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.Teste;
import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.model.enums.ResultadoTeste;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.repository.TesteRepository;
import com.joao.RMAFlow.service.EquipamentoService;

/**
 * RF3-RF6: o resultado do teste do RMA decide o proximo status do equipamento. A verificacao de
 * como o HistoricoStatus e criado ja esta coberta por EquipamentoServiceImplTest; aqui so
 * verificamos qual status foi solicitado para cada combinacao de resultado/viabilidade.
 */
@ExtendWith(MockitoExtension.class)
class TesteServiceImplTest {

    @Mock
    private TesteRepository testeRepository;

    @Mock
    private EquipamentoService equipamentoService;

    @InjectMocks
    private TesteServiceImpl testeService;

    private static Stream<Arguments> casos() {
        return Stream.of(
                Arguments.of(ResultadoTeste.APROVADO, null, StatusEquipamento.DISPONIVEL),
                Arguments.of(ResultadoTeste.REPROVADO, Boolean.TRUE, StatusEquipamento.EM_MANUTENCAO),
                Arguments.of(ResultadoTeste.REPROVADO, Boolean.FALSE, StatusEquipamento.SUCATA)
        );
    }

    @ParameterizedTest
    @MethodSource("casos")
    void registrarResultadoDeveDirecionarStatusDoEquipamento(
            ResultadoTeste resultado, Boolean manutencaoViavel, StatusEquipamento statusEsperado) {
        Long equipamentoId = 10L;
        Long responsavelId = 20L;

        Equipamento equipamento = Equipamento.builder()
                .id(equipamentoId)
                .numeroSerie("SN-100")
                .status(StatusEquipamento.EM_TESTE)
                .build();

        Usuario responsavel = Usuario.builder()
                .id(responsavelId)
                .nome("Tecnico RMA")
                .login("tecnico")
                .perfil(PerfilUsuario.RMA)
                .build();

        Teste teste = Teste.builder()
                .equipamento(equipamento)
                .responsavel(responsavel)
                .dataTeste(LocalDateTime.now())
                .resultado(resultado)
                .build();

        when(testeRepository.save(teste)).thenReturn(teste);

        testeService.registrarResultado(teste, manutencaoViavel);

        verify(equipamentoService).alterarStatus(equipamentoId, statusEsperado, responsavelId);
    }
}
