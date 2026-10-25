import { api } from "@/lib/api";

// --- CONSOME GET /pesquisarMinhasMultasPendentes.v1 ---
function pesquisarMinhasMultasPendentes() {
    return api("/uniaura/pesquisarMinhasMultasPendentes.v1");
}

export { pesquisarMinhasMultasPendentes };
