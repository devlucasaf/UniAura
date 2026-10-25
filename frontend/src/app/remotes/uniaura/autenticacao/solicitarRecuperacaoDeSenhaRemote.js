import { api } from "@/lib/api";

// --- CONSOME POST /solicitarRecuperacaoDeSenha.v1 ---
function solicitarRecuperacaoDeSenha(dados) {
    return api("/uniaura/solicitarRecuperacaoDeSenha.v1", { metodo: "POST", corpo: dados });
}

export { solicitarRecuperacaoDeSenha };