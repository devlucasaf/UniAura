import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarEventosAcademicos.v1 ---
function pesquisarEventosAcademicos(parametros = {}) {
    return api(`/uniaura/pesquisarEventosAcademicos.v1${query(parametros)}`);
}

export { pesquisarEventosAcademicos };
