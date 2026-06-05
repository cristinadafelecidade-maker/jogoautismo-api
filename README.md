# 🧩 Jogo Autismo - Sistema Educativo Inclusivo REST API

Sistema backend desenvolvido em **Java 21 + Spring Boot 3.2** para apoiar o processo de aprendizagem de crianças com autismo e outras necessidades educativas especiais.

---

## 🏗️ Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.2.5 | Framework principal |
| Spring Data JPA | 3.2.5 | ORM e acesso a dados |
| Spring Security | 3.2.5 | Autenticação e autorização |
| MySQL | 8+ | Base de dados |
| JWT (jjwt) | 0.11.5 | Token de autenticação |
| SpringDoc OpenAPI | 2.5.0 | Documentação Swagger |
| BCrypt | - | Encriptação de passwords |

---

## 📦 Estrutura do Projecto

```
src/main/java/ujc/projecto/jogoautismo/
├── JogoautismoApplication.java     # Classe principal
├── config/
│   ├── SecurityConfig.java         # Configuração Spring Security + JWT
│   └── SwaggerConfig.java          # Configuração OpenAPI/Swagger
├── controller/
│   ├── AuthController.java         # POST /api/auth/login
│   ├── AlunoController.java        # CRUD /api/alunos
│   ├── EncarregadoController.java  # CRUD /api/encarregados
│   ├── EducadorController.java     # CRUD /api/educadores
│   ├── AtividadeEducativaController.java  # CRUD /api/atividades
│   └── ProgressoController.java    # CRUD /api/progressos
├── dto/
│   ├── LoginRequest.java           # DTO para login
│   ├── JwtResponse.java            # DTO resposta com token
│   ├── AlunoRequest.java           # DTO para criar/actualizar aluno
│   └── ProgressoRequest.java       # DTO para registar progresso
├── model/
│   ├── Usuario.java                # Entidade de utilizador do sistema
│   ├── Aluno.java                  # Aluno com autismo
│   ├── Encarregado.java            # Responsável/Encarregado
│   ├── Educador.java               # Educador especialista
│   ├── AtividadeEducativa.java     # Actividade do jogo
│   ├── Progresso.java              # Registo de progresso/pontuação
│   ├── Perfil.java                 # Enum: ADMIN, EDUCADOR, ENCARREGADO, ALUNO
│   ├── Genero.java                 # Enum: MASCULINO, FEMININO, OUTRO
│   ├── NivelAutismo.java           # Enum: LEVE, MEDIO, SEVERO
│   └── NivelDificuldade.java       # Enum: FACIL, MEDIO, DIFICIL
├── repository/
│   ├── UsuarioRepository.java
│   ├── AlunoRepository.java
│   ├── EncarregadoRepository.java
│   ├── EducadorRepository.java
│   ├── AtividadeEducativaRepository.java
│   └── ProgressoRepository.java
├── security/
│   ├── JwtUtil.java                # Geração e validação JWT
│   ├── JwtAuthFilter.java          # Filtro de autenticação JWT
│   └── UserDetailsServiceImpl.java # Carregamento de utilizador
```

---



---

## ⚙️ Configuração e Execução

### Pré-requisitos
- Java 21
- Maven 3.8+
- MySQL 8+

### 1. Configurar a base de dados

```sql
CREATE DATABASE jogo_autismo_api;
```

### 2. Configurar `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jogo_autismo_api?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=SUA_PASSWORD
```

### 3. Executar

```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 🔐 Autenticação e Segurança

### Fluxo de Autenticação JWT

```
1. POST /api/auth/login → { email, senha }
2. Servidor valida credenciais
3. Servidor retorna { token, tipo, email, perfil, nome }
4. Cliente inclui token em todas as requests:
   Authorization: Bearer <token>
```

### Perfis e Permissões

| Perfil | Alunos | Educadores | Encarregados | Atividades | Progressos |
|--------|--------|------------|--------------|------------|------------|
| **ADMIN** | CRUD | CRUD | CRUD | CRUD | CRUD |
| **EDUCADOR** | GET | GET | GET | CRUD | CRUD |
| **ENCARREGADO** | GET | ✗ | GET | GET | GET |
| **ALUNO** | ✗ | ✗ | ✗ | GET | POST/GET |

### Credenciais de Teste (senha padrão: `admin123`)

| Perfil | Email |
|--------|-------|
| ADMIN | admin@jogoautismo.mz |
| EDUCADOR | fatima@escola.mz |
| ENCARREGADO | maria@gmail.com |
| ALUNO | nilton@aluno.mz |

---

## 📡 Endpoints da API

