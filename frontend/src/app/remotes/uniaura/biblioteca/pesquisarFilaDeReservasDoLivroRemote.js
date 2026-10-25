import { api, query } from "@/lib/api";

// --- CONSOME GET /pesquisarFilaDeReservasDoLivro.v1 ---
function pesquisarFilaDeReservasDoLivro(livroUid) {
    return api(`/uniaura/pesquisarFilaDeReservasDoLivro.v1${query({ livroUid })}`);
}

export { pesquisarFilaDeReservasDoLivro };
