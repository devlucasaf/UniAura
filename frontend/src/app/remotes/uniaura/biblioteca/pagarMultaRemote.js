import { api } from "@/lib/api";

// --- CONSOME POST /pagarMulta.v1 ---
function pagarMulta(uid) {
    return api("/uniaura/pagarMulta.v1", { metodo: "POST", corpo: { uid } });
}

export { pagarMulta };
