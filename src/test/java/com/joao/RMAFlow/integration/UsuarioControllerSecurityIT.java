package com.joao.RMAFlow.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.joao.RMAFlow.dto.request.UsuarioRequestDTO;
import com.joao.RMAFlow.model.enums.PerfilUsuario;
import com.joao.RMAFlow.repository.UsuarioRepository;

import tools.jackson.databind.ObjectMapper;

/**
 * Prova de ponta a ponta (filtro de seguranca real, @PreAuthorize real via proxy do Spring, nao
 * mockado) que somente ADMIN cria Usuario - a regra de negocio mais importante do projeto.
 * CSRF permanece habilitado (padrao de RMAFLOW_AUTH.md); usamos csrf() em todas as chamadas para
 * isolar o que cada teste realmente verifica (autenticacao/autorizacao, nao CSRF).
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioControllerSecurityIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void criarSemAutenticacaoDeveSerRecusado() throws Exception {
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "Sem Auth", "sem.auth.user", "senha123", null, null, PerfilUsuario.ESTOQUE);

        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "estoque.user", roles = "ESTOQUE")
    void criarComPerfilEstoqueDeveSerProibido() throws Exception {
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "Via Estoque", "via.estoque.user", "senha123", null, null, PerfilUsuario.ESTOQUE);

        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin.user", roles = "ADMIN")
    void criarComPerfilAdminDeveTerSucessoEPersistirNoBanco() throws Exception {
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "Via Admin", "via.admin.user", "senha123", null, null, PerfilUsuario.ESTOQUE);

        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        assertThat(usuarioRepository.findByLogin("via.admin.user")).isPresent();
    }
}
