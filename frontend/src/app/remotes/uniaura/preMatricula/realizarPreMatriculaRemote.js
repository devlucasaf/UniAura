import { api } from "@/lib/api";

// --- CONSOME POST /realizarPreMatricula.v1 ---
function realizarPreMatricula(dados) {
    return api("/uniaura/realizarPreMatricula.v1", { metodo: "POST", corpo: dados });
}

export { realizarPreMatricula };