"use client";

import { useState }     from "react";
import Link             from "next/link";
import { SelectButton } from "primereact/selectbutton";

// --- NOTÍCIAS PUBLICADAS ---
const NOTICIAS = [
    {
        id: "vestibular-2027",
        categoria: "Vestibular",
        data: "2026-10-01",
        titulo: "Inscrições abertas para o vestibular da UniAura",
        resumo: "Mais de 2.000 vagas em 30 cursos de graduação. Provas em agosto e novembro. Inscreva-se e dê o primeiro passo para sua carreira."
    },
    {
        id: "semana-ciencia-tecnologia-2026",
        categoria: "Evento",
        data: "2026-09-18",
        titulo: "Semana de Ciência e Tecnologia 2026",
        resumo: "Palestras, workshops e feira de projetos com alunos de graduação e pós. Participe e compartilhe conhecimento."
    },
    {
        id: "ranking-inovacao",
        categoria: "Conquista",
        data: "2026-08-05",
        titulo: "UniAura no topo do ranking de inovação",
        resumo: "Pelo segundo ano consecutivo, nossa universidade é destaque nacional em pesquisa aplicada e parcerias com o setor produtivo."
    }
];

// --- CATEGORIAS DAS NOTÍCIAS ---
const CATEGORIAS = [
    "Todas",
    "Vestibular",
    "Evento",
    "Conquista"
];

// --- FORMATA A DATA DE PUBLICAÇÃO ---
function formatarData(iso) {
    return new Date(`${iso}T12:00:00`).toLocaleDateString("pt-BR", { day: "2-digit", month: "long", year: "numeric" });
}

// --- COMPONENTE DA PÁGINA DE NOTÍCIAS ---
export default function NoticiasPage() {
    const [categoria, setCategoria] = useState("Todas");

    const filtradas = NOTICIAS
        .filter((noticia) => categoria === "Todas" || noticia.categoria === categoria)
        .sort((a, b) => b.data.localeCompare(a.data));

    return (
        <>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/web/home">Início</Link>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">Notícias</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Comunicação</span>
                    <h1>Notícias</h1>
                    <p className="site-pagina-hero-lead">
                        Vestibular, eventos e conquistas da comunidade UniAura, em um só lugar.
                    </p>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container">
                    <div className="ua-noticias-filtro">
                        <SelectButton
                            value={categoria}
                            options={CATEGORIAS}
                            onChange={(e) => e.value && setCategoria(e.value)}
                            allowEmpty={false}
                            aria-label="Filtrar notícias por categoria"
                        />
                    </div>

                    {filtradas.length === 0 ? (
                        <p className="muted">Nenhuma notícia nesta categoria.</p>
                    ) : (
                        <div className="ua-noticias-grade">
                            {filtradas.map((noticia) => (
                                <article key={noticia.id} className="ua-card ua-noticia">
                                    <span className="ua-noticia__categoria">{noticia.categoria}</span>
                                    <h2>{noticia.titulo}</h2>
                                    <p className="muted">{noticia.resumo}</p>
                                    <footer className="ua-noticia__rodape">
                                        <time dateTime={noticia.data}>{formatarData(noticia.data)}</time>
                                    </footer>
                                </article>
                            ))}
                        </div>
                    )}
                </div>
            </section>
        </>
    );
}
