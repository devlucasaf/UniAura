"use client";

import MolduraAluno         from "@/screens/portal-do-aluno/MolduraAluno";
import DisciplinasAluno     from "@/screens/portal-do-aluno/DisciplinasAluno";

// --- PÁGINA "ACADÊMICO > DISCIPLINAS" DO ALUNO ---
export default function AlunoDisciplinasPage() {
    return (
        <MolduraAluno titulo="Disciplinas">
            <DisciplinasAluno />
        </MolduraAluno>
    );
}
