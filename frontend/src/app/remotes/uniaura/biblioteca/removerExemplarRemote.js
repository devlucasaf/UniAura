import { api } from "@/lib/api";

// --- CONSOME POST /removerExemplar.v1 ---
function removerExemplar(uid) {
    return api("/uniaura/removerExemplar.v1", { metodo: "POST", corpo: { uid } });
}

export { removerExemplar };
