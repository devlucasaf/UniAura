// --- SISTEMA DE NOTIFICAÇÕES ---
let raizNotificacoes;

function garantirRaizNotificacoes() {
    if (!raizNotificacoes) {
        raizNotificacoes = document.createElement("div");
        raizNotificacoes.className = "toast-container";
        document.body.appendChild(raizNotificacoes);
    }
    return raizNotificacoes;
}

export function notificar(mensagem, tipo = "info", tempo = 3500) {
    if (typeof document === "undefined") {
        return;
    }

    const raiz = garantirRaizNotificacoes();
    const elemento = document.createElement("div");
    elemento.className = `toast toast-${tipo}`;
    elemento.textContent = mensagem;
    raiz.appendChild(elemento);

    requestAnimationFrame(() => elemento.classList.add("show"));
    setTimeout(() => {
        elemento.classList.remove("show");
        setTimeout(() => elemento.remove(), 300);
    }, tempo);
}
