"use client";

import MolduraAluno from "@/screens/portal-do-aluno/MolduraAluno";
import PerfilAluno  from "@/screens/portal-do-aluno/PerfilAluno";

// --- PÁGINA "MEU PERFIL" DO ALUNO ---
export default function AlunoPerfilPage() {
    return (
        <MolduraAluno titulo="Meu perfil">
            <PerfilAluno />
        </MolduraAluno>
    );
}
