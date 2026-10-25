import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarMeusEmprestimos.v1 ---
function pesquisarMeusEmprestimos(parametros = {}) {
    return api(`/uniaura/pesquisarMeusEmprestimos.v1${query(parametros)}`);
}

export { pesquisarMeusEmprestimos };
