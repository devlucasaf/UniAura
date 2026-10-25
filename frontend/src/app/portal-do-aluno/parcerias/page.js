"use client";

import MolduraAluno   from "@/screens/portal-do-aluno/MolduraAluno";
import ParceriasAluno from "@/screens/portal-do-aluno/ParceriasAluno";

// --- PÁGINA "PARCERIAS" DO ALUNO ---
export default function AlunoParceriasPage() {
    return (
        <MolduraAluno titulo="Parcerias">
            <ParceriasAluno />
        </MolduraAluno>
    );
}
