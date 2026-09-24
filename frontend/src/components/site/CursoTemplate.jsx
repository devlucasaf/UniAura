"use client";

import { useEfeitosDePagina } from "@/hooks/useEfeitosDePagina";

// --- ESTRUTURA COMPARTILHADA DE UMA PAGINA DE CURSO (SOBRE, FORMACAO, MATRIZ, CARREIRA, CTA) ---
export default function CursoTemplate({ curso }) {
    const raizRef = useEfeitosDePagina();

    return (
        <div className="grad-page" ref={raizRef}>
            <nav className="grad-subnav" aria-label="Seções do curso">
                <div className="grad-container grad-subnav-inner">
                    <span className="grad-subnav-curso">
                        <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor"
                             strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                            <polyline points="16 18 22 12 16 6"></polyline>
                            <polyline points="8 6 2 12 8 18"></polyline>
                        </svg>
                        {curso.subnavLabel}
                    </span>

                    <div className="grad-subnav-links">
                        <a className="grad-subnav-link" href="#grad-sobre">O curso</a>
                        <a className="grad-subnav-link" href="#grad-formacao">Formação</a>
                        <a className="grad-subnav-link" href="#grad-matriz">Matriz curricular</a>
                        <a className="grad-subnav-link" href="#grad-carreira">Carreira</a>
                    </div>
                </div>

                <span className="grad-progresso" aria-hidden="true">
                    <span className="grad-progresso-barra" data-progresso></span>
                </span>
            </nav>

            <main>
                <section className="grad-hero">
                    <span className="grad-hero-brilho grad-hero-brilho-a" aria-hidden="true"></span>
                    <span className="grad-hero-brilho grad-hero-brilho-b" aria-hidden="true"></span>

                    <div className="grad-container grad-hero-grid">
                        <div className="grad-hero-texto">
                            <span className="grad-eyebrow" data-entrada style={{ "--atraso": "60ms" }}>
                                <svg viewBox="0 0 24 24" width="15" height="15" fill="none" stroke="currentColor"
                                     strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                    <polyline points="16 18 22 12 16 6"></polyline>
                                    <polyline points="8 6 2 12 8 18"></polyline>
                                </svg>
                                {curso.eyebrow}
                            </span>

                            <h1 data-entrada style={{ "--atraso": "160ms" }}>{curso.tituloPrincipal} <span>{curso.tituloDestaque}</span></h1>

                            <p data-entrada style={{ "--atraso": "260ms" }}>{curso.descricao}</p>

                            <div className="grad-actions" data-entrada style={{ "--atraso": "360ms" }}>
                                {curso.acoes.map((acao) => (
                                    <a
                                        key={acao.texto}
                                        className={`grad-btn ${acao === curso.acoes[0] ? "grad-btn-primary" : "grad-btn-outline"}`}
                                        href={acao.href}
                                    >
                                        {acao.texto}
                                    </a>
                                ))}
                            </div>
                        </div>

                        <div className="grad-hero-card" data-entrada data-inclinar style={{ "--atraso": "460ms" }} aria-label="Destaque do curso">
                            <div className="grad-janela">
                                <div className="grad-janela-topo">
                                    <span className="grad-janela-ponto"></span>
                                    <span className="grad-janela-ponto"></span>
                                    <span className="grad-janela-ponto"></span>
                                    <span className="grad-janela-titulo">{curso.codigoArquivo}</span>
                                </div>

                                <div className="grad-janela-corpo">
                                    {curso.codigoLinhas.map((linha, indice) => (
                                        <span key={indice} className="grad-janela-linha" dangerouslySetInnerHTML={{ __html: linha }} />
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section className="grad-section" id="grad-sobre">
                    <div className="grad-container">
                        <div className="grad-stats" data-revelar>
                            {curso.stats.map((stat) => (
                                <div className="grad-stat" key={stat.label}>
                                    {stat.contador
                                        ? <strong data-contador={stat.valor} data-sufixo={stat.sufixo}>0</strong>
                                        : <strong>{stat.valor}</strong>}
                                    <span>{stat.label}</span>
                                </div>
                            ))}
                        </div>
                    </div>
                </section>

                <section className="grad-section grad-section-alt" id="grad-formacao">
                    <div className="grad-container">
                        <div className="grad-section-title" data-revelar>
                            <span className="grad-eyebrow">{curso.formacaoEyebrow}</span>
                            <h2>{curso.formacaoTitulo}</h2>
                            <p>{curso.formacaoTexto}</p>
                        </div>

                        <div className="grad-grid-3">
                            {curso.formacaoCards.map((card, indice) => (
                                <article className="grad-card" data-revelar style={{ "--atraso": `${indice * 90}ms` }} key={card.titulo}>
                                    <div className="grad-card-icon">
                                        <svg viewBox="0 0 24 24" width="23" height="23" fill="none" stroke="currentColor"
                                             strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"
                                             dangerouslySetInnerHTML={{ __html: card.iconeSvg }} />
                                    </div>
                                    <h3>{card.titulo}</h3>
                                    <p>{card.texto}</p>
                                </article>
                            ))}
                        </div>
                    </div>
                </section>

                <section className="grad-section" id="grad-matriz">
                    <div className="grad-container">
                        <div className="grad-section-title" data-revelar>
                            <span className="grad-eyebrow">{curso.matrizEyebrow}</span>
                            <h2>{curso.matrizTitulo}</h2>
                            <p>{curso.matrizTexto}</p>
                        </div>

                        <div className="grad-curriculum">
                            {curso.semestres.map((semestre, indice) => (
                                <div
                                    className={`grad-semester${indice === 0 ? " aberto" : ""}`}
                                    data-revelar
                                    style={{ "--atraso": `${indice * 60}ms` }}
                                    key={semestre.titulo}
                                >
                                    <button type="button" aria-expanded={indice === 0}>
                                        <span>{semestre.titulo}</span>
                                        <span className="grad-semester-seta" aria-hidden="true">
                                            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor"
                                                 strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
                                                <polyline points="6 9 12 15 18 9"></polyline>
                                            </svg>
                                        </span>
                                    </button>
                                    <div className="grad-semester-corpo">
                                        <div className="grad-semester-list">
                                            {semestre.disciplinas.map((disciplina) => (
                                                <span className="grad-subject" key={disciplina}>{disciplina}</span>
                                            ))}
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </section>

                <section className="grad-section grad-section-alt" id="grad-carreira">
                    <div className="grad-container">
                        <div className="grad-career">
                            <div data-revelar>
                                <span className="grad-eyebrow">{curso.carreiraEyebrow}</span>
                                <h2 className="grad-career-titulo">{curso.carreiraTitulo}</h2>
                                <p className="muted">{curso.carreiraTexto}</p>
                            </div>

                            <div className="grad-card" data-revelar style={{ "--atraso": "120ms" }}>
                                <strong>{curso.carreiraCardTitulo}</strong>
                                <div className="grad-career-list">
                                    {curso.carreiraItens.map((item, indice) => (
                                        <div className="grad-career-item" style={{ "--atraso": `${200 + indice * 50}ms` }} key={item}>{item}</div>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section className="grad-section">
                    <div className="grad-container">
                        <div className="grad-cta" data-revelar>
                            <div>
                                <h2>{curso.ctaTitulo}</h2>
                                <p>{curso.ctaTexto}</p>
                            </div>
                            <a className="grad-btn grad-cta-btn" href="/matriculas">
                                Fazer minha matrícula
                                <span className="grad-cta-seta" aria-hidden="true">→</span>
                            </a>
                        </div>
                    </div>
                </section>
            </main>
        </div>
    );
}
