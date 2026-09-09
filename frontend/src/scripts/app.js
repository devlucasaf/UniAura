import {
    estaAutenticado,
    obterUsuario,
    possuiPerfil,
    dashboardDoPerfil,
    encerrarSessao
} from "./auth.js";
import { notificar } from "./util.js";
import {
    caminhoAtual,
    caminhoParaUrl,
    navegarPara,
    reescreverLinksInternos,
    ativarInterceptacaoGlobal
} from "./navegacao.js";

const MODELOS = import.meta.glob("/src/templates/{shared,auth,dashboards,secretaria,web}/**/*.html", {
    query: "?raw",
    import: "default"
});

async function carregarModelo(caminho) {
    const carregador = MODELOS[`/src/templates/${caminho}`];
    if (!carregador) {
        throw new Error(`Modelo não encontrado: ${caminho}`);
    }
    return carregador();
}

// --- TABELA DE ROTAS ---
const ROTAS = {
    "/central": {
        modelo: "web/erp-central.html",
        modulo: () => import("./pages/web/erp-central.js"),
        publico: true,
        modo: "limpo",
        classe: "central-screen",
        titulo: "Central de Acesso — Universidade Aura"
    },

    "/home": {
        modelo: "web/home.html",
        modulo: () => import("./pages/web/home.js"),
        publico: true,
        modo: "limpo",
        classe: "site-screen",
        titulo: "Colégio Áurea"
    },

    // --- PAGINAS INTERNAS DO SITE INSTITUCIONAL ---
    "/web/sobre/historia": {
        modelo: "web/sobre/nossa-historia.html",
        modulo: () => import("./pages/web/sobre/nossa-historia.js"),
        publico: true,
        modo: "limpo",
        classe: "site-screen",
        titulo: "Nossa História — Colégio Áurea"
    },

    "/web/sobre/equipe": {
        modelo: "web/sobre/equipe.html",
        modulo: () => import("./pages/web/sobre/equipe.js"),
        publico: true,
        modo: "limpo",
        classe: "site-screen",
        titulo: "Nossa Equipe — Colégio Áurea"
    },

    "/graduacao/cursos": {
        modelo: "web/graduacao/cursos.html",
        modulo: () => import("./pages/web/graduacao/cursos.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Cursos de Graduação — Universidade Aura"
    },

    "/graduacao/ciencia-da-computacao": {
        modelo: "web/graduacao/tecnologia/ciencia-da-computacao.html",
        modulo: () => import("./pages/web/graduacao/tecnologia/ciencia-da-computacao.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Ciência da Computação — Universidade Aura"
    },

    "/graduacao/analise-e-desenvolvimento-de-sistemas": {
        modelo: "web/graduacao/tecnologia/analise-e-desenvolvimento-de-sistemas.html",
        modulo: () => import("./pages/web/graduacao/tecnologia/analise-e-desenvolvimento-de-sistemas.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Análise e Desenvolvimento de Sistemas — Universidade Aura"
    },

    "/graduacao/engenharia-de-software": {
        modelo: "web/graduacao/tecnologia/engenharia-de-software.html",
        modulo: () => import("./pages/web/graduacao/tecnologia/engenharia-de-software.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Engenharia de Software — Universidade Aura"
    },

    "/graduacao/ciencia-de-dados": {
        modelo: "web/graduacao/tecnologia/ciencia-de-dados.html",
        modulo: () => import("./pages/web/graduacao/tecnologia/ciencia-de-dados.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Ciência de Dados — Universidade Aura"
    },

    "/graduacao/engenharia-mecatronica": {
        modelo: "web/graduacao/engenharia/engenharia-mecatronica.html",
        modulo: () => import("./pages/web/graduacao/engenharia/engenharia-mecatronica.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Engenharia Mecatrônica — Universidade Aura"
    },

    "/graduacao/engenharia-civil": {
        modelo: "web/graduacao/engenharia/engenharia-civil.html",
        modulo: () => import("./pages/web/graduacao/engenharia/engenharia-civil.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Engenharia Civil — Universidade Aura"
    },

    "/graduacao/ciencias-aeronauticas": {
        modelo: "web/graduacao/engenharia/ciencias-aeronauticas.html",
        modulo: () => import("./pages/web/graduacao/engenharia/ciencias-aeronauticas.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Ciências Aeronáuticas — Universidade Aura"
    },

    "/graduacao/engenharia-eletrica": {
        modelo: "web/graduacao/engenharia/engenharia-eletrica.html",
        modulo: () => import("./pages/web/graduacao/engenharia/engenharia-eletrica.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Engenharia Elétrica — Universidade Aura"
    },

    "/graduacao/engenharia-florestal": {
        modelo: "web/graduacao/engenharia/engenharia-florestal.html",
        modulo: () => import("./pages/web/graduacao/engenharia/engenharia-florestal.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Engenharia Florestal — Universidade Aura"
    },

    "/graduacao/engenharia-mecanica": {
        modelo: "web/graduacao/engenharia/engenharia-mecanica.html",
        modulo: () => import("./pages/web/graduacao/engenharia/engenharia-mecanica.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Engenharia Mecânica — Universidade Aura"
    },

    "/graduacao/engenharia-producao": {
        modelo: "web/graduacao/engenharia/engenharia-producao.html",
        modulo: () => import("./pages/web/graduacao/engenharia/engenharia-producao.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Engenharia de Produção — Universidade Aura"
    },

    "/graduacao/engenharia-quimica": {
        modelo: "web/graduacao/engenharia/engenharia-quimica.html",
        modulo: () => import("./pages/web/graduacao/engenharia/engenharia-quimica.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Engenharia Química — Universidade Aura"
    },

    "/graduacao/artes-cenicas": {
        modelo: "web/graduacao/arte/artes-cenicas.html",
        modulo: () => import("./pages/web/graduacao/arte/artes-cenicas.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Artes Cênicas — Universidade Aura"
    },

    "/graduacao/artes-visuais": {
        modelo: "web/graduacao/arte/artes-visuais.html",
        modulo: () => import("./pages/web/graduacao/arte/artes-visuais.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Artes Visuais — Universidade Aura"
    },

    "/graduacao/design": {
        modelo: "web/graduacao/arte/design.html",
        modulo: () => import("./pages/web/graduacao/arte/design.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Design — Universidade Aura"
    },

    "/graduacao/fotografia": {
        modelo: "web/graduacao/arte/fotografia.html",
        modulo: () => import("./pages/web/graduacao/arte/fotografia.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Fotografia — Universidade Aura"
    },

    "/graduacao/moda": {
        modelo: "web/graduacao/arte/moda.html",
        modulo: () => import("./pages/web/graduacao/arte/moda.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Design de Moda — Universidade Aura"
    },

    "/graduacao/publicidade-propaganda": {
        modelo: "web/graduacao/arte/publicidade-propaganda.html",
        modulo: () => import("./pages/web/graduacao/arte/publicidade-propaganda.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Publicidade e Propaganda — Universidade Aura"
    },

    "/graduacao/medicina": {
        modelo: "web/graduacao/saude/medicina.html",
        modulo: () => import("./pages/web/graduacao/saude/medicina.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Medicina — Universidade Aura"
    },

    "/graduacao/odontologia": {
        modelo: "web/graduacao/saude/odontologia.html",
        modulo: () => import("./pages/web/graduacao/saude/odontologia.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Odontologia — Universidade Aura"
    },

    "/graduacao/farmacia": {
        modelo: "web/graduacao/saude/farmacia.html",
        modulo: () => import("./pages/web/graduacao/saude/farmacia.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Farmácia — Universidade Aura"
    },

    "/graduacao/fisioterapia": {
        modelo: "web/graduacao/saude/fisioterapia.html",
        modulo: () => import("./pages/web/graduacao/saude/fisioterapia.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Fisioterapia — Universidade Aura"
    },

    "/graduacao/nutricao": {
        modelo: "web/graduacao/saude/nutricao.html",
        modulo: () => import("./pages/web/graduacao/saude/nutricao.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Nutrição — Universidade Aura"
    },

    "/graduacao/biomedicina": {
        modelo: "web/graduacao/saude/biomedicina.html",
        modulo: () => import("./pages/web/graduacao/saude/biomedicina.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Biomedicina — Universidade Aura"
    },

    "/graduacao/biologia": {
        modelo: "web/graduacao/saude/biologia.html",
        modulo: () => import("./pages/web/graduacao/saude/biologia.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Ciências Biológicas — Universidade Aura"
    },

    "/graduacao/educacao-fisica": {
        modelo: "web/graduacao/saude/educacao-fisica.html",
        modulo: () => import("./pages/web/graduacao/saude/educacao-fisica.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Educação Física — Universidade Aura"
    },

    "/graduacao/fonoaudiologia": {
        modelo: "web/graduacao/saude/fonoaudiologia.html",
        modulo: () => import("./pages/web/graduacao/saude/fonoaudiologia.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Fonoaudiologia — Universidade Aura"
    },

    "/graduacao/terapia-ocupacional": {
        modelo: "web/graduacao/saude/terapia-ocupacional.html",
        modulo: () => import("./pages/web/graduacao/saude/terapia-ocupacional.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Terapia Ocupacional — Universidade Aura"
    },

    "/graduacao/medicina-veterinaria": {
        modelo: "web/graduacao/saude/medicina-veterinaria.html",
        modulo: () => import("./pages/web/graduacao/saude/medicina-veterinaria.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Medicina Veterinária — Universidade Aura"
    },

    "/graduacao/administracao": {
        modelo: "web/graduacao/negocio/administracao.html",
        modulo: () => import("./pages/web/graduacao/negocio/administracao.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Administração — Universidade Aura"
    },

    "/graduacao/direito": {
        modelo: "web/graduacao/humanas/direito.html",
        modulo: () => import("./pages/web/graduacao/humanas/direito.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Direito — Universidade Aura"
    },

    "/matriculas": {
        modelo: "web/matriculas.html",
        modulo: () => import("./pages/web/matriculas.js"),
        publico: true,
        modo: "limpo",
        classe: "grad-screen",
        titulo: "Matrículas — Universidade Aura"
    },

    "/login": {
        modelo: "auth/login.html",
        modulo: () => import("./pages/auth/login.js"),
        publico: true,
        modo: "limpo",
        classe: "auth-screen",
        titulo: "Entrar"
    },

    "/aluno/dashboard": {
        modelo: "dashboards/dashboard.html",
        modulo: () => import("./pages/aluno/dashboard.js"),
        perfis: ["ALUNO"],
        titulo: "Painel do Aluno"
    },

    "/professor/dashboard": {
        modelo: "dashboards/dashboard.html",
        modulo: () => import("./pages/professor/dashboard.js"),
        perfis: ["PROFESSOR"],
        titulo: "Painel do Professor"
    },

    "/coordenacao/dashboard": {
        modelo: "dashboards/dashboard.html",
        modulo: () => import("./pages/coordenacao/dashboard.js"),
        perfis: ["COORDENADOR"],
        titulo: "Painel da Coordenação"
    },

    "/secretaria/dashboard": {
        modelo: "dashboards/dashboard.html",
        modulo: () => import("./pages/secretaria/dashboard.js"),
        perfis: ["SECRETARIA"],
        titulo: "Painel da Secretaria"
    },

    "/biblioteca/dashboard": {
        modelo: "dashboards/dashboard.html",
        modulo: () => import("./pages/biblioteca/dashboard.js"),
        perfis: ["BIBLIOTECARIO"],
        titulo: "Painel da Biblioteca"
    },

    "/financeiro/dashboard": {
        modelo: "dashboards/dashboard.html",
        modulo: () => import("./pages/financeiro/dashboard.js"),
        perfis: ["FINANCEIRO"],
        titulo: "Painel Financeiro"
    },

    "/responsavel/dashboard": {
        modelo: "dashboards/dashboard.html",
        modulo: () => import("./pages/responsavel/dashboard.js"),
        perfis: ["RESPONSAVEL"],
        titulo: "Painel do Responsável"
    },

    "/admin/dashboard": {
        modelo: "dashboards/dashboard.html",
        modulo: () => import("./pages/admin/dashboard.js"),
        perfis: ["ADMIN"],
        titulo: "Painel do Administrador"
    },

    // --- CADASTROS DA SECRETARIA ---
    "/secretaria/alunos": {
        modelo: "secretaria/alunos.html",
        modulo: () => import("./pages/secretaria/alunos.js"),
        perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"],
        titulo: "Alunos"
    },
    "/secretaria/professores": {
        modelo: "secretaria/professores.html",
        modulo: () => import("./pages/secretaria/professores.js"),
        perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"],
        titulo: "Professores"
    },
    "/secretaria/responsaveis": {
        modelo: "secretaria/responsaveis.html",
        modulo: () => import("./pages/secretaria/responsaveis.js"),
        perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"],
        titulo: "Responsáveis"
    },
    "/secretaria/funcionarios": {
        modelo: "secretaria/funcionarios.html",
        modulo: () => import("./pages/secretaria/funcionarios.js"),
        perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"],
        titulo: "Funcionários"
    }
};

// --- TEMA ---
const CHAVE_TEMA = "theme";

function aplicarTemaSalvo() {
    const tema = localStorage.getItem(CHAVE_TEMA) || "light";
    document.documentElement.setAttribute("data-theme", tema);
}

function alternarTema() {
    const atual = document.documentElement.getAttribute("data-theme") === "dark" ? "dark" : "light";
    const novo = atual === "dark" ? "light" : "dark";
    document.documentElement.setAttribute("data-theme", novo);
    localStorage.setItem(CHAVE_TEMA, novo);
}

// --- LAYOUT DA APLICAÇÃO ---
let layoutMontado = false;

async function garantirLayout() {
    const app = document.getElementById("app");

    if (!layoutMontado || !document.getElementById("conteudo")) {
        app.className = "app-shell";
        app.innerHTML = await carregarModelo("shared/shell.html");
        document.getElementById("menuLateral").innerHTML = await carregarModelo("shared/sidebar.html");
        document.getElementById("cabecalho").innerHTML = await carregarModelo("shared/header.html");
        reescreverLinksInternos(app);
        ligarEventosCabecalho();
        layoutMontado = true;
    }

    atualizarInfoUsuario();
    filtrarMenuPorPerfil();
}

// --- LIGA OS BOTÕES DO CABEÇALHO ---
function ligarEventosCabecalho() {
    document.getElementById("btnTema")?.addEventListener("click", alternarTema);
    document.getElementById("btnSair")?.addEventListener("click", () => {
        encerrarSessao();
        navegarPara("/login");
    });
    document.getElementById("btnAlternarMenu")?.addEventListener("click", () => {
        document.getElementById("app").classList.toggle("sidebar-open");
    });
}

// --- PREENCHE O NOME DO USUÁRIO NO CABEÇALHO ---
function atualizarInfoUsuario() {
    const usuario = obterUsuario();
    document.querySelectorAll("[data-usuario-nome]").forEach(elemento => {
        elemento.textContent = usuario?.nome || "";
    });
}

// --- EXIBE APENAS OS ITENS DE MENU PERMITIDOS PARA O PERFIL ATUAL ---
function filtrarMenuPorPerfil() {
    const usuario = obterUsuario();
    const perfil = usuario?.role;
    document.querySelectorAll("[data-perfis]").forEach(elemento => {
        const permitidos = elemento.getAttribute("data-perfis").split(",").map(p => p.trim());
        elemento.hidden = !perfil || !permitidos.includes(perfil);
    });
}

// --- DESTACA O LINK ATIVO E ATUALIZA O TÍTULO DA PÁGINA ---
function marcarRotaAtiva(caminho, titulo) {
    const urlAtual = caminhoParaUrl(caminho);
    document.querySelectorAll(".sidebar-nav .nav-link").forEach(link => {
        link.classList.toggle("active", link.getAttribute("href") === urlAtual);
    });

    const tituloElemento = document.querySelector("[data-titulo-pagina]");
    if (tituloElemento) {
        tituloElemento.textContent = titulo || "";
    }
}

// --- RENDERIZAÇÃO DA ROTA ATUAL ---
async function renderizar() {
    const caminho = caminhoAtual();

    if (caminho === "/" || caminho === "") {
        navegarPara(estaAutenticado() ? dashboardDoPerfil(obterUsuario().role) : "/central", { substituir: true });
        return;
    }

    const rota = ROTAS[caminho];

    if (!rota) {
        navegarPara(estaAutenticado() ? dashboardDoPerfil(obterUsuario().role) : "/central", { substituir: true });
        return;
    }

    if (!rota.publico && !estaAutenticado()) {
        navegarPara("/login", { substituir: true });
        return;
    }

    if (rota.publico && caminho === "/login" && estaAutenticado()) {
        navegarPara(dashboardDoPerfil(obterUsuario().role), { substituir: true });
        return;
    }

    if (rota.perfis && !possuiPerfil(rota.perfis)) {
        notificar("Você não tem permissão para acessar esta área.", "error");
        navegarPara(dashboardDoPerfil(obterUsuario()?.role), { substituir: true });
        return;
    }

    const app = document.getElementById("app");

    if (rota.modo === "limpo") {
        layoutMontado = false;
        app.className = rota.classe || "auth-screen";
        app.innerHTML = await carregarModelo(rota.modelo);
        reescreverLinksInternos(app);
        const modulo = await rota.modulo();
        modulo.montar?.(app, {
            rota
        });
    } else {
        await garantirLayout();
        const conteudo = document.getElementById("conteudo");
        conteudo.innerHTML = await carregarModelo(rota.modelo);
        reescreverLinksInternos(conteudo);
        marcarRotaAtiva(caminho, rota.titulo);
        document.getElementById("app").classList.remove("sidebar-open");
        const modulo = await rota.modulo();
        modulo.montar?.(conteudo, {
            rota
        });
    }
}

aplicarTemaSalvo();
ativarInterceptacaoGlobal();
window.addEventListener("popstate", renderizar);
window.addEventListener("DOMContentLoaded", renderizar);
