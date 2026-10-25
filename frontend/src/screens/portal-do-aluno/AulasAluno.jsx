"use client";

import { useMemo, useState }        from "react";
import PainelAluno                  from "./PainelAluno";
import { HORARIOS, montarGrade }    from "./dados";
import useDisciplinasDoSemestre     from "./useDisciplinasDoSemestre";

// --- SEGUNDA A SÁBADO, NA ORDEM DE GRADE ---
const ABREVIADOS = [
    "Seg", 
    "Ter", 
    "Qua", 
    "Qui", 
    "Sex", 
    "Sáb"
];

// --- PAINEL "AULAS" ---
export default function AulasAluno() {
    const { disciplinas } = useDisciplinasDoSemestre();
    const grade = useMemo(() => montarGrade(disciplinas), [disciplinas]);
    const hoje = new Date().getDay();
    const [indice, setIndice] = useState(hoje >= 1 && hoje <= 6 ? hoje - 1 : 0);

    const aulas = grade[indice].aulas
        .map((aula, posicao) => (aula ? { ...aula, horario: HORARIOS[posicao] } : null))
        .filter(Boolean);

    return (
        <PainelAluno id="tituloAulas" titulo="Aulas" icone="pi pi-clock">
            <div className="ua-aulas">
                <div className="ua-aulas__dias" role="tablist" aria-label="Dia da semana">
                    {ABREVIADOS.map((dia, posicao) => (
                        <button key={dia} type="button" role="tab" aria-selected={posicao === indice}
                                className={posicao === indice ? "ativo" : undefined} onClick={() => setIndice(posicao)}>
                            {dia}
                        </button>
                    ))}
                </div>

                <div className="ua-aulas__lista" role="tabpanel">
                    {aulas.length === 0 && <p className="muted">Sem aulas neste dia.</p>}
                    {aulas.map((aula) => (
                        <article key={aula.horario} className="ua-aulas__item">
                            <span className="ua-aulas__horario"><i className="pi pi-clock" aria-hidden="true" /> {aula.horario}</span>
                            <strong>{aula.disciplina}</strong>
                            <span className="ua-aulas__sala">{aula.sala}</span>
                        </article>
                    ))}
                </div>
            </div>
        </PainelAluno>
    );
}
