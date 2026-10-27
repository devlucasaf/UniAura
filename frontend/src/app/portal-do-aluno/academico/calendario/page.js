"use client";

import MolduraAluno         from "@/screens/portal-do-aluno/MolduraAluno";
import CalendarioAcademico  from "@/screens/portal-do-aluno/CalendarioAcademico";

// --- PÁGINA "ACADÊMICO > CALENDÁRIO" DO ALUNO ---
export default function AlunoCalendarioPage() {
    return (
        <MolduraAluno titulo="Calendário">
            <CalendarioAcademico />
        </MolduraAluno>
    );
}
