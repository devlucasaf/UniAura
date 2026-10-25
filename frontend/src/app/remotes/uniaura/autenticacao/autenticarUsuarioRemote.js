import { api } from "@/lib/api";

// --- CONSOME POST /autenticarUsuario.v1 ---
function autenticarUsuario(email, senha) {
    return api("/uniaura/autenticarUsuario.v1", { metodo: "POST", corpo: { email, senha } });
}

export { autenticarUsuario };