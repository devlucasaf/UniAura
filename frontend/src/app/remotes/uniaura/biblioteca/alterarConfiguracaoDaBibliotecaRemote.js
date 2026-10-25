import { api } from "@/lib/api";

// --- CONSOME POST /alterarConfiguracaoDaBiblioteca.v1 ---
function alterarConfiguracaoDaBiblioteca(dados) {
    return api("/uniaura/alterarConfiguracaoDaBiblioteca.v1", { metodo: "POST", corpo: dados });
}

export { alterarConfiguracaoDaBiblioteca };
