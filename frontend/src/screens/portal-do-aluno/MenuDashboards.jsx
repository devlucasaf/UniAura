"use client";

import { useEffect, useState }  from "react";
import Link                     from "next/link";
import { usePathname }          from "next/navigation";
import { Sidebar }              from "primereact/sidebar";
import { ProgressBar }          from "primereact/progressbar";
import { DASHBOARDS }           from "./dados";
import { MENU_ALUNO }           from "./menu";

// --- BOTÃO DE 3 BARRAS QUE ABRE O PAINEL LATERAL ---
export default function MenuDashboards() {
    const pathname = usePathname();
    const [aberto,          setAberto]          = useState(false);
    const [topicoId,        setTopicoId]        = useState(DASHBOARDS[0].id);
    const [gruposAbertos,   setGruposAbertos]   = useState({});

    const topico = DASHBOARDS.find((item) => item.id === topicoId);

    // --- ABRE SOZINHO O GRUPO DA PÁGINA EM QUE O ALUNO ESTÁ ---
    useEffect(() => {
        const grupoAtual = MENU_ALUNO.find((entrada) => entrada.itens && pathname.startsWith(entrada.base));
        if (grupoAtual) {
            setGruposAbertos((atual) => ({ ...atual, [grupoAtual.id]: true }));
        }
    }, [pathname]);

    const alternarGrupo = (id) => setGruposAbertos((atual) => ({ ...atual, [id]: !atual[id] }));

    return (
        <>
            <button type="button" className={`ua-hamburguer${aberto ? " ua-hamburguer--aberto" : ""}`}
                    aria-label="Abrir menu do portal" aria-expanded={aberto} onClick={() => setAberto(true)}>
                <span className="ua-hamburguer__barra"></span>
                <span className="ua-hamburguer__barra"></span>
                <span className="ua-hamburguer__barra"></span>
            </button>

            <Sidebar visible={aberto} position="left" onHide={() => setAberto(false)}
                    header={<h2 className="ua-aluno-sidebar-titulo">Meus dashboards</h2>} className="ua-aluno-sidebar">
                <nav className="ua-nav-aluno" aria-label="Menu do portal do aluno">
                    {MENU_ALUNO.map((entrada) => {
                        // --- LINK DIRETO ---
                        if (!entrada.itens) {
                            return (
                                <Link key={entrada.id} href={entrada.rota} onClick={() => setAberto(false)}
                                        className={`ua-nav-aluno__item${pathname === entrada.rota ? " ua-nav-aluno__item--ativo" : ""}`}>
                                    <i className={entrada.icone} aria-hidden="true" />
                                    <span>{entrada.rotulo}</span>
                                </Link>
                            );
                        }

                        // --- GRUPO: BOTÃO COM SETA PARA BAIXO E SUBITENS QUE ABREM LOGO ABAIXO ---
                        const grupoAberto = !!gruposAbertos[entrada.id];
                        return (
                            <div key={entrada.id} className="ua-nav-aluno__grupo">
                                <button type="button" className="ua-nav-aluno__item ua-nav-aluno__item--grupo"
                                        aria-expanded={grupoAberto} aria-controls={`submenu-${entrada.id}`}
                                        onClick={() => alternarGrupo(entrada.id)}>
                                    <i className={entrada.icone} aria-hidden="true" />
                                    <span>{entrada.rotulo}</span>
                                    <i className={`pi pi-chevron-down ua-nav-aluno__seta${grupoAberto ? " ua-nav-aluno__seta--aberta" : ""}`} aria-hidden="true" />
                                </button>

                                <div id={`submenu-${entrada.id}`} className={`ua-nav-aluno__sub${grupoAberto ? " ua-nav-aluno__sub--aberto" : ""}`}>
                                    <div className="ua-nav-aluno__sub-interno">
                                        {entrada.itens.map((item) => {
                                            const rota = `${entrada.base}/${item.slug}`;
                                            return (
                                                <Link key={item.slug} href={rota} onClick={() => setAberto(false)}
                                                        tabIndex={grupoAberto ? 0 : -1}
                                                        className={`ua-nav-aluno__subitem${pathname === rota ? " ua-nav-aluno__item--ativo" : ""}`}>
                                                    <i className={item.icone} aria-hidden="true" />
                                                    <span>{item.rotulo}</span>
                                                </Link>
                                            );
                                        })}
                                    </div>
                                </div>
                            </div>
                        );
                    })}
                </nav>

                <h3 className="ua-aluno-resumo-titulo">Resumo</h3>
                <div className="ua-aluno-topicos" role="tablist" aria-label="Tópicos do resumo">
                    {DASHBOARDS.map((item) => (
                        <button key={item.id} type="button" role="tab" aria-selected={item.id === topicoId}
                                className="ua-aluno-topico" onClick={() => setTopicoId(item.id)}>
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
                                <ProgressBar
                                    value={indicador.progresso}
                                    showValue={false} style={{ height: "6px" }}
                                />
                            )}
                        </div>
                    ))}
                </div>
            </Sidebar>
        </>
    );
}
