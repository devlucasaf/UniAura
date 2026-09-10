import { reescreverLinksInternos } from "../navegacao.js";


// --- CURSOS DE GRADUAÇÃO DO SUBMENU; rota nula = curso ainda sem página ---
const CURSOS_GRADUACAO = [
    { nome: "Ciência da Computação",                  rota: "#/graduacao/ciencia-da-computacao" },
    { nome: "Análise e Desenvolvimento de Sistemas",  rota: "#/graduacao/analise-e-desenvolvimento-de-sistemas" },
    { nome: "Engenharia de Software",                 rota: "#/graduacao/engenharia-de-software" },
    { nome: "Ciência de Dados",                       rota: "#/graduacao/ciencia-de-dados" },
    { nome: "Engenharia Mecatrônica",                 rota: "#/graduacao/engenharia-mecatronica" },
    { nome: "Engenharia Civil",                       rota: "#/graduacao/engenharia-civil" },
    { nome: "Administração",                          rota: "#/graduacao/administracao" },
    { nome: "Direito",                                rota: "#/graduacao/direito" },
    { nome: "Engenharia de Produção",                 rota: null },
    { nome: "Engenharia Elétrica",                    rota: null },
    { nome: "Arquitetura e Urbanismo",                rota: null },
    { nome: "Biomedicina",                            rota: null }
];

// --- BLOCOS DO MEGA-MENU "A UNIVERSIDADE" ---
const MEGA_UNIVERSIDADE = [
    { titulo: "Nossa História",           descricao: "35 anos de excelência acadêmica",   rota: "#/web/sobre/historia" },
    { titulo: "Nossa Equipe",             descricao: "Quem faz a universidade",           rota: "#/web/sobre/equipe" },
    { titulo: "Estrutura Física",         descricao: "Salas, laboratórios e biblioteca",  rota: null },
    { titulo: "Regimento Universitário",  descricao: "Normas e diretrizes",               rota: null },
    { titulo: "Prêmios e Rankings",       descricao: "Reconhecimentos nacionais",         rota: null }
];

// --- DEMAIS MODALIDADES DO MEGA-MENU "CURSOS" ---
const MEGA_MODALIDADES = [
    { titulo: "Pós-Graduação",           descricao: "Especialização, mestrado e doutorado" },
    { titulo: "Cursos Técnicos",         descricao: "Formação profissional rápida" },
    { titulo: "Idiomas",                 descricao: "Inglês, espanhol e libras" },
    { titulo: "Extensão Universitária",  descricao: "Projetos comunitários" },
    { titulo: "Pesquisa",                descricao: "Grupos e laboratórios" }
];

const LINKS_INSTITUCIONAIS = [
    { nome: "A Universidade",  rota: "#/home" },
    { nome: "Cursos",          rota: "#/graduacao/cursos" },
    { nome: "Matrículas",      rota: "#/matriculas" },
    { nome: "Contato",         rota: "#/contato" }
];

const SVG_FACEBOOK = `<svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor"
                         stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                        <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3Z"></path>
                    </svg>`;

const SVG_INSTAGRAM = `<svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor"
                          stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                        <rect x="2" y="2" width="20" height="20" rx="5"></rect>
                        <circle cx="12" cy="12" r="4"></circle>
                        <path d="M17.5 6.5h.01"></path>
                    </svg>`;

const SVG_GITHUB = `<svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor" aria-hidden="true">
                        <path d="M12 .5C5.65.5.5 5.65.5 12c0 5.08 3.29 9.39 7.86 10.91.57.1.78-.25.78-.55
                 0-.27-.01-1.17-.02-2.12-3.2.69-3.88-1.36-3.88-1.36-.52-1.32-1.28-1.67-1.28-1.67
                 -1.05-.72.08-.71.08-.71 1.16.08 1.77 1.19 1.77 1.19 1.03 1.76 2.69 1.25
                 3.34.96.1-.75.4-1.25.72-1.54-2.55-.29-5.23-1.27-5.23-5.66
                 0-1.25.45-2.28 1.18-3.08-.12-.29-.51-1.46.11-3.05 0 0 .96-.31 3.15 1.18
                 .91-.25 1.89-.38 2.86-.38.97 0 1.95.13 2.86.38 2.19-1.49 3.15-1.18
                 3.15-1.18.62 1.59.23 2.76.11 3.05.73.8 1.18 1.83 1.18 3.08
                 0 4.4-2.69 5.36-5.25 5.65.41.35.77 1.04.77 2.1
                 0 1.52-.01 2.75-.01 3.12 0 .3.21.66.79.55A11.5 11.5 0 0 0 23.5 12
                 C23.5 5.65 18.35.5 12 .5z"/>
                    </svg>`;

