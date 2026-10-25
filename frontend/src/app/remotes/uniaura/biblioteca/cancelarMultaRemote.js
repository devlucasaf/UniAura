import { api } from "@/lib/api";

// --- CONSOME POST /cancelarMulta.v1 ---
function cancelarMulta(uid) {
    return api("/uniaura/cancelarMulta.v1", { metodo: "POST", corpo: { uid } });
}

export { cancelarMulta };
