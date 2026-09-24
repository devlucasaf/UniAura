"use client";

import { useState } from "react";
import Link from "next/link";
import SiteChrome from "@/components/site/SiteChrome";
import { tituloPagina, subtituloPagina, filtros, cards } from "@/data/cursosCatalogo";

export default function CursosPage() {
    const [filtroAtivo, setFiltroAtivo] = useState("todos");

    const visiveis = cards.filter((card) => filtroAtivo === "todos" || card.area === filtroAtivo);

    return (
        <SiteChrome>
            <div className="cursos-page grad-page">
                <div className="grad-container">
                    <header className="cursos-header">
                        <h1>{tituloPagina}</h1>
                        <p>{subtituloPagina}</p>
                    </header>

                    <div className="cursos-filtros" role="tablist" aria-label="Filtrar cursos por área de atuação">
                        {filtros.map((filtro) => (
                            <button
                                key={filtro.valor}
                                className={`site-filtro-btn${filtroAtivo === filtro.valor ? " ativo" : ""}`}
                                type="button"
                                role="tab"
                                aria-selected={filtroAtivo === filtro.valor}
                                onClick={() => setFiltroAtivo(filtro.valor)}
                            >
                                {filtro.label}
                            </button>
                        ))}
                    </div>

                    <p className="cursos-contador muted" aria-live="polite">
                        {visiveis.length === 1 ? "1 curso encontrado" : `${visiveis.length} cursos encontrados`}
                    </p>

                    <div className="cursos-grid" role="tabpanel">
                        {visiveis.map((card) => {
                            const conteudo = (
                                <>
                                    <div className="curso-card-icon" aria-hidden="true">
                                        <svg viewBox="0 0 24 24" width="24" height="24" fill="none" stroke="currentColor"
                                             strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"
                                             dangerouslySetInnerHTML={{ __html: card.iconeSvg }} />
                                    </div>
                                    <h2>{card.titulo}</h2>
                                    <p>{card.descricao}</p>
                                    <span className="curso-card-tag">{card.tag}</span>
                                </>
                            );

                            const estilo = { "--curso-cor": card.cor };

                            return card.slug ? (
                                <Link key={card.titulo} href={`/graduacao/${card.slug}`} className="curso-card" style={estilo}>
                                    {conteudo}
                                </Link>
                            ) : (
                                <span key={card.titulo} className="curso-card" style={estilo} aria-disabled="true">
                                    {conteudo}
                                </span>
                            );
                        })}
                    </div>

                    {visiveis.length === 0 && (
                        <p className="cursos-vazio">Nenhum curso encontrado nesta área.</p>
                    )}
                </div>
            </div>
        </SiteChrome>
    );
}
