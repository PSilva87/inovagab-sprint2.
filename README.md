# InovaGAB — API de Gestão de Inovação

API REST desenvolvida para apoiar a gestão do ciclo de inovação: definição de estratégias, submissão de ideias, priorização, aprovação, acompanhamento de projetos, auditoria e métricas gerenciais.

## Identificação acadêmica

| Item | Informação |
| --- | --- |
| Challenge | Grupo Águia Branca — Sprint 2 |
| Aluno | Paulo José da Silva |
| Turma | 2º ano • 2TDSOC • 2026/1 |
| Repositório | https://github.com/PSilva87/inovagab-sprint2 |

## Visão geral

O sistema possui três perfis de acesso:

| Perfil | Responsabilidades principais |
| --- | --- |
| `OPERADOR` | Consulta estratégias, registra as próprias ideias e as altera ou remove enquanto for o autor. |
| `GESTOR` | Consulta ideias, prioriza, aprova ou rejeita ideias e gerencia projetos. |
| `LIDER` | Gerencia estratégias, consulta projetos, dashboard, auditoria e métricas. |

## Tecnologias

- Java 21 (ambiente validado)
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Security com JWT e BCrypt
- Spring Data MongoDB
- Maven
- JUnit 5 e Mockito
- Postman para testes de comunicação

## Pré-requisitos

- JDK 21 instalado
- MongoDB em execução localmente na porta padrão (`27017`)
- IntelliJ IDEA ou outra IDE Java

## Como executar

1. Inicie o MongoDB local.
2. Abra o projeto na IDE.
3. Configure o SDK do projeto e o Maven para **Java 21**.
4. Execute a classe `InovagabApplication`.
5. A API será disponibilizada em `http://localhost:8080`.

Também é possível iniciar pelo terminal, na pasta do projeto:

```powershell
.\mvnw.cmd spring-boot:run
```

### Configuração local

O arquivo `src/main/resources/application.properties` contém a conexão de desenvolvimento:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/inovagab
jwt.expiration=86400000
```

Para executar localmente, existe o arquivo ignorado pelo Git `src/main/resources/application-local.properties`, onde fica a chave JWT da sua máquina. Caso ele não exista, copie `application-local.properties.example`, renomeie a cópia para `application-local.properties` e informe uma chave Base64 com no mínimo 32 bytes.

> Para ambientes compartilhados ou de produção, a URI do MongoDB e a chave JWT devem ser fornecidas pelas variáveis `MONGODB_URI` e `JWT_SECRET`. Não publique segredos reais em repositórios.

## Usuários iniciais

Na primeira execução, a aplicação cria os usuários abaixo caso ainda não existam no banco.

| Perfil | E-mail | Senha |
| --- | --- | --- |
| Operador | `operador@inovagab.com` | `123456` |
| Gestor | `gestor@inovagab.com` | `123456` |
| Líder | `lider@inovagab.com` | `123456` |

Essas credenciais existem somente para demonstração e testes locais.

## Autenticação

Faça login em `POST /api/auth/login`:

```json
{
  "email": "lider@inovagab.com",
  "senha": "123456"
}
```

A resposta inclui um token JWT. Nas rotas protegidas, envie o cabeçalho:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

## Endpoints

### Infraestrutura e autenticação

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/api/health` | Público | Verifica se a API está disponível. |
| POST | `/api/auth/login` | Público | Autentica usuário e retorna JWT. |
| GET | `/api/teste-protegido` | Autenticado | Valida que um JWT foi aceito. |

### Estratégias

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/api/estrategias` | Líder | Cria uma estratégia. |
| GET | `/api/estrategias` | Todos os perfis | Lista estratégias. |
| GET | `/api/estrategias/{id}` | Todos os perfis | Consulta uma estratégia. |
| PUT | `/api/estrategias/{id}` | Líder | Atualiza uma estratégia. |
| DELETE | `/api/estrategias/{id}` | Líder | Remove uma estratégia. |
| GET | `/api/estrategias/{id}/historico` | Todos os perfis | Consulta o histórico da estratégia. |

Exemplo de corpo para criação ou atualização:

```json
{
  "titulo": "Transformação Digital",
  "descricao": "Ampliar a tecnologia nos processos.",
  "categoria": "TECNOLOGIA",
  "campanha": "Inovação 2026",
  "ativa": true
}
```

### Ideias

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/api/ideias` | Operador | Cria uma ideia com status inicial `PENDENTE`. |
| GET | `/api/ideias/minhas` | Operador | Lista as ideias do usuário autenticado. |
| GET | `/api/ideias` | Gestor | Lista todas as ideias. |
| PUT | `/api/ideias/{id}` | Operador autor | Atualiza uma ideia própria. |
| DELETE | `/api/ideias/{id}` | Operador autor | Remove uma ideia própria. |
| PATCH | `/api/ideias/{id}/priorizacao` | Gestor | Define prioridade e pontuação. |
| PATCH | `/api/ideias/{id}/aprovacao` | Gestor | Aprova ou rejeita uma ideia. |

Exemplo de criação:

```json
{
  "titulo": "Automação de relatórios",
  "descricao": "Automatizar a geração de relatórios operacionais.",
  "estrategiaId": "ID_DA_ESTRATEGIA"
}
```

Exemplo de priorização:

```json
{
  "prioridade": "ALTA",
  "pontuacao": 90
}
```

