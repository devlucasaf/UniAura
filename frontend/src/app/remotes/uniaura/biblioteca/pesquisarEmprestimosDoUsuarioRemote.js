import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarEmprestimosDoUsuario.v1 ---
function pesquisarEmprestimosDoUsuario(usuarioUid, parametros = {}) {
    return api(`/uniaura/pesquisarEmprestimosDoUsuario.v1${query({ usuarioUid, ...parametros })}`);
}

export { pesquisarEmprestimosDoUsuario };
