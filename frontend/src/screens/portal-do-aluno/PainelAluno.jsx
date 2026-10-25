"use client";

// --- PAINEL PADRÃO DA HOME ---
export default function PainelAluno({ id, titulo, icone, acao, children }) {
    return (
        <section className="ua-painel" aria-labelledby={id}>
            <header className="ua-painel__cab">
                <span className="ua-painel__icone">
                    <i className={icone} aria-hidden="true" />
                </span>
                <h2 id={id}>{titulo}</h2>
                {acao}
            </header>
            <div className="ua-painel__corpo">{children}</div>
        </section>
    );
}
