const CHAVE_TEMA = "theme";

// --- LÊ O TEMA SALVO (claro por padrão) ---
export function temaSalvo() {
    if (typeof window === "undefined") {
        return "light";
    }
    return localStorage.getItem(CHAVE_TEMA) || "light";
}

// --- APLICA O TEMA NO <html> E GUARDA A ESCOLHA ---
export function aplicarTema(tema) {
    document.documentElement.setAttribute("data-theme", tema);
    localStorage.setItem(CHAVE_TEMA, tema);
}

// --- ALTERNA O TEMA COM UMA ONDA CIRCULAR EXPANSIVA A PARTIR DO BOTÃO (A MESMA DO SITE) ---
// Recebe o botão clicado e o tema atual; chama aoMudar(novoTema) quando o tema é de fato trocado.
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
        aplicarTema(novo);
        aoMudar?.(novo);
    }, 150);
}
