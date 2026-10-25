import { api } from "@/lib/api";

// --- CONSOME POST /alterarMeuPerfilDeAluno.v1 ---
function alterarMeuPerfilDeAluno(dados) {
    return api("/uniaura/alterarMeuPerfilDeAluno.v1", { metodo: "POST", corpo: dados });
}

export { alterarMeuPerfilDeAluno };