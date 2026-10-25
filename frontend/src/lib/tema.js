const CHAVE_TEMA = "theme";

// --- O VISUAL NOVO VALE SÓ NAS TELAS INTERNAS DO PORTAL DO ALUNO ---
export function ehPortalDoAluno(caminho = typeof window !== "undefined" ? window.location.pathname : "") {
    return caminho.includes("/portal-do-aluno/") && !/\/portal-do-aluno\/(login|cadastro)\/?$/.test(caminho);
}

// --- LÊ O TEMA SALVO; NA PRIMEIRA VISITA, O PORTAL DO ALUNO SEGUE O SISTEMA OPERACIONAL E O RESTO COMEÇA CLARO ---
export function temaSalvo() {
    if (typeof window === "undefined") {
        return "light";
    }

    const salvo = localStorage.getItem(CHAVE_TEMA);
    if (salvo === "light" || salvo === "dark") {
        return salvo;
    }
    return ehPortalDoAluno() && window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
}

// --- APLICA O TEMA NO <html> E GUARDA A ESCOLHA ---
export function aplicarTema(tema, { salvar = true, animar = false } = {}) {
    const raiz = document.documentElement;

    if (animar) {
        raiz.classList.add("ua-trocando-tema");
        setTimeout(() => raiz.classList.remove("ua-trocando-tema"), 500);
    }

    raiz.setAttribute("data-theme", tema);
    if (salvar) {
        localStorage.setItem(CHAVE_TEMA, tema);
    }
}

// --- ALTERNA O TEMA COM UMA ONDA CIRCULAR EXPANSIVA A PARTIR DO BOTÃO ---
export function alternarTemaComOnda(botao, temaAtual, aoMudar) {
    const novo = temaAtual === "dark" ? "light" : "dark";
    const retangulo = botao.getBoundingClientRect();
    const onda = document.createElement("span");

    onda.className = "site-tema-onda";
    onda.style.left = `${retangulo.left + retangulo.width / 2}px`;
    onda.style.top = `${retangulo.top + retangulo.height / 2}px`;
    onda.dataset.tema = novo;
    document.body.appendChild(onda);
    onda.addEventListener("animationend", () => onda.remove(), { once: true });

    botao.classList.add("girando");
    setTimeout(() => botao.classList.remove("girando"), 600);

    setTimeout(() => {
        aplicarTema(novo, { animar: true });
        aoMudar?.(novo);
    }, 150);
}
