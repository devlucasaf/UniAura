import { api } from "@/lib/api";

// --- CONSOME GET /consultarMeuPerfilDeAluno.v1 ---
function consultarMeuPerfilDeAluno() {
    return api("/uniaura/consultarMeuPerfilDeAluno.v1");
}

export { consultarMeuPerfilDeAluno };