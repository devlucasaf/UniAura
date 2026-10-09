"use client";

import MolduraAluno from "@/screens/portal-do-aluno/MolduraAluno";
import HomeAluno    from "@/screens/portal-do-aluno/HomeAluno";

// --- PÁGINA INICIAL DO PERFIL DE ALUNO (A TELA FICA EM src/screens/portal-do-aluno) ---
export default function AlunoDashboardPage() {
    return (
        <MolduraAluno>
            <HomeAluno />
        </MolduraAluno>
    );
}
