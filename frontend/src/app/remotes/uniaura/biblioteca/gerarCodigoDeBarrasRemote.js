import { api } from "@/lib/api";

// --- CONSOME GET /gerarCodigoDeBarras.v1 ---
function gerarCodigoDeBarras() {
    return api("/uniaura/gerarCodigoDeBarras.v1");
}

export { gerarCodigoDeBarras };
