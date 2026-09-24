// --- ANIMAÇÕES COMPARTILHADAS PELAS PÁGINAS "grad-*" (CONTATO, MATRÍCULAS, CURSOS) ---
// --- PORTADO QUASE VERBATIM DO _curso.js ANTIGO: SÓ REMOVE O QUE JÁ VIROU REACT (HEADER) ---
function preferemMenosMovimento() {
    return window.matchMedia("(prefers-reduced-motion: reduce)").matches;
}

// --- MONTA TODOS OS EFEITOS NA RAIZ INFORMADA E RETORNA UMA FUNÇÃO DE LIMPEZA ---
export function montarEfeitosDePagina(raiz) {
    if (!raiz) {
        return () => {};
    }

    raiz.querySelector(".grad-page")?.classList.add("grad-anima");

    const limpezas = [
        configurarAcordeaoMatriz(raiz),
        configurarRolagemSuave(raiz),
        configurarEntradaDoHero(raiz),
        configurarRevelacaoNoScroll(raiz),
        configurarContadores(raiz),
        configurarBrilhoDosCartoes(raiz),
        configurarInclinacaoDoCartao(raiz),
        configurarOndaNosBotoes(raiz),
        configurarEfeitosDeScroll(raiz)
    ].filter(Boolean);

    return () => limpezas.forEach((limpar) => limpar());
}

// --- ABRE/FECHA CADA SEMESTRE DA MATRIZ CURRICULAR (SÓ UM ABERTO POR VEZ) ---
function configurarAcordeaoMatriz(raiz) {
    const remocoes = [];
    const semestres = Array.from(raiz.querySelectorAll(".grad-semester, .cc-semester"));

    semestres.forEach((semestre) => {
        const botao = semestre.querySelector("button");
        if (!botao) {
            return;
        }
        const aoClicar = () => {
            const abrindo = !semestre.classList.contains("aberto");

            semestres.forEach((outro) => {
                if (outro !== semestre) {
                    outro.classList.remove("aberto");
                    outro.querySelector("button")?.setAttribute("aria-expanded", "false");
                }
            });

            semestre.classList.toggle("aberto", abrindo);
            botao.setAttribute("aria-expanded", String(abrindo));
        };
        botao.addEventListener("click", aoClicar);
        remocoes.push(() => botao.removeEventListener("click", aoClicar));
    });
    return () => remocoes.forEach((r) => r());
}

// --- ROLAGEM SUAVE PARA AS SEÇÕES INTERNAS DA PÁGINA ---
function configurarRolagemSuave(raiz) {
    const comportamento = preferemMenosMovimento() ? "auto" : "smooth";
    const remocoes = [];

    raiz.querySelectorAll('a[href^="#grad-"]').forEach((link) => {
        const aoClicar = (evento) => {
            const alvo = raiz.querySelector(link.getAttribute("href"));
            if (!alvo) {
                return;
            }
            evento.preventDefault();
            alvo.scrollIntoView({ behavior: comportamento, block: "start" });
        };
        link.addEventListener("click", aoClicar);
        remocoes.push(() => link.removeEventListener("click", aoClicar));
    });

    return () => remocoes.forEach((r) => r());
}

// --- ENTRADA ESCALONADA DO HERO ASSIM QUE A PÁGINA MONTA ---
function configurarEntradaDoHero(raiz) {
    raiz.querySelectorAll("[data-entrada]").forEach((elemento) => {
        aplicarAnimacaoUnica(elemento, "entrando", "entrou", 2600);
    });
    return null;
}

// --- REVELA OS BLOCOS CONFORME ELES ENTRAM NA TELA ---
function configurarRevelacaoNoScroll(raiz) {
    const alvos = Array.from(raiz.querySelectorAll("[data-revelar]"));
    if (alvos.length === 0) {
        return null;
    }

    if (!("IntersectionObserver" in window)) {
        alvos.forEach((alvo) => aplicarAnimacaoUnica(alvo, "revelando", "revelado", 2600));
        return null;
    }

    const observador = new IntersectionObserver((entradas) => {
        entradas.forEach((entrada) => {
            if (!entrada.isIntersecting) {
                return;
            }
            observador.unobserve(entrada.target);
            aplicarAnimacaoUnica(entrada.target, "revelando", "revelado", 2600);
        });
    }, { threshold: 0.16, rootMargin: "0px 0px -8% 0px" });

    alvos.forEach((alvo) => observador.observe(alvo));
    return () => observador.disconnect();
}

