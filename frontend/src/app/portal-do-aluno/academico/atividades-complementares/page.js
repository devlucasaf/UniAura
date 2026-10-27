"use client";

import MolduraAluno             from "@/screens/portal-do-aluno/MolduraAluno";
import AtividadesComplementares from "@/screens/portal-do-aluno/AtividadesComplementares";

// --- PÁGINA "ACADÊMICO > ATIVIDADES COMPLEMENTARES" DO ALUNO ---
export default function AlunoAtividadesComplementaresPage() {
    return (
        <MolduraAluno titulo="Atividades complementares">
            <AtividadesComplementares />
        </MolduraAluno>
    );
}
