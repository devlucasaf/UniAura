import { api } from "@/lib/api";

// --- CONSOME POST /cancelarReserva.v1 ---
function cancelarReserva(uid) {
    return api("/uniaura/cancelarReserva.v1", { metodo: "POST", corpo: { uid } });
}

export { cancelarReserva };
