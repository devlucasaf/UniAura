import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarMultas.v1 ---
function pesquisarMultas(parametros = {}) {
    return api(`/uniaura/pesquisarMultas.v1${query(parametros)}`);
}

export { pesquisarMultas };
