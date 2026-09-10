import { navegarPara } from "../../../navegacao.js";
import { montarChromeDoSite } from "../../../componentes/chrome-site.js";

const CHAVE_TEMA = "theme";

export function montar(raiz) {
    // --- PRIMEIRO O CABEÇALHO E O RODAPÉ: O RESTO DEPENDE DOS ELEMENTOS QUE ELES CRIAM ---
    montarChromeDoSite(raiz);

    const menu = raiz.querySelector("#siteMenu");
    const burger = raiz.querySelector("#siteBurger");
    const cabecalho = raiz.querySelector("#siteHeader");

    aplicarTemaNoBotao(raiz);
    configurarBotaoTema(raiz);
    configurarMenuMobile(menu, burger);
    configurarSombraCabecalho(cabecalho);
    configurarBotoesPortal(raiz);
    configurarMegaMenu(raiz);
    configurarRolagemSuave(raiz);
    configurarFiltroPorArea(raiz);
}

// --- FILTRA OS CARDS PELA ÁREA DE ATUAÇÃO MARCADA EM data-area ---
function configurarFiltroPorArea(raiz) {
    const botoes = Array.from(raiz.querySelectorAll(".cursos-filtros .site-filtro-btn"));
    const cards = Array.from(raiz.querySelectorAll(".curso-card"));

    if (botoes.length === 0 || cards.length === 0) {
        return;
    }

    const contador = raiz.querySelector("#cursosContador");
    const vazio = raiz.querySelector("#cursosVazio");

    const aplicar = (filtro) => {
        let visiveis = 0;

        cards.forEach((card) => {
            const combina = filtro === "todos" || card.dataset.area === filtro;
            card.hidden = !combina;
            if (combina) {
                visiveis++;
            }
        });

        if (contador) {
            contador.textContent = visiveis === 1
                ? "1 curso encontrado"
                : `${visiveis} cursos encontrados`;
        }

        if (vazio) {
            vazio.hidden = visiveis > 0;
        }
    };

    botoes.forEach((botao) => {
        botao.addEventListener("click", () => {
            botoes.forEach((b) => {
                b.classList.remove("ativo");
                b.setAttribute("aria-selected", "false");
            });
            botao.classList.add("ativo");
            botao.setAttribute("aria-selected", "true");

            aplicar(botao.dataset.filtro);
        });
    });

    aplicar("todos");
}

// --- ABRE E FECHA O MENU HAMBURGER NO MOBILE ---
function configurarMenuMobile(menu, burger) {
    burger?.addEventListener("click", () => {
        const aberto = menu.classList.toggle("aberto");
        burger.classList.toggle("ativo", aberto);
        burger.setAttribute("aria-expanded", String(aberto));
    });
}

// --- BOTOES QUE LEVAM AO LOGIN DO SISTEMA ---
function configurarBotoesPortal(raiz) {
    raiz.querySelectorAll("#btnPortal, #btnPortalHero, #btnPortalAlunos").forEach((botao) => {
        botao.addEventListener("click", () => {
            navegarPara("/login");
        });
    });
}

// --- SOMBRA NO CABECALHO QUANDO A PAGINA E ROLADA ---
function configurarSombraCabecalho(cabecalho) {
    const aoRolar = () => {
        cabecalho?.classList.toggle("com-sombra", window.scrollY > 10);
    };
    aoRolar();
    window.addEventListener("scroll", aoRolar, { passive: true });
}

// --- APLICA O ESTADO VISUAL DO BOTAO DE TEMA CONFORME O TEMA ATUAL ---
function aplicarTemaNoBotao(raiz) {
    const botao = raiz.querySelector("#btnTemaSite");
    if (!botao) {
        return;
    }
    const escuro = document.documentElement.getAttribute("data-theme") === "dark";
    botao.classList.toggle("noturno", escuro);
}

