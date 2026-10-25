import { api } from "@/lib/api";

// --- CONSOME GET /consultarConfiguracaoDaBiblioteca.v1 ---
function consultarConfiguracaoDaBiblioteca() {
    return api("/uniaura/consultarConfiguracaoDaBiblioteca.v1");
}

export { consultarConfiguracaoDaBiblioteca };
