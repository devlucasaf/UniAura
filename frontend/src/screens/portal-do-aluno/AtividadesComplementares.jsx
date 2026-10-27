"use client";

import { useEffect, useState }  from "react";
import Link                     from "next/link";
import { Button }               from "primereact/button";
import { Dialog }               from "primereact/dialog";
import { Dropdown }             from "primereact/dropdown";
import { InputNumber }          from "primereact/inputnumber";
import { InputText }            from "primereact/inputtext";
import { obterUsuario }         from "@/lib/auth";
import { notificar }            from "@/lib/notificar";

const HORAS_TOTAIS = 140;

// --- CATEGORIAS DE ATIVIDADE COMPLEMENTAR ---
const CATEGORIAS = [
    {
        value: "Ensino",
        label: "Ensino (monitoria, cursos)"
    },
    {
        value: "Pesquisa",
        label: "Pesquisa (iniciação científica)"
    },
    {
        value: "Extensão",
        label: "Extensão (projetos e ações)"
    },
    {
        value: "Eventos",
        label: "Eventos (palestras, congressos)"
    },
    {
        value: "Cultura",
        label: "Cultura e arte"
    },
    {
        value: "Voluntariado",
        label: "Voluntariado"
    }
];

const RAIO = 78;
const CIRCUNFERENCIA = 2 * Math.PI * RAIO;

const FORMULARIO_VAZIO = { descricao: "", categoria: "Ensino", horas: 10 };