// --- ALTERNA O TEMA COM ANIMACAO ---
function configurarBotaoTema(raiz) {
    const botao = raiz.querySelector("#btnTemaSite");
    if (!botao) {
        return;
    }

    botao.addEventListener("click", () => {
        criarOndaDeTema(botao);

        botao.classList.add("girando");
        setTimeout(() => botao.classList.remove("girando"), 600);

        setTimeout(() => {
            const atual = document.documentElement.getAttribute("data-theme") === "dark" ? "dark" : "light";
            const novo = atual === "dark" ? "light" : "dark";
            document.documentElement.setAttribute("data-theme", novo);
            localStorage.setItem(CHAVE_TEMA, novo);
            botao.classList.toggle("noturno", novo === "dark");
        }, 150);
    });
}

// --- CRIA UMA ONDA CIRCULAR EXPANSIVA A PARTIR DO BOTAO CLICADO ---
function criarOndaDeTema(botao) {
    const retangulo = botao.getBoundingClientRect();
    const onda = document.createElement("span");
    onda.className = "site-tema-onda";

    const centroX = retangulo.left + retangulo.width / 2;
    const centroY = retangulo.top + retangulo.height / 2;
    onda.style.left = `${centroX}px`;
    onda.style.top = `${centroY}px`;

    const proximoTema = document.documentElement.getAttribute("data-theme") === "dark" ? "light" : "dark";
    onda.dataset.tema = proximoTema;

    document.body.appendChild(onda);

    onda.addEventListener("animationend", () => onda.remove(), { once: true });
}

// --- MEGA-MENU ---
function configurarMegaMenu(raiz) {
    const itens = raiz.querySelectorAll("[data-mega]");
    const submenus = raiz.querySelectorAll("[data-submenu]");

    itens.forEach((item) => {
        const link = item.querySelector(".site-link-com-mega");

        link?.addEventListener("click", (evento) => {
            const ehTouch = window.matchMedia("(hover: none)").matches;
            if (ehTouch) {
                evento.preventDefault();
                const abrindo = !item.classList.contains("aberto");
                fecharTodosMegaMenus(itens);
                item.classList.toggle("aberto", abrindo);
                link.setAttribute("aria-expanded", String(abrindo));
            }
        });
    });

    submenus.forEach((submenu) => {
        const gatilho = submenu.querySelector(".site-mega-link-gatilho");

        gatilho?.addEventListener("click", (evento) => {
            const ehTouch = window.matchMedia("(hover: none)").matches;
            if (ehTouch) {
                evento.preventDefault();
                const abrindo = !submenu.classList.contains("aberto");
                fecharTodosSubmenus(submenus);
                submenu.classList.toggle("aberto", abrindo);
                gatilho.setAttribute("aria-expanded", String(abrindo));
            }
        });
    });

    document.addEventListener("keydown", (evento) => {
        if (evento.key === "Escape") {
            fecharTodosMegaMenus(itens);
            fecharTodosSubmenus(submenus);
        }
    });

    document.addEventListener("click", (evento) => {
        const clicouDentroMega = evento.target.closest("[data-mega]");
        const clicouDentroSubmenu = evento.target.closest("[data-submenu]");
        if (!clicouDentroMega) {
            fecharTodosMegaMenus(itens);
        }
        if (!clicouDentroSubmenu) {
            fecharTodosSubmenus(submenus);
        }
    });
}

function fecharTodosMegaMenus(itens) {
    itens.forEach((item) => {
        item.classList.remove("aberto");
        item.querySelector(".site-link-com-mega")?.setAttribute("aria-expanded", "false");
    });
}

function fecharTodosSubmenus(submenus) {
    submenus.forEach((submenu) => {
        submenu.classList.remove("aberto");
        submenu.querySelector(".site-mega-link-gatilho")?.setAttribute("aria-expanded", "false");
    });
}

// --- ROLAGEM SUAVE PARA ANCORAS DA PROPRIA PAGINA ---
function configurarRolagemSuave(raiz) {
    raiz.querySelectorAll('a[href^="#"]:not([href^="#/"])').forEach((link) => {
        link.addEventListener("click", (evento) => {
            const alvo = raiz.querySelector(link.getAttribute("href"));
            if (!alvo) {
                return;
            }
            evento.preventDefault();
            alvo.scrollIntoView({
                behavior: "smooth",
                block: "start"
            });
        });
    });
}
