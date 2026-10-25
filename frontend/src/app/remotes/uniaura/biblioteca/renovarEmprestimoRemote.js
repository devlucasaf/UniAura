import { api } from "@/lib/api";

// --- CONSOME POST /renovarEmprestimo.v1 ---
function renovarEmprestimo(uid) {
    return api("/uniaura/renovarEmprestimo.v1", { metodo: "POST", corpo: { uid } });
}

export { renovarEmprestimo };