function aplicarAnimacaoUnica(elemento, classeEmCurso, classeFinal, limite) {
    elemento.classList.add(classeEmCurso);

    const finalizar = () => {
        clearTimeout(seguranca);
        elemento.removeEventListener("animationend", aoTerminar);
        elemento.classList.add(classeFinal);
        elemento.classList.remove(classeEmCurso);
    };

    const aoTerminar = (evento) => {
        if (evento.target === elemento) {
            finalizar();
        }
    };

    const seguranca = setTimeout(finalizar, limite);
    elemento.addEventListener("animationend", aoTerminar);
}

// --- NÚMEROS DAS ESTATÍSTICAS CONTANDO ATÉ O VALOR FINAL ---
function configurarContadores(raiz) {
    const contadores = Array.from(raiz.querySelectorAll("[data-contador]"));
    if (contadores.length === 0) {
        return null;
    }

    if (!("IntersectionObserver" in window)) {
        contadores.forEach(animarContador);
        return null;
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
    return () => observador.disconnect();
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

// --- BRILHO QUE SEGUE O CURSOR DENTRO DOS CARTÕES ---
function configurarBrilhoDosCartoes(raiz) {
    if (preferemMenosMovimento()) {
        return null;
    }
    const remocoes = [];
    raiz.querySelectorAll(".grad-card").forEach((cartao) => {
        const aoMover = (evento) => {
            const area = cartao.getBoundingClientRect();
            cartao.style.setProperty("--grad-x", `${evento.clientX - area.left}px`);
            cartao.style.setProperty("--grad-y", `${evento.clientY - area.top}px`);
        };
        cartao.addEventListener("pointermove", aoMover);
        remocoes.push(() => cartao.removeEventListener("pointermove", aoMover));
    });
    return () => remocoes.forEach((r) => r());
}

// --- CARTÃO DE CÓDIGO QUE INCLINA ACOMPANHANDO O MOUSE ---
function configurarInclinacaoDoCartao(raiz) {
    const cartao = raiz.querySelector("[data-inclinar]");
    if (!cartao || preferemMenosMovimento()) {
        return null;
    }

    const forca = 7;
    let liberado = false;
    const temporizador = setTimeout(() => { liberado = true; }, 1400);

    const aoMover = (evento) => {
        if (!liberado || window.matchMedia("(hover: none)").matches) {
            return;
        }
        const area = cartao.getBoundingClientRect();
        const deslocamentoX = (evento.clientX - area.left) / area.width - 0.5;
        const deslocamentoY = (evento.clientY - area.top) / area.height - 0.5;
        cartao.style.transform =
            `perspective(900px) rotateX(${(-deslocamentoY * forca).toFixed(2)}deg) rotateY(${(deslocamentoX * forca).toFixed(2)}deg) translateY(-4px)`;
    };
    const aoSair = () => { cartao.style.transform = ""; };

    cartao.addEventListener("pointermove", aoMover);
    cartao.addEventListener("pointerleave", aoSair);

    return () => {
        clearTimeout(temporizador);
        cartao.removeEventListener("pointermove", aoMover);
        cartao.removeEventListener("pointerleave", aoSair);
    };
}

// --- ONDA CIRCULAR AO CLICAR NOS BOTÕES DA PÁGINA ---
function configurarOndaNosBotoes(raiz) {
    if (preferemMenosMovimento()) {
        return null;
    }
    const remocoes = [];
    raiz.querySelectorAll(".grad-btn").forEach((botao) => {
        const aoPressionar = (evento) => {
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
        };
        botao.addEventListener("pointerdown", aoPressionar);
        remocoes.push(() => botao.removeEventListener("pointerdown", aoPressionar));
    });
    return () => remocoes.forEach((r) => r());
}

// --- BARRA DE PROGRESSO, PARALAXE DO HERO E SEÇÃO ATIVA NA SUBNAVEGAÇÃO ---
// --- (A SOMBRA DO CABEÇALHO JÁ É CONTROLADA PELO PRÓPRIO SiteHeader EM REACT) ---
function configurarEfeitosDeScroll(raiz) {
    const barra = raiz.querySelector("[data-progresso]");
    const hero = raiz.querySelector(".grad-hero");
    const secoes = Array.from(raiz.querySelectorAll("main section[id]"));
    const links = Array.from(raiz.querySelectorAll(".grad-subnav-link"));
    const comParalaxe = !preferemMenosMovimento();

    let agendado = false;

    const atualizar = () => {
        agendado = false;
        if (!raiz.isConnected) {
            return;
        }

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
    window.addEventListener("scroll", agendar, { passive: true });
    window.addEventListener("resize", agendar, { passive: true });

    return () => {
        window.removeEventListener("scroll", agendar);
        window.removeEventListener("resize", agendar);
    };
}

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
