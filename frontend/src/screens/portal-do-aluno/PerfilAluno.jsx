"use client";

import { Tag }                      from "primereact/tag";
import { ProgressBar }              from "primereact/progressbar";
import { DASHBOARDS, DESEMPENHO }   from "./dados";

// --- MÉDIA DAS DUAS NOTAS, FREQUÊNCIA EM % E FORMATAÇÃO COM VÍRGULA ---
const media = (disciplina) => (disciplina.nota1 + disciplina.nota2) / 2;
const frequencia = (disciplina) => Math.round(((disciplina.aulas - disciplina.faltas) / disciplina.aulas) * 100);
const formatar = (numero) => numero.toFixed(1).replace(".", ",");

// --- ESTATÍSTICAS DO ALUNO COM INDICADORES, NOTAS E PRESENÇA POR DISCIPLINA ---
export default function PerfilAluno() {
    const resumo = DASHBOARDS.find((item) => item.id === "academico");

    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-secao-cabecalho">
                <h1>Meu perfil</h1>
                <span className="muted">Notas e presença do semestre (dados de exemplo)</span>
            </div>

            <div className="ua-aluno-indicadores ua-aluno-indicadores--linha">
                {resumo.indicadores.map((indicador) => (
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
                            {DESEMPENHO.map((disciplina) => (
                                <tr key={disciplina.disciplina}>
                                    <th scope="row">{disciplina.disciplina}</th>
                                    <td>{formatar(disciplina.nota1)}</td>
                                    <td>{formatar(disciplina.nota2)}</td>
                                    <td>
                                        <strong>{formatar(media(disciplina))}</strong>
                                    </td>
                                    <td>
                                        <Tag
                                            value={media(disciplina) >= 7 ? "Aprovado" : "Em recuperação"}
                                            severity={media(disciplina) >= 7 ? "success" : "warning"}
                                            rounded
                                        />
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
                            {DESEMPENHO.map((disciplina) => (
                                <tr key={disciplina.disciplina}>
                                    <th scope="row">{disciplina.disciplina}</th>
                                    <td>{disciplina.aulas}</td>
                                    <td>{disciplina.faltas}</td>
                                    <td className="ua-aluno-freq">
                                        <ProgressBar 
                                            value={frequencia(disciplina)} 
                                            showValue={false} 
                                            style={{ height: "8px" }} 
                                        />
                                        <span>{frequencia(disciplina)}%</span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    );
}
