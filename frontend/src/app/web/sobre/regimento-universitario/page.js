"use client";

import { useRouter } from "next/navigation";
import Link          from "next/link";
import { Button }    from "primereact/button";

// --- TÍTULOS DO REGIMENTO UNIVERSITÁRIO COM O RESUMO DO QUE CADA UM TRATA ---
const TITULOS = [
    {
        titulo: "Da organização da universidade",
        texto: "Define a estrutura administrativa e acadêmica, os órgãos colegiados e as competências de cada instância.",
    },
    {
        titulo: "Do ensino e da matrícula",
        texto: "Regras de ingresso, matrícula, trancamento, aproveitamento de estudos e integralização curricular.",
    },
    {
        titulo: "Da avaliação e da frequência",
        texto: "Critérios de avaliação, frequência mínima exigida e procedimentos para segunda chamada e revisão de notas.",
    },
    {
        titulo: "Do corpo discente",
        texto: "Direitos e deveres dos alunos, representação estudantil e normas de conduta.",
    },
    {
        titulo: "Da pesquisa, extensão e estágio",
        texto: "Diretrizes para projetos de pesquisa, atividades de extensão, estágios e trabalho de conclusão de curso.",
    },
    {
        titulo: "Disposições gerais",
        texto: "Regras de convivência, uso das instalações e casos omissos, decididos pelos órgãos competentes.",
    },
];

// --- COMPONENTE DA PÁGINA DO REGIMENTO UNIVERSITÁRIO ---
export default function RegimentoUniversitarioPage() {
    const router = useRouter();

    return (
        <>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/web/home">Início</Link>
                    <span aria-hidden="true">›</span>
                    <span>Sobre</span>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">Regimento Universitário</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Sobre nós</span>
                    <h1>Regimento Universitário</h1>
                    <p className="site-pagina-hero-lead">
                        As normas e diretrizes que organizam a vida acadêmica, com transparência para toda a comunidade.
                    </p>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container">
                    <div className="site-section-head">
                        <span className="site-eyebrow">Conteúdo</span>
                        <h2>O que o regimento trata</h2>
                    </div>

                    <div className="site-cards">
                        {TITULOS.map((item) => (
                            <article key={item.titulo} className="site-card ua-card">
                                <h3>{item.titulo}</h3>
                                <p className="muted">{item.texto}</p>
                            </article>
                        ))}
                    </div>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container site-cta-final">
                    <h2>Precisa de uma cópia do regimento?</h2>
                    <p className="muted">Solicite o documento completo pela secretaria acadêmica.</p>
                    <Button label="Solicitar cópia" size="large" onClick={() => router.push("/web/contato")} />
                </div>
            </section>
        </>
    );
}