const REDES_SOCIAIS = [
    { nome: "Facebook",  href: "#",                                                              svg: SVG_FACEBOOK,  externo: false },
    { nome: "Instagram", href: "https://www.instagram.com/__.fr3it4s.__/",                       svg: SVG_INSTAGRAM, externo: false },
    { nome: "GitHub",    href: "https://github.com/devlucasaf/ERP-University-Academic-System",   svg: SVG_GITHUB,    externo: true }
];

// --- MONTA O CABEÇALHO E O RODAPÉ NA PÁGINA JÁ RENDERIZADA ---
export function montarChromeDoSite(raiz, opcoes = {}) {
    substituirMarcador(raiz, '[data-chrome="header"]', cabecalhoHtml(opcoes));
    substituirMarcador(raiz, '[data-chrome="footer"]', rodapeHtml());

    reescreverLinksInternos(raiz);
}

// --- TROCA O MARCADOR PELO HTML, PRESERVANDO A ESTRUTURA ORIGINAL DO DOM ---
function substituirMarcador(raiz, seletor, html) {
    const marcador = raiz.querySelector(seletor);

    if (!marcador) {
        return;
    }

    const molde = document.createElement("template");
    molde.innerHTML = html.trim();
    marcador.replaceWith(...molde.content.childNodes);
}

// --- SEM ROTA VIRA ÂNCORA MORTA, COMO ERA NOS TEMPLATES ---
function submenuLink({ nome, rota }) {
    return `<a href="${rota || "#"}" class="site-submenu-link" role="menuitem">${nome}</a>`;
}

function megaLink({ titulo, descricao, rota }) {
    return `<a href="${rota || "#"}" class="site-mega-link" role="menuitem">
                                <strong>${titulo}</strong>
                                <span>${descricao}</span>
                            </a>`;
}

function rodapeLink({ nome, rota }) {
    return `<a href="${rota}">${nome}</a>`;
}

function redeSocialLink({ nome, href, svg, externo }) {
    const alvo = externo ? ' target="_blank"' : "";
    return `<a href="${href}"${alvo} rel="noopener" aria-label="${nome}">
                    ${svg}
                    ${nome}
                </a>`;
}

