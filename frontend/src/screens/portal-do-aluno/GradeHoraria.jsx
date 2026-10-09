"use client";

import { GRADE, HORARIOS } from "./dados";

// --- NOMES DOS DIAS NA ORDEM DE Date.getDay() (DOMINGO = 0), PARA DESTACAR O DIA DE HOJE ---
const DIAS_DA_SEMANA = ["Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"];

// --- TABELA DE AULAS DA SEMANA: O DIA DA SEMANA FICA NA PRIMEIRA COLUNA, À ESQUERDA ---
export default function GradeHoraria() {
    const hoje = DIAS_DA_SEMANA[new Date().getDay()];

    return (
        <section aria-labelledby="tituloGrade">
            <div className="ua-aluno-secao-cabecalho">
                <h2 id="tituloGrade">Meus cursos e horários</h2>
                <span className="muted">Grade da semana</span>
            </div>

            <div className="ua-card ua-aluno-tabela-caixa">
                <table className="ua-aluno-tabela">
                    <caption className="ua-sr-only">Disciplinas por dia da semana e horário</caption>
                    <thead>
                        <tr>
                            <th scope="col">Dia</th>
                            {HORARIOS.map((horario) => (
                                <th key={horario} scope="col">{horario}</th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {GRADE.map((linha) => (
                            <tr key={linha.dia} className={linha.dia === hoje ? "ua-aluno-hoje" : undefined}>
                                <th scope="row">
                                    {linha.dia}
                                    {linha.dia === hoje && <span className="ua-aluno-hoje-rotulo">hoje</span>}
                                </th>
                                {linha.aulas.map((aula, indice) => (
                                    <td key={indice}>
                                        {aula ? (
                                            <div className="ua-aluno-aula">
                                                <strong>{aula.disciplina}</strong>
                                                <small>{aula.sala}</small>
                                            </div>
                                        ) : (
                                            <span className="ua-aluno-sem-aula" aria-label="Sem aula">—</span>
                                        )}
                                    </td>
                                ))}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </section>
    );
}
