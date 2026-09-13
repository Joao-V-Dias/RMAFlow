package com.joao.RMAFlow.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.joao.RMAFlow.model.Equipamento;
import com.joao.RMAFlow.model.HistoricoStatus;
import com.joao.RMAFlow.model.Modelo;
import com.joao.RMAFlow.model.Parceiro;
import com.joao.RMAFlow.model.Teste;
import com.joao.RMAFlow.model.Usuario;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.model.enums.ResultadoTeste;
import com.joao.RMAFlow.model.enums.StatusEquipamento;
import com.joao.RMAFlow.repository.EquipamentoRepository;
import com.joao.RMAFlow.repository.HistoricoStatusRepository;
import com.joao.RMAFlow.repository.ModeloRepository;
import com.joao.RMAFlow.repository.ParceiroRepository;
import com.joao.RMAFlow.repository.UsuarioRepository;
import com.joao.RMAFlow.service.TesteService;

/**
 * Ponta a ponta pela camada de servico (sem mock, H2 real): prova que registrar um teste
 * REPROVADO com manutencao inviavel realmente grava, em cascata, o novo status do Equipamento e
 * o registro de HistoricoStatus correspondente - os testes de unidade ja provam a logica, mas
 * mockada (nao provam o efeito colateral persistido de fato).
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EquipamentoTesteFluxoIT {

    @Autowired
    private TesteService testeService;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private HistoricoStatusRepository historicoStatusRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private ParceiroRepository parceiroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testeReprovadoComManutencaoInviavelLevaEquipamentoParaSucataEGeraHistorico() {
        Modelo modelo = modeloRepository.save(Modelo.builder()
                .nome("Roteador X")
                .fabricante("Fabricante Y")
                .tipo("roteador")
                .build());

        Parceiro parceiro = parceiroRepository.save(Parceiro.builder()
                .nome("Cliente Teste")
                .cpfCnpj("00000000000191")
                .endereco("Rua Teste, 123")
                .build());

        Usuario responsavel = usuarioRepository.save(Usuario.builder()
                .nome("Tecnico RMA")
                .login("tecnico.rma.fluxo")
                .senha("hash-qualquer")
                .perfil(PerfilUsuario.RMA)
                .build());

        Equipamento equipamento = equipamentoRepository.save(Equipamento.builder()
                .numeroSerie("SN-FLUXO-001")
                .modelo(modelo)
                .parceiro(parceiro)
                .status(StatusEquipamento.EM_TESTE)
                .build());

        Teste teste = Teste.builder()
                .equipamento(equipamento)
                .responsavel(responsavel)
                .dataTeste(LocalDateTime.now())
                .resultado(ResultadoTeste.REPROVADO)
                .build();

        testeService.registrarResultado(teste, false);

        Equipamento equipamentoAtualizado = equipamentoRepository.findById(equipamento.getId()).orElseThrow();
        assertThat(equipamentoAtualizado.getStatus()).isEqualTo(StatusEquipamento.SUCATA);

        List<HistoricoStatus> historico =
                historicoStatusRepository.findByEquipamentoIdOrderByDataAlteracaoDesc(equipamento.getId());
        assertThat(historico).isNotEmpty();

        HistoricoStatus ultimo = historico.get(0);
        assertThat(ultimo.getStatusAnterior()).isEqualTo(StatusEquipamento.EM_TESTE);
        assertThat(ultimo.getStatusNovo()).isEqualTo(StatusEquipamento.SUCATA);
    }
}
