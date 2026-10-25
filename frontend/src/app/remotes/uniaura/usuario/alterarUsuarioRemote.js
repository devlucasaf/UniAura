import { api } from "@/lib/api";

// --- CONSOME POST /alterarUsuario.v1 ---
function alterarUsuario(dados) {
    return api("/uniaura/alterarUsuario.v1", { metodo: "POST", corpo: dados });
}

export { alterarUsuario };
