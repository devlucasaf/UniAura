import { api } from "@/lib/api";

// --- CONSOME POST /registrarEmprestimo.v1 ---
function registrarEmprestimo(dados) {
    return api("/uniaura/registrarEmprestimo.v1", { metodo: "POST", corpo: dados });
}

export { registrarEmprestimo };