// --- ATIVIDADES COMPLEMENTARES ---
export default function AtividadesComplementares() {
    const [chave,      setChave]      = useState(null);
    const [atividades, setAtividades] = useState([]);
    const [aberto,     setAberto]     = useState(false);
    const [formulario, setFormulario] = useState(FORMULARIO_VAZIO);

    // --- AS ATIVIDADES FICAM GUARDADAS NESTE NAVEGADOR, POR ALUNO, ATÉ EXISTIR O ENVIO REAL AO BACKEND ---
    useEffect(() => {
        const nova = `atividades-complementares-${obterUsuario()?.uid ?? "aluno"}`;
        setChave(nova);

        try {
            setAtividades(JSON.parse(localStorage.getItem(nova) || "[]"));
        } catch {
            setAtividades([]);
        }
    }, []);

    // --- GRAVA A LISTA NOVA NA TELA E NO NAVEGADOR ---
    const gravar = (lista) => {
        setAtividades(lista);
        localStorage.setItem(chave, JSON.stringify(lista));
    };

    const registrar = (evento) => {
        evento.preventDefault();

        if (!formulario.descricao.trim()) {
            notificar("Descreva a atividade realizada.", "warning");
            return;
        }

        if (!formulario.horas || formulario.horas < 1) {
            notificar("Informe a quantidade de horas.", "warning");
            return;
        }

        gravar([...atividades, { id: Date.now(), ...formulario, descricao: formulario.descricao.trim(), data: new Date().toISOString() }]);
        setFormulario(FORMULARIO_VAZIO);
        setAberto(false);
        notificar("Atividade registrada.", "success");
    };

    const remover = (id) => gravar(atividades.filter((atividade) => atividade.id !== id));

    const concluidas = atividades.reduce((soma, atividade) => soma + atividade.horas, 0);
    const validas = Math.min(concluidas, HORAS_TOTAIS);
    const faltam = HORAS_TOTAIS - validas;
    const percentual = Math.round((validas / HORAS_TOTAIS) * 100);

    const porCategoria = CATEGORIAS.map((categoria) => ({
        ...categoria,
        horas: atividades.filter((atividade) => atividade.categoria === categoria.value)
            .reduce((soma, atividade) => soma + atividade.horas, 0)
    }));
    const maiorCategoria = Math.max(1, ...porCategoria.map((categoria) => categoria.horas));

    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-saudacao">
                <h1>Atividades complementares</h1>
                <nav className="ua-aluno-migalhas" aria-label="Você está aqui">
                    <Link href="/portal-do-aluno/home">Home</Link>
                    <span aria-hidden="true">/</span>
                    <span aria-current="page">Atividades complementares</span>
                </nav>
            </div>

            {/* --- GRÁFICO DE PROGRESSO --- */}
            <section className="ua-card ua-complementares__resumo" aria-labelledby="tituloProgresso">
                <div className="ua-complementares__grafico">
                    <svg viewBox="0 0 200 200" role="img"
                            aria-label={`${validas} de ${HORAS_TOTAIS} horas concluídas, faltam ${faltam} horas`}>
                        <circle className="ua-complementares__trilho" cx="100" cy="100" r={RAIO} />
                        <circle
                            className="ua-complementares__barra"
                            cx="100"
                            cy="100"
                            r={RAIO}
                            strokeDasharray={CIRCUNFERENCIA}
                            strokeDashoffset={CIRCUNFERENCIA * (1 - validas / HORAS_TOTAIS)}
                        />
                    </svg>
                    <div className="ua-complementares__centro">
                        <strong>{percentual}%</strong>
                        <span>concluído</span>
                    </div>
                </div>

                <div className="ua-complementares__numeros">
                    <h2 id="tituloProgresso">Seu progresso</h2>
                    <p className="muted">O seu curso exige {HORAS_TOTAIS} horas de atividades complementares.</p>

                    <dl>
                        <div>
                            <dt>Concluídas</dt>
                            <dd>{validas} h</dd>
                        </div>
                        <div className="falta">
                            <dt>Faltam</dt>
                            <dd>{faltam} h</dd>
                        </div>
                        <div>
                            <dt>Total do curso</dt>
                            <dd>{HORAS_TOTAIS} h</dd>
                        </div>
                    </dl>

                    {faltam === 0 && <p className="ua-complementares__parabens">Você completou todas as horas exigidas.</p>}

                    <Button
                        type="button"
                        label="Registrar atividade"
                        icon="pi pi-plus"
                        onClick={() => setAberto(true)}
                    />
                </div>
            </section>

            <div className="ua-aluno-colunas">
                {/* --- HORAS POR CATEGORIA --- */}
                <section className="ua-card" aria-labelledby="tituloCategorias">
                    <h2 id="tituloCategorias" className="ua-complementares__titulo">Horas por categoria</h2>
                    <ul className="ua-complementares__categorias">
                        {porCategoria.map((categoria) => (
                            <li key={categoria.value}>
                                <span className="rotulo">{categoria.value}</span>
                                <div className="trilho" aria-hidden="true">
                                    <div className="preenchimento" style={{ width: `${(categoria.horas / maiorCategoria) * 100}%` }} />
                                </div>
                                <span className="valor">{categoria.horas} h</span>
                            </li>
                        ))}
                    </ul>
                </section>

                {/* --- ATIVIDADES REGISTRADAS --- */}
                <section className="ua-card" aria-labelledby="tituloRegistradas">
                    <h2 id="tituloRegistradas" className="ua-complementares__titulo">Atividades registradas</h2>

                    {atividades.length === 0 && (
                        <p className="muted">Nenhuma atividade registrada ainda. Use o botão "Registrar atividade" para começar.</p>
                    )}

                    <ul className="ua-complementares__lista">
                        {[...atividades].reverse().map((atividade) => (
                            <li key={atividade.id}>
                                <div>
                                    <strong>{atividade.descricao}</strong>
                                    <small>{atividade.categoria} · {new Date(atividade.data).toLocaleDateString("pt-BR")}</small>
                                </div>
                                <span className="horas">{atividade.horas} h</span>
                                <Button
                                    type="button"
                                    icon="pi pi-trash"
                                    rounded
                                    text
                                    severity="danger"
                                    aria-label={`Remover ${atividade.descricao}`}
                                    onClick={() => remover(atividade.id)}
                                />
                            </li>
                        ))}
                    </ul>

                    <p className="muted ua-complementares__nota">
                        As horas registradas aqui ficam salvas neste navegador. A validação pela secretaria será feita em uma próxima etapa.
                    </p>
                </section>
            </div>

            <Dialog header="Registrar atividade" visible={aberto} onHide={() => setAberto(false)}
                    style={{ width: "min(28rem, 92vw)" }} draggable={false} dismissableMask>
                <form className="ua-form-login" noValidate onSubmit={registrar}>
                    <div className="field">
                        <label htmlFor="atividadeDescricao">Descrição</label>
                        <InputText
                            id="atividadeDescricao"
                            maxLength={150}
                            value={formulario.descricao}
                            onChange={(evento) => setFormulario({ ...formulario, descricao: evento.target.value })}
                        />
                    </div>

                    <div className="field">
                        <label htmlFor="atividadeCategoria">Categoria</label>
                        <Dropdown
                            inputId="atividadeCategoria"
                            options={CATEGORIAS}
                            value={formulario.categoria}
                            onChange={(evento) => setFormulario({ ...formulario, categoria: evento.value })}
                        />
                    </div>

                    <div className="field">
                        <label htmlFor="atividadeHoras">Horas</label>
                        <InputNumber
                            inputId="atividadeHoras"
                            min={1}
                            max={HORAS_TOTAIS}
                            suffix=" h"
                            value={formulario.horas}
                            onValueChange={(evento) => setFormulario({ ...formulario, horas: evento.value })}
                        />
                    </div>

                    <Button type="submit" label="Salvar" icon="pi pi-check" />
                </form>
            </Dialog>
        </div>
    );
}
