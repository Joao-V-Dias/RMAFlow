# RMAFlow — Especificação do Modelo de Domínio e Persistência

## Contexto

O RMAFlow é um sistema web para automatizar o processo de RMA (Retorno de Material para Análise) da OnNet, uma provedora de internet de médio porte. O sistema cobre o fluxo desde o recebimento do equipamento devolvido pelo cliente até a definição do seu destino final (reuso, manutenção, venda, descarte ou sucata), incluindo emissão de nota fiscal e geração de relatórios.

Este arquivo especifica **apenas a camada de domínio e persistência** (entidades JPA, enums, repositórios e camada de serviço básica) do backend. O frontend será feito com **Thymeleaf**, mas ainda não deve ser implementado nesta etapa — o foco agora é o backend em **Spring Boot**.

## Stack técnica

- **Java** (versão LTS mais recente estável, ex: 21)
- **Spring Boot** (versão estável mais recente)
- **Spring Data JPA** + **Hibernate**
- **Banco de dados**: PostgreSQL (pode ser trocado por H2 em ambiente de desenvolvimento/testes — configurar via `application.yml`/`application-dev.yml`)
- **Bean Validation** (`jakarta.validation`) para validações de entrada
- **Lombok** para reduzir boilerplate (getters/setters/construtores)
- **Frontend**: Thymeleaf — **não implementar nesta etapa**, apenas deixar o projeto pronto para receber templates depois (dependência `spring-boot-starter-thymeleaf` já incluída no `pom.xml`/`build.gradle`)

## Estrutura de pacotes sugerida

```
com.onnet.rmaflow
├── model                # entidades JPA
├── model.enums          # enums do domínio
├── repository           # interfaces Spring Data JPA
├── service              # interfaces de serviço (regras de negócio)
│   └── impl             # implementações das interfaces de serviço
├── controller           # interfaces de controller (stub por enquanto)
│   └── impl             # implementações das interfaces de controller
└── config               # configurações gerais
```

Para `service` e `controller`, cada classe terá sua interface correspondente: a interface declara os métodos/contrato, e a implementação (em `impl`) é anotada com `@Service`/`@RestController` (ou `@Controller`, se optar por MVC com Thymeleaf mais adiante) e injetada via a interface nas demais camadas.

Ajuste o nome-base do pacote (`com.onnet.rmaflow`) se preferir outro.

## Enums

### `StatusEquipamento`

```
RECEBIDO, EM_TESTE, EM_MANUTENCAO, DISPONIVEL, VENDIDO, DESCARTADO, SUCATA
```

### `ResultadoTeste`

```
APROVADO, REPROVADO
```

### `TipoNotaFiscal`

```
VENDA, DESCARTE, REMESSA
```

### `StatusNotaFiscal`

```
PENDENTE, EMITIDA, ENVIADA, ERRO_ENVIO
```

### `PerfilUsuario`

```
ESTOQUE, RMA, FINANCEIRO
```

## Entidades

### `Modelo`

Representa o modelo de um equipamento (ex: roteador X, ONU Y), reaproveitado por todas as unidades físicas daquele modelo.

| Campo          | Tipo   | Observações                                                              |
| -------------- | ------ | ------------------------------------------------------------------------ |
| id             | Long   | PK, auto-gerado                                                          |
| nome           | String | obrigatório                                                              |
| fabricante     | String | obrigatório                                                              |
| tipo           | String | ex: roteador, ONU, modem (pode virar enum `TipoEquipamento` se preferir) |
| especificacoes | String | opcional, texto livre                                                    |

Relacionamento: `Modelo` 1 — N `Equipamento`.

### `Parceiro`

Entidade unificada que representa tanto o cliente que devolveu o equipamento quanto o destinatário de uma nota fiscal (comprador, ou o próprio cliente em caso de remessa/descarte).

| Campo    | Tipo   | Observações                       |
| -------- | ------ | --------------------------------- |
| id       | Long   | PK, auto-gerado                   |
| nome     | String | nome ou razão social, obrigatório |
| cpfCnpj  | String | obrigatório, único                |
| endereco | String | obrigatório                       |
| telefone | String | opcional                          |
| email    | String | opcional, formato validado        |

