import { navegarPara } from "../../../navegacao.js";
import { montarChromeDoSite } from "../../../componentes/chrome-site.js";

const CHAVE_TEMA = "theme";

// --- CONFIGURA O TEMA E A SOMBRA DO CABECALHO ---
export function configurarNavbarPagina(raiz) {
    // --- PRIMEIRO O CABEÇALHO E O RODAPÉ: O RESTO DEPENDE DOS ELEMENTOS QUE ELES CRIAM ---
    montarChromeDoSite(raiz);

    aplicarTemaNoBotao(raiz);
    configurarBotaoTema(raiz);
    configurarSombraCabecalho(raiz);
    configurarBotaoPortal(raiz);
}

// --- APLICA O ICONE CORRETO NO BOTAO DE TEMA ---
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

// --- CRIA A ONDA CIRCULAR QUE TROCA O TEMA ---
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

// --- SOMBRA NO CABECALHO AO ROLAR ---
function configurarSombraCabecalho(raiz) {
    const cabecalho = raiz.querySelector("#siteHeader");
    if (!cabecalho) {
        return;
    }

    const aoRolar = () => {
        cabecalho.classList.toggle("com-sombra", window.scrollY > 10);
    };
    aoRolar();
    window.addEventListener("scroll", aoRolar, { passive: true });
}

// --- BOTAO "PORTAL DO ALUNO" LEVA AO LOGIN ---
function configurarBotaoPortal(raiz) {
    const botao = raiz.querySelector("#btnPortal");
    botao?.addEventListener("click", () => {
        navegarPara("/login");
    });
}

// --- BOTOES NAVEGAM PARA UMA ROTA INTERNA ---
export function configurarBotoesInternos(raiz) {
    raiz.querySelectorAll("[data-ir]").forEach((elemento) => {
        elemento.addEventListener("click", () => {
            navegarPara(elemento.dataset.ir);
        });
    });
}

