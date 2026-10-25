import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarMinhasReservas.v1 ---
function pesquisarMinhasReservas(parametros = {}) {
    return api(`/uniaura/pesquisarMinhasReservas.v1${query(parametros)}`);
}

export { pesquisarMinhasReservas };
