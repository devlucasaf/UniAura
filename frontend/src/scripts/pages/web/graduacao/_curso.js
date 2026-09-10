import { navegarPara } from "../../../navegacao.js";
import { montarChromeDoSite } from "../../../componentes/chrome-site.js";

const CHAVE_TEMA = "theme";

// --- COMPORTAMENTO COMPARTILHADO PELAS PÁGINAS DE CURSO ---
export function montarPaginaDeCurso(raiz) {
    // --- PRIMEIRO O CABEÇALHO E O RODAPÉ: O RESTO DEPENDE DOS ELEMENTOS QUE ELES CRIAM ---
    montarChromeDoSite(raiz);

    const menu = raiz.querySelector("#siteMenu");
    const burger = raiz.querySelector("#siteBurger");

    ligarAnimacoes(raiz);

    // --- CABEÇALHO E RODAPÉ DO SITE ---
    aplicarTemaNoBotao(raiz);
    configurarBotaoTema(raiz);
    configurarMenuMobile(menu, burger);
    configurarBotoesPortal(raiz);
    configurarMegaMenu(raiz);

    // --- CONTEÚDO DO CURSO ---
    configurarAcordeaoMatriz(raiz);
    configurarRolagemSuave(raiz, menu, burger);

    // --- ANIMAÇÕES ---
    configurarEntradaDoHero(raiz);
    configurarRevelacaoNoScroll(raiz);
    configurarContadores(raiz);
    configurarBrilhoDosCartoes(raiz);
    configurarInclinacaoDoCartao(raiz);
    configurarOndaNosBotoes(raiz);
    configurarEfeitosDeScroll(raiz);
}

// --- INDICA AO CSS QUE O SCRIPT ESTÁ ATIVO ---
function ligarAnimacoes(raiz) {
    raiz.querySelector(".grad-page")?.classList.add("grad-anima");
}

