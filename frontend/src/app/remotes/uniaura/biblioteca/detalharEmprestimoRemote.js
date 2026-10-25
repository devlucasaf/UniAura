import { api, query } from "@/lib/api";

// --- CONSOME GET /detalharEmprestimo.v1 ---
function detalharEmprestimo(uid) {
    return api(`/uniaura/detalharEmprestimo.v1${query({ uid })}`);
}

export { detalharEmprestimo };
