import { api } from "@/lib/api";

// --- CONSOME POST /removerLivro.v1 ---
function removerLivro(uid) {
    return api("/uniaura/removerLivro.v1", { metodo: "POST", corpo: { uid } });
}

export { removerLivro };
