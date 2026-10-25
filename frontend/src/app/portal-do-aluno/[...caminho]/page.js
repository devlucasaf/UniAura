"use client";

import { useParams, notFound }  from "next/navigation";
import MolduraAluno             from "@/screens/portal-do-aluno/MolduraAluno";
import SecaoEmConstrucao        from "@/screens/portal-do-aluno/SecaoEmConstrucao";
import { acharItemDoMenu }      from "@/screens/portal-do-aluno/menu";

// --- PÁGINA GENÉRICA DAS OPÇÕES DO MENU DO ALUNO SEM TELA PRÓPRIA ---
export default function AlunoSecaoPage() {
    const { caminho } = useParams();

    let partesDoCaminho = [caminho];
    if (Array.isArray(caminho)) {
        partesDoCaminho = caminho;
    }

    const item = acharItemDoMenu(partesDoCaminho);

    if (!item) {
        notFound();
    }

    return (
        <MolduraAluno titulo={item.rotulo}>
            <SecaoEmConstrucao item={item} />
        </MolduraAluno>
    );
}
