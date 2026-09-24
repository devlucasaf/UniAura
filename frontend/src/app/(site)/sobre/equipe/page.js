"use client";

import { useState } from "react";
import Link from "next/link";
import SiteChrome from "@/components/site/SiteChrome";
import EQUIPE from "@/data/equipe.json";

const FILTROS = [
    { valor: "todos", rotulo: "Todos" },
    { valor: "direcao", rotulo: "Direção" },
    { valor: "coordenacao", rotulo: "Coordenação" },
    { valor: "professor", rotulo: "Professores" },
    { valor: "apoio", rotulo: "Apoio e Limpeza" }
];

// --- RETORNA AS INICIAIS DE UM NOME ---
function obterIniciais(nome) {
    const partes = nome.trim().split(" ").filter((p) => p.length > 0);
    if (partes.length === 0) {
        return "?";
    }

    if (partes.length === 1) {
        return partes[0][0].toUpperCase();
    }
    return (partes[0][0] + partes[partes.length - 1][0]).toUpperCase();
}

export default function EquipePage() {
    const [filtro, setFiltro] = useState("todos");

    const lista = filtro === "todos" ? EQUIPE : EQUIPE.filter((p) => p.categoria === filtro);
    const contador = lista.length === 1
        ? "1 profissional encontrado"
        : `${lista.length} profissionais encontrados`;

    return (
        <SiteChrome>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/">Início</Link>
                    <span aria-hidden="true">›</span>
                    <span>Sobre</span>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">Nossa Equipe</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Quem faz o Áurea</span>
                    <h1>Nossa Equipe</h1>
                    <p className="site-pagina-hero-lead">
                        Mais de 120 profissionais dedicados a fazer do Colégio Áurea um lugar de aprendizado,
                        acolhimento e transformação.
                    </p>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container">
                    <div className="site-equipe-filtros" role="tablist" aria-label="Filtrar equipe por profissão">
                        {FILTROS.map((item) => (
                            <button
                                key={item.valor}
                                className={`site-filtro-btn${filtro === item.valor ? " ativo" : ""}`}
                                type="button"
                                role="tab"
                                aria-selected={filtro === item.valor}
                                onClick={() => setFiltro(item.valor)}
                            >
                                {item.rotulo}
                            </button>
                        ))}
                    </div>

                    <p className="site-equipe-contador muted" aria-live="polite">{contador}</p>

                    <div className="site-equipe-grid" role="tabpanel">
                        {lista.map((pessoa) => (
                            <article key={pessoa.nome} className="site-equipe-card" data-categoria={pessoa.categoria}>
                                <div className="site-equipe-avatar" aria-hidden="true">
                                    {obterIniciais(pessoa.nome)}
                                </div>
                                <div className="site-equipe-info">
                                    <h3>{pessoa.nome}</h3>
                                    <span>{pessoa.cargo}</span>
                                </div>
                            </article>
                        ))}
                    </div>

                    {lista.length === 0 && (
                        <p className="site-equipe-vazio">
                            Nenhum profissional encontrado nesta categoria.
                        </p>
                    )}
                </div>
            </section>
        </SiteChrome>
    );
}