Exemplo de aprovação:

```json
{
  "aprovada": true
}
```

Status possíveis de uma ideia: `PENDENTE`, `PRIORIZADA`, `APROVADA` e `REJEITADA`.

### Projetos

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/api/projetos` | Gestor | Cria um projeto. |
| GET | `/api/projetos` | Gestor e Líder | Lista projetos e seus dados de acompanhamento. |
| PUT | `/api/projetos/{id}` | Gestor | Atualiza os dados do projeto. |
| PATCH | `/api/projetos/{id}/progresso` | Gestor | Atualiza o progresso entre 0 e 100. |
| PATCH | `/api/projetos/{id}/resultados` | Gestor | Registra resultados e retorno financeiro. |
| DELETE | `/api/projetos/{id}` | Gestor | Remove um projeto. |

Exemplo de criação:

```json
{
  "nome": "Portal de Inovação",
  "descricao": "Portal para gestão de ideias.",
  "estrategiaId": "ID_DA_ESTRATEGIA",
  "investimento": 50000,
  "prazo": "2026-12-31"
}
```

Exemplo para atualizar o progresso:

```json
{
  "progresso": 50
}
```

Exemplo para registrar resultados:

```json
{
  "resultados": "Redução de 20% no tempo de consolidação dos relatórios.",
  "retornoFinanceiro": 75000
}
```

### Dashboard, auditoria e métricas

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/api/dashboard` | Líder | Exibe totais, investimento, retorno, lucro, ROI e resumo financeiro por estratégia. |
| GET | `/api/auditoria` | Líder | Lista registros de ações relevantes em ordem decrescente de data. |
| GET | `/api/metricas` | Líder | Retorna total de auditorias, ações nas últimas 24 horas e agrupamento por entidade. |

As ações de criação, atualização, exclusão, priorização, aprovação e registro de resultados geram registros de auditoria automaticamente.

O dashboard calcula `lucroTotal` (retorno menos investimento) e `roiPercentual`. Também retorna `resultadosPorEstrategia`, com o total de projetos, investimento, retorno, lucro e ROI de cada estratégia, permitindo que o aplicativo apresente análises gerais e específicas.

## Validações e respostas de erro

- Campos obrigatórios são validados pela API.
- E-mail e senha são obrigatórios no login.
- O progresso do projeto deve estar entre 0 e 100.
- Investimento e retorno financeiro não podem ser negativos.
- Um operador não pode alterar ou excluir ideias criadas por outro operador.
- Acesso sem token ou com perfil inadequado retorna `401 Unauthorized` ou `403 Forbidden`.
- Recursos inexistentes retornam `404 Not Found`.

## Testes

### Testes automatizados

Os testes unitários estão em `src/test/java/com/inovagab/inovagab/service` e foram executados com Java 21.

```powershell
.\mvnw.cmd test
```

Cobertura validada:

- geração e validação de token JWT;
- login bem-sucedido;
- criação, priorização e proteção de propriedade de ideias;
- criação e atualização de projetos;
- registro de auditoria e cálculo de métricas.

Resultado validado no IntelliJ: **8 testes aprovados de 8 executados**.

### Testes de comunicação

A coleção do Postman está disponível em:

`postman/InovaGAB-Sprint2.postman_collection.json`

Para importar, use **Import** no Postman e selecione esse arquivo. A coleção armazena o endereço base, tokens e identificadores retornados como variáveis, facilitando a execução em sequência.

## Estrutura principal

```text
src/main/java/com/inovagab/inovagab
├── config/security    # Spring Security e filtro JWT
├── controller         # Endpoints REST
├── dto                # Objetos de entrada e saída da API
├── model              # Entidades MongoDB e enums
├── repository         # Acesso aos dados
└── service            # Regras de negócio
```

## Aplicativo Android

O aplicativo Android está na pasta `mobile/` e consome a API Spring Boot por JWT.

Principais fluxos validados no aplicativo:

- login de operador, gestor e líder;
- cadastro e consulta de estratégias;
- cadastro e aprovação de ideias;
- cadastro e consulta de projetos;
- dashboard de liderança com indicadores;
- seleção de estratégia pelo nome, sem exigir que o usuário informe IDs;
- navegação por seta de retorno nas telas internas.

### Executar no Android Studio

1. Abra a pasta `mobile/` no Android Studio.
2. Inicie a API em `http://localhost:8080`.
3. Execute o aplicativo em um emulador Android.

No emulador, a API é acessada por `http://10.0.2.2:8080/api/`. Para usar um celular físico, atualize o endereço no arquivo `mobile/app/src/main/java/br/com/fiap/inovagab/ApiClient.kt` para o IP local do computador que estiver executando a API.

### Gerar APK

No Android Studio, use **Build > Generate App Bundles or APKs > Generate APKs**. O arquivo de depuração é gerado em:

```text
mobile/app/build/outputs/apk/debug/app-debug.apk
```

> O APK não é versionado no Git para manter o repositório leve. A versão gerada e validada localmente pode ser entregue junto com o projeto.

## Status da Sprint

- API REST, autenticação, permissões e auditoria: concluídos.
- Testes unitários: 8 de 8 aprovados.
- Testes de comunicação via Postman: concluídos.
- Aplicativo Android integrado e validado nos três perfis: concluído.
- Interface visual inspirada no contexto de mobilidade e inovação: concluída.
