<img
    width=100%
    src="https://capsule-render.vercel.app/api?type=waving&color=A020F0&height=120&section=header"
/>

<p align="center">
    <img 
        src="https://img.shields.io/badge/status-em%20progresso-yellow?style=for-the-badge" 
    />
    <img 
        src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" 
    />
    <img 
        src="https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" 
    />
    <img 
        src="https://img.shields.io/badge/SQL%20Server-CC2927?style=for-the-badge&logo=microsoftsqlserver&logoColor=white" 
    />
    <img 
        src="https://img.shields.io/badge/Next.js-16-000000?style=for-the-badge&logo=nextdotjs&logoColor=white" 
    />
    <img 
        src="https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge&logo=react&logoColor=black" 
    />
    <img 
        src="https://img.shields.io/badge/license-MIT-A020F0?style=for-the-badge" 
    />
</p>


# UniAura — ERP Acadêmico Universitário

ERP de uma universidade fictícia, a **UniAura**. Reúne o site institucional (cursos de graduação,
matrículas, contato) e o sistema interno: alunos, professores, turmas, disciplinas, notas,
frequência, biblioteca, financeiro, coordenação, ouvidoria e comunicação.

---

## 🧱 Stack

### Backend
- **Java 17** + **Spring Boot 3.5**
- **Spring Web**, **Spring Data JPA**, **Spring Security**, **Spring Validation**, **Spring Mail**
- **SQL Server** (driver `mssql-jdbc`)
- **Hibernate `ddl-auto=update`** (schema gerado a partir das entidades)
- **Lombok**, **ModelMapper**
- **SpringDoc OpenAPI** (Swagger UI)
- **java-jwt (Auth0)** para autenticação JWT (access token + refresh token)
- **Maven** (wrapper `./mvnw` incluso)

### Frontend
- **Next.js 16** (App Router) + **React 19**
- JavaScript + CSS puro (sem TypeScript e sem framework de CSS)
- **ESLint** (`eslint-config-next`)

### Banco de dados
- **Microsoft SQL Server** (local, porta `1433`)

---

## 🧩 Módulos

### Backend (`backend/src/main/java/erp/uniaura/modules`)

| Área | Módulos |
|---|---|
| Acesso | `autenticacao`, `usuario`, `auditoria` |
| Pessoas | `aluno`, `professor`, `funcionario`, `responsavel` |
| Acadêmico | `curso`, `disciplina`, `turma`, `matricula`, `prematricula`, `nota`, `frequencia`, `atividade`, `material`, `calendario`, `coordenacao` |
| Biblioteca | `biblioteca` (livros, exemplares, empréstimos, reservas, multas e configurações, com tarefas agendadas) |
| Administrativo | `financeiro` (mensalidades), `documento`, `processo`, `ouvidoria`, `comunicado` |

Infraestrutura compartilhada em `infra/`: segurança/JWT, CORS, OpenAPI, e-mail, armazenamento
de arquivos, gerador de protocolos e o seeder do admin.

### Perfis de usuário

`ADMIN`, `SECRETARIA`, `COORDENADOR`, `PROFESSOR`, `ALUNO`, `RESPONSAVEL`, `BIBLIOTECARIO`, `FINANCEIRO`.
Cada perfil tem sua própria área no frontend (ex.: `/secretaria/dashboard`, `/biblioteca/acervo`).

---

## 📁 Estrutura do projeto

```
ERP-Academic-School-System/
├── backend/                               # API Spring Boot
│   ├── pom.xml
│   └── src/main/
│       ├── java/erp/uniaura/
│       │   ├── UniAuraApplication.java
│       │   ├── dto/auth/
│       │   ├── exception/
│       │   ├── infra/
│       │   │   ├── config/                # CORS, OpenAPI, FrontendStarter
│       │   │   ├── email/
│       │   │   ├── protocolo/             # GeradorProtocolo
│       │   │   ├── security/              # JWT, filtros, UserDetails
│       │   │   ├── seed/                  # AdminSeeder
│       │   │   └── storage/               # upload de arquivos
│       │   └── modules/                   # um pacote por módulo (dto/model/repository/rest/service)
│       └── resources/
│           ├── application.properties
│           ├── application-local.properties.example
│           └── banner.txt
├── frontend/                              # Next.js (App Router)
│   ├── package.json
│   ├── next.config.mjs                    # basePath + proxy /api
│   ├── public/img/
│   └── src/
│       ├── app/
│       │   ├── (site)/                    # site institucional: graduação, matrículas, sobre, contato
│       │   ├── login/, portal-do-aluno/
│       │   └── admin/, secretaria/, coordenacao/, professor/,
│       │       aluno/, responsavel/, biblioteca/, financeiro/
│       ├── components/                    # interno/ (AppShell, CRUD), site/, ui/
│       ├── data/cursos/                   # conteúdo das páginas de cada curso
│       ├── hooks/
│       ├── lib/                           # cliente da API, auth, máscaras, formatação
│       └── styles/
├── scripts/
│   └── setup-database.sql                 # cria banco + login (executar uma vez)
└── storage/                               # arquivos enviados (uploads locais)
```

---

## 🛠️ Tecnologias

