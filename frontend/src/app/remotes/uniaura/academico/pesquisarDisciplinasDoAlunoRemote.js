import { api } from "@/lib/api";

// --- CONSOME GET /pesquisarDisciplinasDoAluno.v1 ---
function pesquisarDisciplinasDoAluno() {
    return api("/uniaura/pesquisarDisciplinasDoAluno.v1");
}

export { pesquisarDisciplinasDoAluno };
