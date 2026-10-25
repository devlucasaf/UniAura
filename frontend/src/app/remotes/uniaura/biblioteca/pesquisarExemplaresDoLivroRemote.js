import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarExemplaresDoLivro.v1 ---
function pesquisarExemplaresDoLivro(livroUid, parametros = {}) {
    return api(`/uniaura/pesquisarExemplaresDoLivro.v1${query({ livroUid, ...parametros })}`);
}

export { pesquisarExemplaresDoLivro };
