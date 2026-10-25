import { api } from "@/lib/api";

// --- CONSOME POST /reservarLivro.v1 ---
function reservarLivro(livroUid) {
    return api("/uniaura/reservarLivro.v1", { metodo: "POST", corpo: { livroUid } });
}

export { reservarLivro };
