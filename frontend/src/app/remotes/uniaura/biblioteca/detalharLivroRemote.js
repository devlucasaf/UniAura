import { api, query } from "@/lib/api";

// --- CONSOME GET /detalharLivro.v1 ---
function detalharLivro(uid) {
    return api(`/uniaura/detalharLivro.v1${query({ uid })}`);
}

export { detalharLivro };
