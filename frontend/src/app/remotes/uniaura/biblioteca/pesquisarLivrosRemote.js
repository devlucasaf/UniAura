import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarLivros.v1 ---
function pesquisarLivros(parametros = {}) {
    return api(`/uniaura/pesquisarLivros.v1${query(parametros)}`);
}

export { pesquisarLivros };
