"use client";

import { Tag }                  from "primereact/tag";
import { ProgressBar }          from "primereact/progressbar";
import { DASHBOARDS }           from "./dados";
import useDisciplinasDoSemestre from "./useDisciplinasDoSemestre";

// --- ESTATÍSTICAS DO ALUNO COM INDICADORES, NOTAS E PRESENÇA POR DISCIPLINA ---
export default function PerfilAluno() {
    const { disciplinas } = useDisciplinasDoSemestre();
    const resumo = DASHBOARDS.find((item) => item.id === "academico");

    // --- A QUANTIDADE DE DISCIPLINAS VEM DO CURSO DO ALUNO; NOTAS E FALTAS SÃO LANÇADAS PELO PROFESSOR ---
    const indicadores = resumo.indicadores.map((indicador) => (
        indicador.rotulo === "Disciplinas em curso" && disciplinas.length > 0
            ? { ...indicador, valor: String(disciplinas.length) }
            : indicador
    ));

    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-secao-cabecalho">
                <h1>Meu perfil</h1>
                <span className="muted">Notas e faltas serão lançadas pelo professor de cada disciplina</span>
            </div>

            <div className="ua-aluno-indicadores ua-aluno-indicadores--linha">
                {indicadores.map((indicador) => (
                    <div key={indicador.rotulo} className="ua-card ua-indicador">
                        <span className="ua-indicador__valor">{indicador.valor}</span>
                        <span className="ua-indicador__rotulo">{indicador.rotulo}</span>
                        {indicador.progresso !== undefined && (
                            <ProgressBar 
                                value={indicador.progresso} 
                                showValue={false} 
                                style={{ height: "6px" }} 
                            />
                        )}
                    </div>
                ))}
            </div>

            <section aria-labelledby="tituloNotas">
                <div className="ua-aluno-secao-cabecalho">
                    <h2 id="tituloNotas">Notas</h2>
                </div>
                <div className="ua-card ua-aluno-tabela-caixa">
                    <table className="ua-aluno-tabela">
                        <thead>
                            <tr>
                                <th scope="col">Disciplina</th>
                                <th scope="col">1ª nota</th>
                                <th scope="col">2ª nota</th>
                                <th scope="col">Média</th>
                                <th scope="col">Situação</th>
                            </tr>
                        </thead>
                        <tbody>
                            {disciplinas.map((disciplina) => (
                                <tr key={disciplina}>
                                    <th scope="row">{disciplina}</th>
                                    <td>—</td>
                                    <td>—</td>
                                    <td>—</td>
                                    <td>
                                        <Tag value="Aguardando lançamento" severity="info" rounded />
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </section>

            <section aria-labelledby="tituloPresenca">
                <div className="ua-aluno-secao-cabecalho">
                    <h2 id="tituloPresenca">Presença</h2>
                </div>
                <div className="ua-card ua-aluno-tabela-caixa">
                    <table className="ua-aluno-tabela">
                        <thead>
                            <tr>
                                <th scope="col">Disciplina</th>
                                <th scope="col">Aulas</th>
                                <th scope="col">Faltas</th>
                                <th scope="col">Frequência</th>
                            </tr>
                        </thead>
                        <tbody>
                            {disciplinas.map((disciplina) => (
                                <tr key={disciplina}>
                                    <th scope="row">{disciplina}</th>
                                    <td>—</td>
                                    <td>—</td>
                                    <td>—</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    );
}
