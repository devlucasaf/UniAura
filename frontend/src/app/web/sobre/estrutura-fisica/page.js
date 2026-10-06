"use client";

import { useRouter } from "next/navigation";
import Link          from "next/link";
import { Button }    from "primereact/button";


const ESPACOS = [
    {
        titulo: "Salas de aula",
        texto: "Salas climatizadas, com recursos audiovisuais e acessibilidade em todos os andares.",
    },
    {
        titulo: "Laboratórios",
        texto: "Laboratórios de informática, engenharia, química e biologia, com equipamentos atualizados para a prática desde o primeiro semestre.",
    },
    {
        titulo: "Biblioteca",
        texto: "Acervo físico e digital, salas de estudo em grupo e acesso ao portal de periódicos.",
    },
    {
        titulo: "Espaços de convivência",
        texto: "Áreas de estudo livre, cantina, praça de convivência e quadra poliesportiva coberta.",
    },
];

// --- COMPONENTE DA PÁGINA ESTRUTURA FÍSICA DA UNIVERSIDADE ---
export default function EstruturaFisicaPage() {
    const router = useRouter();

    return (
        <>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/web/home">Início</Link>
                    <span aria-hidden="true">›</span>
                    <span>Sobre</span>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">Estrutura Física</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Sobre nós</span>
                    <h1>Estrutura Física</h1>
                    <p className="site-pagina-hero-lead">
                        Salas, laboratórios e biblioteca pensados para o aprendizado prático e a convivência acadêmica.
                    </p>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container">
                    <div className="site-cards">
                        {ESPACOS.map((espaco) => (
                            <article key={espaco.titulo} className="site-card ua-card">
                                <h2>{espaco.titulo}</h2>
                                <p className="muted">{espaco.texto}</p>
                            </article>
                        ))}
                    </div>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container site-cta-final">
                    <h2>Quer conhecer o campus?</h2>
                    <p className="muted">Agende uma visita guiada e veja a estrutura de perto.</p>
                    <Button label="Agendar visita" size="large" onClick={() => router.push("/web/contato")} />
                </div>
            </section>
        </>
    );
}
