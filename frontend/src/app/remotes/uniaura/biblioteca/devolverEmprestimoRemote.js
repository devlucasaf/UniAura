import { api } from "@/lib/api";

// --- CONSOME POST /devolverEmprestimo.v1 ---
function devolverEmprestimo(uid) {
    return api("/uniaura/devolverEmprestimo.v1", { metodo: "POST", corpo: { uid } });
}

export { devolverEmprestimo };
