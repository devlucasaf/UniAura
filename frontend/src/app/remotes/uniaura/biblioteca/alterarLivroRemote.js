import { api, corpoMultipart } from "@/lib/api";

// --- CONSOME POST /alterarLivro.v1 (MULTIPART: dados com o uid + capa) ---
function alterarLivro(uid, dados, capa) {
    return api("/uniaura/alterarLivro.v1", { metodo: "POST", corpo: corpoMultipart({ uid, ...dados }, capa, "capa"), multipart: true });
}

export { alterarLivro };
