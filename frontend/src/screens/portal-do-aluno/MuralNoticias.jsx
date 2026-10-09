"use client";

import { Tag }      from "primereact/tag";
import { NOTICIAS } from "./dados";

// --- FORMATA A DATA DE PUBLICAÇÃO DA NOTÍCIA ---
const formatarData = (data) => data.toLocaleDateString("pt-BR", { day: "2-digit", month: "short" });

// --- MURAL COM AS NOTÍCIAS E AVISOS PARA O ALUNO ---
export default function MuralNoticias() {
    return (
        <section aria-labelledby="tituloMural">
            <div className="ua-aluno-secao-cabecalho">
                <h2 id="tituloMural">Mural de notícias</h2>
                <span className="muted">Avisos da universidade</span>
            </div>

            <div className="ua-aluno-mural">
                {NOTICIAS.map((noticia) => (
                    <article key={noticia.id} className="ua-card ua-aluno-noticia">
                        <div className="ua-aluno-noticia__topo">
                            <Tag value={noticia.categoria} rounded />
                            <time dateTime={noticia.data.toISOString()}>{formatarData(noticia.data)}</time>
                        </div>
                        <h3>{noticia.titulo}</h3>
                        <p className="muted">{noticia.resumo}</p>
                    </article>
                ))}
            </div>
        </section>
    );
}
