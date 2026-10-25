"use client";

import PainelAluno      from "./PainelAluno";
import { NOTICIAS }     from "./dados";

// --- FORMATA A DATA DE PUBLICAÇÃO DA NOTÍCIA ---
const formatarData = (data) => data.toLocaleDateString("pt-BR");

// --- TIMELINE ---
export default function MuralNoticias() {
    return (
        <PainelAluno id="tituloMural" titulo="Timeline" icone="pi pi-list">
            <ul className="ua-timeline">
                {NOTICIAS.map((noticia) => (
                    <li key={noticia.id} className="ua-timeline__linha">
                        <span className="ua-timeline__origem">{noticia.categoria}</span>
                        <span className="ua-timeline__texto">
                            <strong>{noticia.titulo}</strong>
                            <small>{noticia.resumo}</small>
                        </span>
                        <i className="pi pi-envelope ua-timeline__icone" aria-hidden="true" />
                        <time className="ua-timeline__data" dateTime={noticia.data.toISOString()}>{formatarData(noticia.data)}</time>
                    </li>
                ))}
            </ul>
        </PainelAluno>
    );
}
