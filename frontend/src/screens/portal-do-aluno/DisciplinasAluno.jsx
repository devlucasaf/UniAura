"use client";

import { useEffect, useState }          from "react";
import Link                             from "next/link";
import { Button }                       from "primereact/button";
import { Dialog }                       from "primereact/dialog";
import { notificar }                    from "@/lib/notificar";
import { pesquisarDisciplinasDoAluno }  from "@/app/remotes/uniaura/academico/pesquisarDisciplinasDoAlunoRemote";

const TURNOS = { 
    MANHA: "Matutino", 
    TARDE: "Vespertino", 
    NOITE: "Noturno" 
};

const LIMITE_FALTAS = 15;

// --- PERÍODO LETIVO DO SEMESTRE k DO CURSO ---
const periodoDe = (semestre, atual) => {
    const hoje = new Date();
    const indiceHoje = hoje.getFullYear() * 2 + (hoje.getMonth() < 6 ? 0 : 1);
    const indice = indiceHoje - (atual - semestre);

    return { ano: Math.floor(indice / 2), parte: (indice % 2) + 1 };
};

const rotuloDoPeriodo = ({ ano, parte }) => `${parte}º semestre de ${ano}`;
const datasDoPeriodo  = ({ ano, parte }) => (parte === 1 ? `01/02/${ano} a 15/07/${ano}` : `01/08/${ano} a 18/12/${ano}`);

