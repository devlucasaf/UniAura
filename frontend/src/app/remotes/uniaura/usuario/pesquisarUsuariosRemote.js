import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarUsuarios.v1 ---
function pesquisarUsuarios(parametros = {}) {
    return api(`/uniaura/pesquisarUsuarios.v1${query(parametros)}`);
}

export { pesquisarUsuarios };