<div align="center">
    <img 
        alt="Java" 
        title="Java" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=java" 
    />
    <img 
        alt="Spring" 
        title="Spring" 
        width="40px" 
        style="padding: 5px;" 
        src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original.svg" 
    />
    <img 
        alt="JavaScript" 
        title="JavaScript" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=javascript" 
    />
    <img 
        alt="React" 
        title="React" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=react" 
    />
    <img 
        alt="Next.js" 
        title="Next.js" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=nextjs" 
    />
    <img 
        alt="HTML" 
        title="HTML" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=html" 
    />
    <img 
        alt="CSS" 
        title="CSS" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=css" 
    />
    <img 
        alt="SqlServer" 
        title="SqlServer" 
        width="40px" 
        style="padding: 5px;" 
        src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/microsoftsqlserver/microsoftsqlserver-original.svg" 
    />
    <img 
        alt="Git" 
        title="Git" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=git" 
    />
    <img 
        alt="GitHub" 
        title="GitHub" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=github" 
    />
    <img 
        alt="IntelliJ" 
        title="IntelliJ IDEA" 
        width="40px" 
        style="padding: 5px;" 
        src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/intellij/intellij-original.svg" 
    />
    <img 
        alt="VS Code" 
        title="VS Code" 
        width="40px" 
        style="padding: 5px;" 
        src="https://skillicons.dev/icons?i=vscode" 
    />
</div>

---

## ⚙️ Pré-requisitos

- **JDK 17**
- **Maven 3.9+** (ou o wrapper `./mvnw`)
- **Node.js 20.9+** e **npm** (exigido pelo Next.js 16)
- **SQL Server 2019+** rodando em `localhost:1433` (com autenticação SQL/mixed mode habilitada)
- **SSMS** (SQL Server Management Studio) para executar o script inicial

---

## 🚀 Setup

### 1. Clonar o repositório
```powershell
git clone https://github.com/devlucasaf/ERP-Academic-School-System.git
cd ERP-Academic-School-System
```

### 2. Banco de dados (SQL Server + SSMS)

O schema é gerado automaticamente pelo **Hibernate** (`spring.jpa.hibernate.ddl-auto=update`).
Você só precisa criar o **banco, o login e as permissões** uma única vez.

1. Abra o **SSMS** e conecte-se à instância como administrador (ex.: login `sa`).
2. Abra o arquivo [`scripts/setup-database.sql`](scripts/setup-database.sql) (**File > Open > File...**).
3. Execute o script (**F5**). Ele cria o banco, o login SQL `erp_academic_user` e concede `db_owner`.
4. Garanta que a instância esteja em **"SQL Server and Windows Authentication mode"**
   (botão direito na instância > **Properties > Security**) e **reinicie o serviço** do SQL Server.

> ⚠️ Confira se o nome do banco no script bate com o `databaseName` da
> `spring.datasource.url` em `application.properties` (atualmente `ErpUniversityAcademicSystem`).

### 3. Credenciais locais (perfil `local`)

Nenhuma senha fica no `application.properties` versionado. O perfil ativo padrão é `local`,
que carrega o arquivo `application-local.properties` (ignorado pelo Git):

```powershell
cd backend/src/main/resources
copy application-local.properties.example application-local.properties
```

Preencha nele:
- `spring.datasource.username` / `spring.datasource.password`
- `security.jwt.secret` (gere com `openssl rand -base64 48`)
- `spring.mail.username` / `spring.mail.password` (opcional)

Como alternativa, use variáveis de ambiente:

| Variável | Uso | Padrão |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | perfil ativo | `local` |
| `DB_USER` / `DB_PASS` | credenciais do SQL Server | — |
| `JWT_SECRET` | segredo de assinatura do JWT | `change-me-in-production` |
| `MAIL_HOST` / `MAIL_PORT` / `MAIL_USER` / `MAIL_PASS` | SMTP | `smtp.gmail.com` / `587` |
| `STORAGE_TYPE` / `STORAGE_LOCAL_PATH` | armazenamento de uploads | `local` / `./storage` |
| `START_FRONTEND` | backend inicia o frontend junto (dev) | `true` |

### 4. Schema e usuário admin

Ao subir o Spring Boot:

- O **Hibernate** cria/atualiza as tabelas a partir das entidades JPA.
- O **`AdminSeeder`** cria o administrador inicial na primeira execução (idempotente):
  - **email:** `admin@escola.com`
  - **senha:** `admin123` (hash BCrypt)

  Troque essa senha após o primeiro login.

> ⚠️ `ddl-auto=update` é ideal para **desenvolvimento**. Para produção, considere migrations
> versionadas (ex.: Flyway/Liquibase).

### 5. Backend

```powershell
cd backend
./mvnw spring-boot:run
```

No IntelliJ, também há a run configuration **Backend** em `.run/`.

- API: **http://localhost:8080/api**
- Swagger UI: **http://localhost:8080/api/swagger-ui.html**
- Arquivos enviados: servidos em **`/api/files`**

Com `START_FRONTEND=true` (padrão), o backend executa `npm run dev` na pasta `frontend/` e abre o
navegador. Defina `START_FRONTEND=false` para rodar o frontend separadamente.

### 6. Frontend

```powershell
cd frontend
npm install
npm run dev
```

App disponível em: **http://localhost:3000/uniaura/app**

O `next.config.mjs` define o `basePath` `/uniaura/app` e redireciona `/api/*` para
`http://localhost:8080/api/*`, então o frontend não precisa de configuração de CORS em dev.

Outros scripts: `npm run build`, `npm run start`, `npm run lint`.

---

## 📜 Licença

Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.

<img
    width="100%"
    src="https://capsule-render.vercel.app/api?type=waving&color=A020F0&height=120&section=footer"
/>