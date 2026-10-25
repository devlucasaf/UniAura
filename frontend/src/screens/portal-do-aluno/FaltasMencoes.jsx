"use client";

import Link                     from "next/link";
import PainelAluno              from "./PainelAluno";
import useDisciplinasDoSemestre from "./useDisciplinasDoSemestre";

// --- PAINEL "FALTAS E MENÇÕES" ---
export default function FaltasMencoes() {
    const { carregando, disciplinas } = useDisciplinasDoSemestre();

    return (
        <PainelAluno
            id="tituloFaltas"
            titulo="Faltas e menções"
            icone="pi pi-pencil"
            acao={
                <Link href="/portal-do-aluno/academico/disciplinas" className="ua-painel__seta" aria-label="Ir para as disciplinas" title="Ver disciplinas">
                    <i className="pi pi-arrow-right" aria-hidden="true" />
                </Link>
            }
        >
            <table className="ua-aluno-tabela ua-faltas">
                <thead>
                    <tr>
                        <th scope="col">Disciplinas</th>
                        <th scope="col">Faltas</th>
                        <th scope="col">Menção</th>
                    </tr>
                </thead>
                <tbody>
                    {disciplinas.map((disciplina) => (
                        <tr key={disciplina}>
                            <th scope="row">{disciplina}</th>
                            <td>—</td>
                            <td>—</td>
                        </tr>
                    ))}
                    {!carregando && disciplinas.length === 0 && (
                        <tr>
                            <td colSpan={3}>Nenhuma disciplina encontrada para o seu curso.</td>
                        </tr>
                    )}
                </tbody>
            </table>
            <p className="muted">Faltas e menções serão lançadas pelo professor de cada disciplina.</p>
        </PainelAluno>
    );
}