// --- DISCIPLINAS EM TRILHA ---
export default function DisciplinasAluno() {
    const [curso,       setCurso]       = useState(null);
    const [estado,      setEstado]      = useState("carregando");
    const [mensagem,    setMensagem]    = useState("");
    const [escolhido,   setEscolhido]   = useState(null);
    const [detalhe,     setDetalhe]     = useState(null);

    // --- BUSCA NO BACKEND A MATRIZ DO CURSO DO ALUNO LOGADO ---
    useEffect(() => {
        pesquisarDisciplinasDoAluno()
            .then((dados) => { setCurso(dados); setEstado("pronto"); })
            .catch((erro) => { setMensagem(erro?.message ?? ""); setEstado("erro"); });
    }, []);

    const total = curso?.matriz.length ?? 1;
    const atual = Math.min(curso?.semestreAtual ?? 1, total);
    const selecionado = escolhido ?? atual;

    const semestre = curso?.matriz[selecionado - 1];
    const periodo = curso ? periodoDe(selecionado, curso.semestreAtual ?? 1) : null;
    const emAndamento = selecionado === atual;
    const turno = TURNOS[curso?.turno] || "Matutino";
    const totalDisciplinas = curso?.matriz.reduce((acumulado, itemDaMatriz) => acumulado + itemDaMatriz.disciplinas.length, 0) ?? 0;
    const horas = curso?.cargaHoraria ? Math.round(curso.cargaHoraria / totalDisciplinas / 5) * 5 : 75;

    // --- FALTAS E MENÇÕES SÃO LANÇADAS PELO PROFESSOR; ATÉ LÁ NÃO HÁ NENHUM REGISTRO ---
    const resultado = () => ({ faltas: 0, mp: null, mf: null });

    const dadosDasDisciplinas = semestre?.disciplinas.map((nome) => ({ nome, ...resultado() })) ?? [];
    const faltasTotais = dadosDasDisciplinas.reduce((acumulado, dadosDaDisciplina) => acumulado + dadosDaDisciplina.faltas, 0);
    const progresso = Math.round((atual / total) * 100);

    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-saudacao">
                <h1>Disciplinas</h1>
                <nav className="ua-aluno-migalhas" aria-label="Você está aqui">
                    <Link href="/portal-do-aluno/home">Home</Link>
                    <span aria-hidden="true">/</span>
                    <span aria-current="page">Disciplinas</span>
                </nav>
            </div>

            {estado === "carregando" && <p className="muted">Carregando seu curso...</p>}

            {estado === "erro" && (
                <div className="ua-card">
                    <p>{mensagem || "Não foi possível carregar os dados do seu curso. Tente novamente."}</p>
                </div>
            )}

            {curso && (
                <>
                    {/* --- TRILHA DO CURSO --- */}
                    <section className="ua-trilha" aria-labelledby="tituloTrilha">
                        <div className="ua-trilha__topo">
                            <div>
                                <span className="ua-trilha__curso">{curso.nivel} · {curso.modalidade}</span>
                                <h2 id="tituloTrilha">{curso.titulo}</h2>
                            </div>
                            <div className="ua-trilha__numeros">
                                <span>
                                    <b>{progresso}%</b> do curso
                                </span>
                                <span>
                                    <b>{atual}</b> de {total} semestres
                                </span>
                            </div>
                        </div>

                        <ol className="ua-trilha__etapas" role="tablist" aria-label="Semestres">
                            {curso.matriz.map((itemDaMatriz) => {
                                const numero = itemDaMatriz.semestre;
                                const situacao = numero < atual ? "feito" : numero === atual ? "atual" : "futuro";
                                return (
                                    <li key={numero} className={`ua-trilha__etapa ${situacao}`} style={{ "--i": numero }}>
                                        <button type="button" role="tab" aria-selected={numero === selecionado} aria-label={`${numero}º semestre${situacao === "futuro" ? " (ainda não cursado)" : ""}`}
                                                disabled={situacao === "futuro"} className={numero === selecionado ? "ua-trilha__botao escolhida" : "ua-trilha__botao"} onClick={() => setEscolhido(numero)}>
                                            {situacao === "feito" ? <i className="pi pi-check" aria-hidden="true" /> : numero}
                                        </button>
                                    </li>
                                );
                            })}
                        </ol>
                    </section>

                    {/* --- RESUMO DO SEMESTRE ESCOLHIDO --- */}
                    <section className="ua-resumo-sem" aria-label="Resumo do semestre">
                        <div className="ua-resumo-sem__titulo">
                            <h2>{semestre.titulo.split("·")[1]?.trim() || semestre.titulo}</h2>
                            <span>{selecionado}º semestre · {rotuloDoPeriodo(periodo)} · {datasDoPeriodo(periodo)}</span>
                        </div>
                        <div className="ua-resumo-sem__fichas">
                            <span>
                                <b>{semestre.disciplinas.length}</b> disciplinas
                            </span>
                            <span>
                                <b>{semestre.disciplinas.length * horas}</b> horas
                            </span>
                            <span>
                                <b>{faltasTotais}</b> faltas
                            </span>
                            <span>{turno}</span>
                            {emAndamento && <span className="ua-resumo-sem__atual">Em andamento</span>}
                        </div>
                    </section>

                    {/* --- CARTÕES DAS DISCIPLINAS --- */}
                    <ul className="ua-cartoes" key={selecionado}>
                        {dadosDasDisciplinas.map((dadosDaDisciplina, indice) => {
                            const porcentagem = Math.min(100, Math.round((dadosDaDisciplina.faltas / LIMITE_FALTAS) * 100));
                            const nivel = dadosDaDisciplina.faltas >= 10 ? "alerta" : dadosDaDisciplina.faltas >= 6 ? "atencao" : "ok";
                            return (
                                <li key={dadosDaDisciplina.nome} className="ua-cartao" style={{ "--i": indice }}>
                                    <div className="ua-cartao__cabeca">
                                        <span className="ua-cartao__numero" aria-hidden="true">{String(indice + 1).padStart(2, "0")}</span>
                                        <h3>{dadosDaDisciplina.nome}</h3>
                                    </div>

                                    <div className={`ua-faltas-barra ${nivel}`} style={{ "--i": indice }}>
                                        <span className="ua-faltas-barra__texto">
                                            <span>Faltas</span>
                                            <span><b>{dadosDaDisciplina.faltas}</b> de {LIMITE_FALTAS}</span>
                                        </span>
                                        <div className="ua-faltas-barra__trilho" role="progressbar" aria-valuemin={0} aria-valuemax={LIMITE_FALTAS}
                                                aria-valuenow={dadosDaDisciplina.faltas} aria-label={`${dadosDaDisciplina.faltas} faltas de ${LIMITE_FALTAS} permitidas`}>
                                            <div className="ua-faltas-barra__preenchimento" style={{ "--pct": `${porcentagem}%`, "--i": indice }} />
                                        </div>
                                    </div>

                                    <dl className="ua-cartao__notas">
                                        <div>
                                            <dt>Menção parcial</dt>
                                            <dd className={dadosDaDisciplina.mp ? undefined : "vazio"}>{dadosDaDisciplina.mp ?? "—"}</dd>
                                        </div>
                                        <div>
                                            <dt>Final</dt>
                                            <dd className={dadosDaDisciplina.mf ? undefined : "vazio"}>{dadosDaDisciplina.mf ?? "—"}</dd>
                                        </div>
                                    </dl>

                                    <div className="ua-cartao__rodape">
                                        <small>{horas} h · {turno}</small>
                                        <span>
                                            <Button
                                                type="button"
                                                icon="pi pi-eye"
                                                rounded
                                                text
                                                aria-label={`Detalhes de ${dadosDaDisciplina.nome}`}
                                                tooltip="Detalhes"
                                                tooltipOptions={{ position: "top" }}
                                                onClick={() => setDetalhe(dadosDaDisciplina)}
                                            />
                                            <Button
                                                type="button"
                                                icon="pi pi-video"
                                                rounded
                                                text
                                                aria-label={`Sala online de ${dadosDaDisciplina.nome}`}
                                                tooltip="Sala online"
                                                tooltipOptions={{ position: "top" }}
                                                onClick={() => notificar("A sala online estará disponível em breve.", "info")}
                                            />
                                        </span>
                                    </div>
                                </li>
                            );
                        })}
                    </ul>

                    <p className="muted ua-cartoes__nota">
                        Barra de faltas: verde até 5, amarela de 6 a 9 e vermelha a partir de 10 (limite de {LIMITE_FALTAS}).
                        Faltas e menções serão lançadas pelo professor de cada disciplina.
                    </p>
                </>
            )}

            <Dialog header={detalhe?.nome} visible={Boolean(detalhe)} onHide={() => setDetalhe(null)}
                    style={{ width: "min(26rem, 92vw)" }} draggable={false} dismissableMask>
                {detalhe && (
                    <ul className="ua-disc__detalhe">
                        <li>
                            <span>Curso</span>
                            <strong>{curso?.titulo}</strong>
                        </li>
                        <li>
                            <span>Semestre</span>
                            <strong>{selecionado}º ({rotuloDoPeriodo(periodo)})</strong>
                        </li>
                        <li>
                            <span>Carga horária</span>
                            <strong>{horas} horas</strong>
                        </li>
                        <li>
                            <span>Faltas</span>
                            <strong>{detalhe.faltas} de {LIMITE_FALTAS}</strong>
                        </li>
                        <li>
                            <span>Menção parcial</span>
                            <strong>{detalhe.mp ?? "Sem lançamentos"}</strong>
                        </li>
                        <li>
                            <span>Menção final</span>
                            <strong>{detalhe.mf ?? "—"}</strong>
                        </li>
                    </ul>
                )}
            </Dialog>
        </div>
    );
}
