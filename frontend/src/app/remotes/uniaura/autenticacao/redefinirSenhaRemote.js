import { api } from "@/lib/api";

// --- CONSOME POST /redefinirSenha.v1 ---
function redefinirSenha(dados) {
    return api("/uniaura/redefinirSenha.v1", { metodo: "POST", corpo: dados });
}

export { redefinirSenha };