function cabecalhoHtml(opcoes) {
    const universidade = opcoes.ancoras
        ? 'href="#sobre" data-scroll="sobre"'
        : 'href="#/home"';

    const cursos = opcoes.ancoras
        ? 'href="#ensino" data-scroll="ensino"'
        : 'href="#/graduacao/cursos"';

    return `
    <header id="siteHeader" class="site-header">
        <div class="site-container site-nav">

            <a href="#/home" class="site-brand" aria-label="UniAura - Página inicial">
                <img src="/img/uniaura.png" alt="" class="site-brand-logo" />
                <span class="site-brand-text">
                    <strong>UniAura</strong>
                </span>
            </a>

            <nav id="siteMenu" class="site-menu" aria-label="Navegação principal">

                <div class="site-menu-item" data-mega>
                    <a class="site-link site-link-com-mega" ${universidade} aria-haspopup="true" aria-expanded="false">
                        A Universidade
                        <svg class="site-link-seta" viewBox="0 0 24 24" width="14" height="14" fill="none"
                             stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                            <polyline points="6 9 12 15 18 9"></polyline>
                        </svg>
                    </a>

                    <div class="site-mega" role="menu" aria-label="Sobre a UniAura">
                        <div class="site-mega-grade">
                            ${MEGA_UNIVERSIDADE.map(megaLink).join("\n                            ")}
                        </div>

                        <div class="site-mega-destaque">
                            <span class="site-mega-eyebrow">Conheça</span>
                            <h4>UniAura, 35 anos de tradição</h4>
                            <p>Uma universidade que forma líderes e transforma a sociedade.</p>
                            <a href="#/web/sobre/historia" class="site-mega-cta">Nossa história completa →</a>
                        </div>
                    </div>
                </div>

                <div class="site-menu-item" data-mega>
                    <a class="site-link site-link-com-mega" ${cursos} aria-haspopup="true" aria-expanded="false">
                        Cursos
                        <svg class="site-link-seta" viewBox="0 0 24 24" width="14" height="14" fill="none"
                             stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                            <polyline points="6 9 12 15 18 9"></polyline>
                        </svg>
                    </a>

                    <div class="site-mega" role="menu" aria-label="Cursos de graduação e pós">
                        <div class="site-mega-grade">
                            <div class="site-mega-item-flyout" data-submenu>
                                <button type="button" class="site-mega-link-gatilho" aria-haspopup="true" aria-expanded="false">
                                    <span class="site-mega-link-texto">
                                        <strong>Graduação</strong>
                                        <span>Bacharelados e tecnólogos</span>
                                    </span>
                                    <svg class="site-mega-seta-submenu" viewBox="0 0 24 24" width="16" height="16" fill="none"
                                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                                        <polyline points="9 6 15 12 9 18"></polyline>
                                    </svg>
                                </button>

                                <div class="site-submenu" role="menu" aria-label="Cursos de Graduação">
                                    ${CURSOS_GRADUACAO.map(submenuLink).join("\n                                    ")}
                                </div>
                            </div>
                            ${MEGA_MODALIDADES.map(megaLink).join("\n                            ")}
                        </div>

                        <div class="site-mega-destaque">
                            <span class="site-mega-eyebrow">Calendário</span>
                            <h4>Ano letivo 2027</h4>
                            <p>Inscrições abertas para o vestibular e para a pós-graduação.</p>
                            <a href="#/matriculas" class="site-mega-cta">Fazer minha matrícula →</a>
                        </div>
                    </div>
                </div>

                <a class="site-link" href="#/home">Notícias</a>
                <a class="site-link" href="#/contato">Contato</a>
                <a class="site-link" href="#/contato">Portal do Aluno</a>
            </nav>

            <div class="site-actions">

                <button
                        id="btnTemaSite"
                        class="site-tema-toggle"
                        type="button"
                        aria-label="Alternar tema claro e escuro"
                        title="Alternar tema"
                >
                    <span class="site-tema-icone site-tema-sol" aria-hidden="true">
                        <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                             stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="12" cy="12" r="4"></circle>
                            <path d="M12 2v2M12 20v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M2 12h2M20 12h2M4.93 19.07l1.41-1.41M17.66 6.34l1.41-1.41"></path>
                        </svg>
                    </span>
                    <span class="site-tema-icone site-tema-lua" aria-hidden="true">
                        <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                             stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path>
                        </svg>
                    </span>
                </button>

                <button id="btnPortal" class="btn btn-primary site-btn-entrar" type="button">
                    Portal do Aluno
                </button>

                <button id="siteBurger" class="site-burger" type="button" aria-label="Abrir menu" aria-expanded="false">
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
            </div>
        </div>
    </header>`;
}

// --- FOOTER ---
function rodapeHtml() {
    return `
    <footer class="site-footer">
        <div class="site-container site-footer-inner">

            <div class="site-footer-brand">
                <div class="site-brand">
                    <img src="/img/uniaura.png" alt="" class="site-brand-logo" />
                    <span class="site-brand-text">Uni<strong>Aura</strong></span>
                </div>
                <p class="muted">
                    Educação que transforma vidas há mais de 35 anos.
                </p>
            </div>

            <div class="site-footer-col">
                <h4>Institucional</h4>
                ${LINKS_INSTITUCIONAIS.map(rodapeLink).join("\n                ")}
            </div>

            <div class="site-footer-col">
                <h4>Redes sociais</h4>
                ${REDES_SOCIAIS.map(redeSocialLink).join("\n                ")}
            </div>
        </div>

        <div class="site-footer-bottom">
            © 2026 UniAura — Todos os direitos reservados.
        </div>
    </footer>`;
}
