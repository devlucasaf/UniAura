"use client";

import { useState }         from "react";
import { Sidebar }          from "primereact/sidebar";
import { ProgressBar }      from "primereact/progressbar";
import { DASHBOARDS }       from "./dados";

// --- BOTÃO DE 3 BARRAS (ANIMADO) QUE ABRE UM PAINEL COM OS DASHBOARDS DOS DIFERENTES TÓPICOS ---
export default function MenuDashboards() {
    const [aberto, setAberto] = useState(false);
    const [topicoId, setTopicoId] = useState(DASHBOARDS[0].id);

    const topico = DASHBOARDS.find((item) => item.id === topicoId);

    return (
        <>
            <button
                type="button"
                className={`ua-hamburguer${aberto ? " ua-hamburguer--aberto" : ""}`}
                aria-label="Abrir dashboards"
                aria-expanded={aberto}
                onClick={() => setAberto(true)}
            >
                <span className="ua-hamburguer__barra"></span>
                <span className="ua-hamburguer__barra"></span>
                <span className="ua-hamburguer__barra"></span>
            </button>

            <Sidebar
                visible={aberto}
                position="left"
                onHide={() => setAberto(false)}
                header={<h2 className="ua-aluno-sidebar-titulo">Meus dashboards</h2>}
                className="ua-aluno-sidebar"
            >
                <div className="ua-aluno-topicos" role="tablist" aria-label="Tópicos do dashboard">
                    {DASHBOARDS.map((item) => (
                        <button
                            key={item.id}
                            type="button"
                            role="tab"
                            aria-selected={item.id === topicoId}
                            className="ua-aluno-topico"
                            onClick={() => setTopicoId(item.id)}
                        >
                            <i className={item.icone} aria-hidden="true" />
                            {item.titulo}
                        </button>
                    ))}
                </div>

                <div className="ua-aluno-indicadores" role="tabpanel" key={topico.id}>
                    {topico.indicadores.map((indicador) => (
                        <div key={indicador.rotulo} className="ua-card ua-indicador">
                            <span className="ua-indicador__valor">{indicador.valor}</span>
                            <span className="ua-indicador__rotulo">{indicador.rotulo}</span>
                            {indicador.progresso !== undefined && (
                                <ProgressBar value={indicador.progresso} showValue={false} style={{ height: "6px" }} />
                            )}
                        </div>
                    ))}
                </div>
            </Sidebar>
        </>
    );
}