### Autenticação
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/auth/login` | Login e obtenção de token JWT | Público |

### Alunos
| Método | Endpoint | Descrição | Perfis |
|--------|----------|-----------|--------|
| GET | `/api/alunos` | Listar todos os alunos | ADMIN, EDUCADOR, ENCARREGADO |
| GET | `/api/alunos/{id}` | Buscar aluno por ID | ADMIN, EDUCADOR, ENCARREGADO |
| GET | `/api/alunos/buscar?nome=X` | Buscar por nome | ADMIN, EDUCADOR, ENCARREGADO |
| GET | `/api/alunos/encarregado/{id}` | Alunos por encarregado | ADMIN, EDUCADOR, ENCARREGADO |
| POST | `/api/alunos` | Registar novo aluno | ADMIN |
| PUT | `/api/alunos/{id}` | Actualizar aluno | ADMIN |
| PUT | `/api/alunos/{id}/encarregado/{encId}` | Associar encarregado | ADMIN |
| DELETE | `/api/alunos/{id}` | Remover aluno | ADMIN |

### Encarregados
| Método | Endpoint | Descrição | Perfis |
|--------|----------|-----------|--------|
| GET | `/api/encarregados` | Listar todos | ADMIN, EDUCADOR, ENCARREGADO |
| GET | `/api/encarregados/{id}` | Buscar por ID | ADMIN, EDUCADOR, ENCARREGADO |
| GET | `/api/encarregados/buscar?nome=X` | Buscar por nome | ADMIN, EDUCADOR, ENCARREGADO |
| POST | `/api/encarregados` | Cadastrar encarregado | ADMIN |
| PUT | `/api/encarregados/{id}` | Actualizar | ADMIN |
| DELETE | `/api/encarregados/{id}` | Remover | ADMIN |

### Educadores
| Método | Endpoint | Descrição | Perfis |
|--------|----------|-----------|--------|
| GET | `/api/educadores` | Listar todos | ADMIN, EDUCADOR |
| GET | `/api/educadores/{id}` | Buscar por ID | ADMIN, EDUCADOR |
| GET | `/api/educadores/buscar?nome=X` | Buscar por nome | ADMIN, EDUCADOR |
| POST | `/api/educadores` | Cadastrar educador | ADMIN |
| PUT | `/api/educadores/{id}` | Actualizar | ADMIN |
| DELETE | `/api/educadores/{id}` | Remover | ADMIN |

### Atividades Educativas
| Método | Endpoint | Descrição | Perfis |
|--------|----------|-----------|--------|
| GET | `/api/atividades` | Listar todas | Todos autenticados |
| GET | `/api/atividades/{id}` | Buscar por ID | Todos autenticados |
| GET | `/api/atividades/nivel/{nivel}` | Filtrar por nível | Todos autenticados |
| GET | `/api/atividades/buscar?titulo=X` | Buscar por título | Todos autenticados |
| POST | `/api/atividades` | Registar atividade | ADMIN, EDUCADOR |
| PUT | `/api/atividades/{id}` | Actualizar atividade | ADMIN, EDUCADOR |
| DELETE | `/api/atividades/{id}` | Remover | ADMIN, EDUCADOR |

### Progressos
| Método | Endpoint | Descrição | Perfis |
|--------|----------|-----------|--------|
| GET | `/api/progressos` | Listar todos | ADMIN, EDUCADOR, ENCARREGADO, ALUNO |
| GET | `/api/progressos/{id}` | Buscar por ID | ADMIN, EDUCADOR, ENCARREGADO, ALUNO |
| GET | `/api/progressos/aluno/{id}` | Progressos de um aluno | ADMIN, EDUCADOR, ENCARREGADO, ALUNO |
| GET | `/api/progressos/atividade/{id}` | Por atividade | ADMIN, EDUCADOR, ENCARREGADO, ALUNO |
| POST | `/api/progressos` | Registar progresso | ADMIN, EDUCADOR, ALUNO |
| PUT | `/api/progressos/{id}` | Actualizar progresso | ADMIN, EDUCADOR |
| DELETE | `/api/progressos/{id}` | Remover | ADMIN, EDUCADOR |

---

## 📊 Status HTTP Utilizados

| Código | Significado | Quando é retornado |
|--------|------------|-------------------|
| 200 OK | Sucesso | GET, PUT bem-sucedidos |
| 201 Created | Criado | POST bem-sucedido |
| 204 No Content | Sem conteúdo | DELETE bem-sucedido |
| 400 Bad Request | Pedido inválido | Dados inválidos, entidade não encontrada |
| 401 Unauthorized | Não autenticado | Sem token ou token inválido |
| 403 Forbidden | Sem permissão | Perfil sem autorização para a operação |
| 404 Not Found | Não encontrado | ID não existe na base de dados |

---

## 📚 Swagger UI

Após iniciar a aplicação, aceda à documentação interativa em:

```
http://localhost:8080/swagger-ui.html
```

Para testar endpoints protegidos no Swagger:
1. Faça login em `POST /api/auth/login`
2. Copie o valor do campo `token`
3. Clique em **Authorize** (botão com cadeado)
4. Cole o token no campo e clique **Authorize**

---

## 📬 Importar Colecção Postman

1. Abrir o Postman
2. Clicar em **Import**
3. Seleccionar o ficheiro `JogoAutismo_Postman_Collection.json`
4. A colecção aparece com todos os endpoints organizados
5. Primeiro executar **"Login como ADMIN"** — o token é salvo automaticamente
6. Executar os outros requests normalmente

---

## 🎮 Relação com o Jogo Existente

O projecto original (`jogoautismoparaclaude.zip`) tinha:
- Thymeleaf (interface web MVC)
- Modelos: Crianca, Responsavel, Jogo, SessaoJogo, ResultadoAtividade
- Autenticação por sessão HTTP simples

Esta versão da API **preserva a lógica de negócio** e **expande** com:
- REST API completa (JSON) em vez de Thymeleaf
- JWT stateless em vez de sessões HTTP
- Novos modelos: Aluno (renomeado/expandido), Encarregado (expandido), Educador (novo), AtividadeEducativa (substitui Jogo), Progresso (novo)
- Controlo de acesso por perfil granular
- Documentação automática Swagger
- Dados de seed para todos os perfis

---

## 👨‍💻 Autor

**UJC - Projecto de Sistema Educativo Inclusivo**  
Java + Spring Boot + Spring Security + MySQL + JWT + Swagger
