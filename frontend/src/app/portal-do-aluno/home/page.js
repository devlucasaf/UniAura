"use client";

import MolduraAluno from "@/screens/portal-do-aluno/MolduraAluno";
import HomeAluno    from "@/screens/portal-do-aluno/HomeAluno";

// --- PÁGINA INICIAL DO PERFIL DE ALUNO ---
export default function AlunoHomePage() {
    return (
        <MolduraAluno>
            <HomeAluno />
        </MolduraAluno>
    );
}
