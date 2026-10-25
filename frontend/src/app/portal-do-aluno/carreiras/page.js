"use client";

import MolduraAluno     from "@/screens/portal-do-aluno/MolduraAluno";
import CarreirasAluno   from "@/screens/portal-do-aluno/CarreirasAluno";

// --- PÁGINA "CARREIRAS" DO ALUNO ---
export default function AlunoCarreirasPage() {
    return (
        <MolduraAluno titulo="Carreiras">
            <CarreirasAluno />
        </MolduraAluno>
    );
}
