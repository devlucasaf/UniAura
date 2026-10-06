"use client";

import { useRouter } from "next/navigation";
import Link          from "next/link";
import { Button }    from "primereact/button";

// --- LINHA DO TEMPO DOS RECONHECIMENTOS E PRÊMIOS DA UNIVERSIDADE ---
const RECONHECIMENTOS = [
    {
        ano: "2026",
        titulo: "Destaque em inovação",
        texto: "Pelo segundo ano consecutivo, a UniAura aparece entre as instituições de destaque nacional em pesquisa aplicada e parcerias com o setor produtivo.",
    },
    {
        ano: "2024",
        titulo: "30 anos de tradição",
        texto: "Celebramos três décadas de história com 98% de aprovação dos nossos alunos em universidades públicas e privadas.",
    },
    {
        ano: "2020",
        titulo: "Educação híbrida",
        texto: "Reconhecimento pela implantação de uma plataforma completa de ensino híbrido durante a pandemia.",
    },
];

// --- INDICADORES EM DESTAQUE NO TOPO DA PÁGINA ---
const INDICADORES = [
    {
        valor: "1º",
        rotulo: "Ranking de inovação (2º ano seguido)"
    },
    {
        valor: "98%",
        rotulo: "Aprovação em universidades"
    },
    {
        valor: "35+",
        rotulo: "Anos de excelência acadêmica"
    },
];

// --- COMPONENTE DA PÁGINA DE PRÊMIOS E RANKINGS DA UNIVERSIDADE ---
export default function PremiosERankingsPage() {
    const router = useRouter();

    return (
        <>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/web/home">Início</Link>
                    <span aria-hidden="true">›</span>
                    <span>Sobre</span>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">Prêmios e Rankings</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Sobre nós</span>
                    <h1>Prêmios e Rankings</h1>
                    <p className="site-pagina-hero-lead">
                        Reconhecimentos nacionais que refletem o trabalho de alunos, professores e colaboradores.
                    </p>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container">
                    <div className="site-numeros">
                        {INDICADORES.map((indicador) => (
                            <div key={indicador.rotulo} className="site-numero-card ua-card">
                                <strong>{indicador.valor}</strong>
                                <span>{indicador.rotulo}</span>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            <section className="site-section site-section-alt">
                <div className="site-container">
                    <div className="site-section-head">
                        <span className="site-eyebrow">Linha do tempo</span>
                        <h2>Nossas conquistas</h2>
                    </div>

                    <ol className="site-timeline">
                        {RECONHECIMENTOS.map((item) => (
                            <li key={item.ano + item.titulo} className="site-timeline-item">
                                <div className="site-timeline-marco">{item.ano}</div>
                                <div className="site-timeline-conteudo">
                                    <h3>{item.titulo}</h3>
                                    <p>{item.texto}</p>
                                </div>
                            </li>
                        ))}
                    </ol>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container site-cta-final">
                    <h2>Faça parte dessa história</h2>
                    <p className="muted">Conheça os cursos e participe de uma comunidade reconhecida.</p>
                    <Button label="Conheça os cursos" size="large" onClick={() => router.push("/web/graduacao/cursos")} />
                </div>
            </section>
        </>
    );
}