Relacionamentos: `Parceiro` 1 — N `Equipamento` (quem devolveu); `Parceiro` 1 — N `NotaFiscal` (destinatário).

### `Usuario`

Usuário do sistema, com acesso controlado por perfil (RNF3).

| Campo    | Tipo            | Observações                              |
| -------- | --------------- | ---------------------------------------- |
| id       | Long            | PK, auto-gerado                          |
| nome     | String          | obrigatório                              |
| login    | String          | obrigatório, único                       |
| senha    | String          | obrigatório, armazenar com hash (BCrypt) |
| telefone | String          | opcional                                 |
| endereco | String          | opcional                                 |
| perfil   | `PerfilUsuario` | obrigatório                              |

### `Equipamento`

Entidade central do sistema.

| Campo       | Tipo                | Observações                                        |
| ----------- | ------------------- | -------------------------------------------------- |
| id          | Long                | PK, auto-gerado                                    |
| numeroSerie | String              | obrigatório, único                                 |
| modelo      | `Modelo`            | FK, obrigatório                                    |
| parceiro    | `Parceiro`          | FK, obrigatório (de quem foi recebido)             |
| status      | `StatusEquipamento` | obrigatório, default `RECEBIDO`                    |
| obsoleto    | boolean             | default `false`                                    |
| dataEntrada | LocalDateTime       | obrigatório, preenchido automaticamente na criação |

Relacionamentos: N — 1 com `Modelo` e `Parceiro`; 1 — N com `Teste`, `HistoricoStatus`; 0..1 — N com `Manutencao`; 1 — 0..1 com `NotaFiscal`.

### `Teste`

Registro do teste de funcionamento realizado pelo setor de RMA (RF3).

| Campo       | Tipo             | Observações     |
| ----------- | ---------------- | --------------- |
| id          | Long             | PK, auto-gerado |
| equipamento | `Equipamento`    | FK, obrigatório |
| responsavel | `Usuario`        | FK, obrigatório |
| dataTeste   | LocalDateTime    | obrigatório     |
| resultado   | `ResultadoTeste` | obrigatório     |
| observacoes | String           | opcional        |

### `Manutencao`

Registro de encaminhamento para manutenção (RF5/RF6).

| Campo       | Tipo          | Observações                   |
| ----------- | ------------- | ----------------------------- |
| id          | Long          | PK, auto-gerado               |
| equipamento | `Equipamento` | FK, obrigatório               |
| viavel      | boolean       | indica se o conserto é viável |
| dataInicio  | LocalDateTime | opcional                      |
| dataFim     | LocalDateTime | opcional                      |
| observacoes | String        | opcional                      |

### `NotaFiscal`

Nota fiscal emitida para venda, descarte ou remessa (RF8/RF10).

| Campo       | Tipo               | Observações                                           |
| ----------- | ------------------ | ----------------------------------------------------- |
| id          | Long               | PK, auto-gerado                                       |
| equipamento | `Equipamento`      | FK, obrigatório                                       |
| parceiro    | `Parceiro`         | FK, obrigatório (destinatário)                        |
| tipo        | `TipoNotaFiscal`   | obrigatório                                           |
| valor       | BigDecimal         | opcional (nulo quando não houver valor, ex: descarte) |
| dataEmissao | LocalDateTime      | preenchido na emissão                                 |
| status      | `StatusNotaFiscal` | obrigatório, default `PENDENTE`                       |

### `HistoricoStatus`

Trilha de auditoria das mudanças de status do equipamento — resolve o problema de falta de visibilidade citado na motivação do projeto.

| Campo          | Tipo                | Observações                        |
| -------------- | ------------------- | ---------------------------------- |
| id             | Long                | PK, auto-gerado                    |
| equipamento    | `Equipamento`       | FK, obrigatório                    |
| usuario        | `Usuario`           | FK, obrigatório (quem alterou)     |
| statusAnterior | `StatusEquipamento` | pode ser nulo no primeiro registro |
| statusNovo     | `StatusEquipamento` | obrigatório                        |
| dataAlteracao  | LocalDateTime       | preenchido automaticamente         |

## Repositórios

Criar uma interface `JpaRepository<Entidade, Long>` para cada entidade acima:

