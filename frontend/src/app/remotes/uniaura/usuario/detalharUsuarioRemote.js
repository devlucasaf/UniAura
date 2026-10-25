import { api, query } from "@/lib/api";

// --- CONSOME GET /detalharUsuario.v1 ---
function detalharUsuario(uid) {
    return api(`/uniaura/detalharUsuario.v1${query({ uid })}`);
}

export { detalharUsuario };