// --- O USUÁRIO PEDIU MENOS MOVIMENTO NO SISTEMA OPERACIONAL? ---
function preferemMenosMovimento() {
    return window.matchMedia("(prefers-reduced-motion: reduce)").matches;
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

// --- ABRE/FECHA CADA SEMESTRE DA MATRIZ CURRICULAR ---
function configurarAcordeaoMatriz(raiz) {
    raiz.querySelectorAll(".grad-semester").forEach((semestre) => {
        const botao = semestre.querySelector("button");

        botao?.addEventListener("click", () => {
            const abrindo = !semestre.classList.contains("aberto");

            semestre.classList.toggle("aberto", abrindo);
            botao.setAttribute("aria-expanded", String(abrindo));
        });
    });
}

// --- ROLAGEM SUAVE PARA AS SEÇÕES INTERNAS DA PÁGINA ---
function configurarRolagemSuave(raiz, menu, burger) {
    const comportamento = preferemMenosMovimento() ? "auto" : "smooth";

    raiz.querySelectorAll('a[href^="#grad-"]').forEach((link) => {
        link.addEventListener("click", (evento) => {
            const alvo = raiz.querySelector(link.getAttribute("href"));

            if (!alvo) {
                return;
            }

            evento.preventDefault();
            alvo.scrollIntoView({
                behavior: comportamento,
                block: "start"
            });

            menu?.classList.remove("aberto");
            burger?.classList.remove("ativo");
            burger?.setAttribute("aria-expanded", "false");
        });
    });
}

// --- ENTRADA ESCALONADA DO HERO ASSIM QUE A PÁGINA MONTA ---
function configurarEntradaDoHero(raiz) {
    raiz.querySelectorAll("[data-entrada]").forEach((elemento) => {
        aplicarAnimacaoUnica(elemento, "entrando", "entrou", 2600);
    });
}

// --- REVELA OS BLOCOS CONFORME ELES ENTRAM NA TELA ---
function configurarRevelacaoNoScroll(raiz) {
    const alvos = Array.from(raiz.querySelectorAll("[data-revelar]"));

    if (alvos.length === 0) {
        return;
    }

    if (!("IntersectionObserver" in window)) {
        alvos.forEach((alvo) => aplicarAnimacaoUnica(alvo, "revelando", "revelado", 2600));
        return;
    }

    const observador = new IntersectionObserver((entradas) => {
        entradas.forEach((entrada) => {
            if (!entrada.isIntersecting) {
                return;
            }
            observador.unobserve(entrada.target);
            aplicarAnimacaoUnica(entrada.target, "revelando", "revelado", 2600);
        });
    }, {
        threshold: 0.16,
        rootMargin: "0px 0px -8% 0px"
    });

    alvos.forEach((alvo) => observador.observe(alvo));
}

// --- ROLA A ANIMACAO E DEIXA O ELEMENTO NO ESTADO ESTAVEL ---
function aplicarAnimacaoUnica(elemento, classeEmCurso, classeFinal, limite) {
    elemento.classList.add(classeEmCurso);

    const finalizar = () => {
        clearTimeout(seguranca);
        elemento.removeEventListener("animationend", aoTerminar);
        elemento.classList.add(classeFinal);
        elemento.classList.remove(classeEmCurso);
    };

    // --- IGNORA AS ANIMACOES QUE SOBEM DOS ELEMENTOS FILHOS ---
    const aoTerminar = (evento) => {
        if (evento.target === elemento) {
            finalizar();
        }
    };

    const seguranca = setTimeout(finalizar, limite);
    elemento.addEventListener("animationend", aoTerminar);
}

// --- NUMEROS DAS ESTATISTICAS CONTANDO ATE O VALOR FINAL ---
function configurarContadores(raiz) {
    const contadores = Array.from(raiz.querySelectorAll("[data-contador]"));

    if (contadores.length === 0) {
        return;
    }

    if (!("IntersectionObserver" in window)) {
        contadores.forEach(animarContador);
        return;
    }

    const observador = new IntersectionObserver((entradas) => {
        entradas.forEach((entrada) => {
            if (!entrada.isIntersecting) {
                return;
            }
            observador.unobserve(entrada.target);
            animarContador(entrada.target);
        });
    }, { threshold: 0.5 });

    contadores.forEach((contador) => observador.observe(contador));
}

function animarContador(elemento) {
    const destino = Number(elemento.dataset.contador);
    const sufixo = elemento.dataset.sufixo || "";

    if (!Number.isFinite(destino)) {
        return;
    }

    const escrever = (valor) => {
        elemento.textContent = `${valor.toLocaleString("pt-BR")}${sufixo}`;
    };

    if (preferemMenosMovimento()) {
        escrever(destino);
        return;
    }

    const duracao = 1600;
    let inicio = null;

    const passo = (agora) => {
        if (inicio === null) {
            inicio = agora;
        }

        const progresso = Math.min((agora - inicio) / duracao, 1);
        const suavizado = 1 - Math.pow(1 - progresso, 4);

        escrever(Math.round(destino * suavizado));

        if (progresso < 1) {
            requestAnimationFrame(passo);
        }
    };

    requestAnimationFrame(passo);
}

// --- BRILHO QUE SEGUE O CURSOR DENTRO DOS CARTOES ---
function configurarBrilhoDosCartoes(raiz) {
    if (preferemMenosMovimento()) {
        return;
    }

    raiz.querySelectorAll(".grad-card").forEach((cartao) => {
        cartao.addEventListener("pointermove", (evento) => {
            const area = cartao.getBoundingClientRect();
            cartao.style.setProperty("--grad-x", `${evento.clientX - area.left}px`);
            cartao.style.setProperty("--grad-y", `${evento.clientY - area.top}px`);
        });
    });
}

// --- CARTAO DE CODIGO QUE INCLINA ACOMPANHANDO O MOUSE ---
function configurarInclinacaoDoCartao(raiz) {
    const cartao = raiz.querySelector("[data-inclinar]");

    if (!cartao || preferemMenosMovimento()) {
        return;
    }

    const forca = 7;
    let liberado = false;

    setTimeout(() => {
        liberado = true;
    }, 1400);

    cartao.addEventListener("pointermove", (evento) => {
        if (!liberado || window.matchMedia("(hover: none)").matches) {
            return;
        }

        const area = cartao.getBoundingClientRect();
        const deslocamentoX = (evento.clientX - area.left) / area.width - 0.5;
        const deslocamentoY = (evento.clientY - area.top) / area.height - 0.5;

        cartao.style.transform =
            `perspective(900px)` +
            ` rotateX(${(-deslocamentoY * forca).toFixed(2)}deg)` +
            ` rotateY(${(deslocamentoX * forca).toFixed(2)}deg)` +
            ` translateY(-4px)`;
    });

    cartao.addEventListener("pointerleave", () => {
        cartao.style.transform = "";
    });
}

// --- ONDA CIRCULAR AO CLICAR NOS BOTOES DA PAGINA ---
function configurarOndaNosBotoes(raiz) {
    if (preferemMenosMovimento()) {
        return;
    }

    raiz.querySelectorAll(".grad-btn").forEach((botao) => {
        botao.addEventListener("pointerdown", (evento) => {
            const area = botao.getBoundingClientRect();
            const tamanho = Math.max(area.width, area.height) * 2.2;

            const onda = document.createElement("span");
            onda.className = "grad-onda";
            onda.style.width = `${tamanho}px`;
            onda.style.height = `${tamanho}px`;
            onda.style.left = `${evento.clientX - area.left}px`;
            onda.style.top = `${evento.clientY - area.top}px`;

            botao.appendChild(onda);
            onda.addEventListener("animationend", () => onda.remove(), { once: true });
        });
    });
}

// --- SOMBRA DO CABECALHO, BARRA DE PROGRESSO, PARALAXE E SEÇÃO ATIVA ---
function configurarEfeitosDeScroll(raiz) {
    const cabecalho = raiz.querySelector("#siteHeader");
    const barra = raiz.querySelector("[data-progresso]");
    const hero = raiz.querySelector(".grad-hero");
    const secoes = Array.from(raiz.querySelectorAll("main section[id]"));
    const links = Array.from(raiz.querySelectorAll(".grad-subnav-link"));
    const comParalaxe = !preferemMenosMovimento();

    let agendado = false;

    const atualizar = () => {
        agendado = false;

        // --- A PAGINA JA PODE TER SIDO TROCADA PELO ROTEADOR ---
        if (!raiz.isConnected) {
            return;
        }

        cabecalho?.classList.toggle("com-sombra", window.scrollY > 10);

        if (barra) {
            const rolavel = document.documentElement.scrollHeight - window.innerHeight;
            const lido = rolavel > 0 ? (window.scrollY / rolavel) * 100 : 0;
            barra.style.width = `${Math.min(Math.max(lido, 0), 100)}%`;
        }

        if (hero && comParalaxe) {
            hero.style.setProperty("--grad-parallax", `${Math.min(window.scrollY, 800) * 0.18}px`);
        }

        destacarSecaoAtiva(secoes, links);
    };

    const agendar = () => {
        if (agendado) {
            return;
        }
        agendado = true;
        requestAnimationFrame(atualizar);
    };

    atualizar();
    window.addEventListener(
        "scroll",
        agendar, {
            passive: true
        }
    );
    window.addEventListener(
        "resize",
        agendar, {
            passive: true
        }
    );
}

// --- MARCA NA SUBNAVEGACAO A SECAO QUE ESTA SENDO LIDA ---
function destacarSecaoAtiva(secoes, links) {
    if (secoes.length === 0 || links.length === 0) {
        return;
    }

    const linha = window.scrollY + 190;
    let ativa = secoes[0];

    secoes.forEach((secao) => {
        const topo = secao.getBoundingClientRect().top + window.scrollY;
        if (topo <= linha) {
            ativa = secao;
        }
    });

    links.forEach((link) => {
        link.classList.toggle("ativo", link.getAttribute("href") === `#${ativa.id}`);
    });
}