- `ModeloRepository`
- `ParceiroRepository` (incluir método `findByCpfCnpj`)
- `UsuarioRepository` (incluir método `findByLogin`)
- `EquipamentoRepository` (incluir métodos `findByStatus`, `findByNumeroSerie`)
- `TesteRepository`
- `ManutencaoRepository`
- `NotaFiscalRepository`
- `HistoricoStatusRepository` (incluir método `findByEquipamentoIdOrderByDataAlteracaoDesc`)

## Camada de serviço (interface + implementação, CRUD básico por enquanto)

Para cada entidade, criar uma **interface** em `service` com a assinatura das operações básicas (ex: `salvar`, `buscarPorId`, `listarTodos`, `atualizar`, `excluir`, mais os métodos específicos de cada domínio), e uma **implementação** em `service.impl`, anotada com `@Service`, injetando o repositório correspondente:

- `ModeloService` / `ModeloServiceImpl`
- `ParceiroService` / `ParceiroServiceImpl`
- `UsuarioService` / `UsuarioServiceImpl`
- `EquipamentoService` / `EquipamentoServiceImpl`
- `TesteService` / `TesteServiceImpl`
- `ManutencaoService` / `ManutencaoServiceImpl`
- `NotaFiscalService` / `NotaFiscalServiceImpl`
- `HistoricoStatusService` / `HistoricoStatusServiceImpl`

Toda alteração de `status` em `Equipamento` deve, futuramente, gerar automaticamente um registro em `HistoricoStatus` — pode já deixar um método utilitário preparado para isso na interface `EquipamentoService` (ex: `alterarStatus(Long equipamentoId, StatusEquipamento novoStatus, Long usuarioId)`), mesmo que a regra completa venha depois.

## Camada de controller (interface + stub de implementação, sem lógica ainda)

Mesma lógica de interface + implementação, mas por enquanto **apenas como stub**: a interface declara as assinaturas dos endpoints (ex: `listar`, `buscarPorId`, `criar`, `atualizar`, `excluir`), e a implementação em `controller.impl` fica anotada como `@RestController` (ou `@Controller`, se for MVC com Thymeleaf), injetando o `Service` correspondente pela interface, mas sem regra de negócio adicional — só repassando para o serviço. Não é necessário decidir rotas/paths definitivos nesta etapa; use um padrão simples (`/api/equipamentos`, `/api/parceiros`, etc.) que possa ser revisado depois:

- `ModeloController` / `ModeloControllerImpl`
- `ParceiroController` / `ParceiroControllerImpl`
- `UsuarioController` / `UsuarioControllerImpl`
- `EquipamentoController` / `EquipamentoControllerImpl`
- `TesteController` / `TesteControllerImpl`
- `ManutencaoController` / `ManutencaoControllerImpl`
- `NotaFiscalController` / `NotaFiscalControllerImpl`
- `HistoricoStatusController` / `HistoricoStatusControllerImpl`

## O que NÃO fazer nesta etapa

- Não implementar lógica de negócio nos controllers além de repassar para o serviço.
- Não implementar nenhuma tela ou template Thymeleaf.
- Não implementar autenticação/autorização (Spring Security) ainda — apenas deixar o campo `perfil` modelado em `Usuario`.
- Não implementar emissão real de nota fiscal (integração fiscal) nem envio de e-mail — apenas os campos e status previstos.

## Critérios de aceite desta etapa

1. Projeto Spring Boot compila e sobe sem erros.
2. Todas as entidades acima existem como classes JPA anotadas corretamente (`@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne`/`@OneToMany` conforme os relacionamentos).
3. Todos os enums existem em `model.enums`.
4. Todos os repositórios existem e estendem `JpaRepository`.
5. Cada entidade tem sua interface de serviço (`service`) e implementação (`service.impl`), injetando o repositório correspondente.
6. Cada entidade tem sua interface de controller (`controller`) e implementação stub (`controller.impl`), injetando o serviço correspondente pela interface.
7. `pom.xml`/`build.gradle` inclui as dependências: `spring-boot-starter-data-jpa`, `spring-boot-starter-web`, `spring-boot-starter-validation`, `spring-boot-starter-thymeleaf`, driver do PostgreSQL (ou H2 para dev), Lombok.
