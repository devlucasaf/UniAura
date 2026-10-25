import { api } from "@/lib/api";

// --- CONSOME POST /alterarMinhaSenha.v1 ---
function alterarMinhaSenha(senhaAtual, novaSenha) {
    return api("/uniaura/alterarMinhaSenha.v1", { metodo: "POST", corpo: { senhaAtual, novaSenha } });
}

export { alterarMinhaSenha };