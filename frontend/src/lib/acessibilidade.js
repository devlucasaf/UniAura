// --- PREFERÊNCIAS DE ACESSIBILIDADE, GUARDADAS NO NAVEGADOR E APLICADAS COMO ATRIBUTOS NO <html> ---
const CHAVE = "acessibilidade";

export const PADRAO_ACESSIBILIDADE = {
    contraste: "",
    cursorGrande: false,
    daltonismo: "",
    pausarAnimacoes: false
};

export function acessibilidadeSalva() {
    try {
        return { ...PADRAO_ACESSIBILIDADE, ...JSON.parse(localStorage.getItem(CHAVE) || "{}") };
    } catch {
        return { ...PADRAO_ACESSIBILIDADE };
    }
}

// --- APLICA AS PREFERÊNCIAS NO DOCUMENTO E AS SALVA ---
export function aplicarAcessibilidade(preferencias, salvar = true) {
    const raiz = document.documentElement;
    const definir = (nome, valor) => (valor ? raiz.setAttribute(nome, valor) : raiz.removeAttribute(nome));

    definir("data-contraste", preferencias.contraste);
    definir("data-daltonismo", preferencias.daltonismo);
    definir("data-cursor-grande", preferencias.cursorGrande ? "sim" : "");
    definir("data-sem-animacao", preferencias.pausarAnimacoes ? "sim" : "");

    if (salvar) {
        localStorage.setItem(CHAVE, JSON.stringify(preferencias));
    }
}
