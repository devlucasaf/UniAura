import { api, corpoMultipart } from "@/lib/api";

// --- CONSOME POST /cadastrarLivro.v1 (MULTIPART: dados + capa) ---
function cadastrarLivro(dados, capa) {
    return api("/uniaura/cadastrarLivro.v1", { metodo: "POST", corpo: corpoMultipart(dados, capa, "capa"), multipart: true });
}

export { cadastrarLivro };
