import { api } from "@/lib/api";

// --- CONSOME POST /cadastrarExemplar.v1 ---
function cadastrarExemplar(dados) {
    return api("/uniaura/cadastrarExemplar.v1", { metodo: "POST", corpo: dados });
}

export { cadastrarExemplar };
