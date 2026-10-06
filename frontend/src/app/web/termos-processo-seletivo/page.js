"use client";

import Link         from "next/link";
import { Button }   from "primereact/button";
import { useRouter } from "next/navigation";

// --- SEÇÕES DOS TERMOS DO PROCESSO SELETIVO COM TÍTULO E TEXTO ---
const SECOES = [
    {
        titulo: "1. Inscrição",
        texto: "A inscrição é feita pelo formulário de matrícula, com dados verdadeiros e completos. Informações falsas ou incompletas podem levar ao cancelamento da inscrição em qualquer fase do processo."
    },
    {
        titulo: "2. Formas de ingresso",
        texto: "O ingresso pode ocorrer por vestibular, nota do ENEM, transferência externa ou nova graduação para quem já possui diploma, conforme as vagas e os editais de cada curso."
    },
    {
        titulo: "3. Documentação",
        texto: "Após a inscrição, o candidato recebe por e-mail as instruções para enviar a documentação exigida. A matrícula só é efetivada com a entrega e a conferência de todos os documentos dentro do prazo."
    },
    {
        titulo: "4. Classificação e vagas",
        texto: "A classificação segue os critérios do edital vigente. A UniAura pode alterar a oferta de turnos, turmas ou vagas conforme a demanda, e o candidato será avisado pelos canais informados na inscrição."
    },
    {
        titulo: "5. Protocolo",
        texto: "Ao concluir a inscrição, o sistema gera um protocolo, que deve ser guardado para acompanhar o andamento e para qualquer atendimento junto à secretaria."
    },
    {
        titulo: "6. Dados pessoais",
        texto: "Os dados informados são usados apenas para o processo seletivo e a matrícula, conforme a política de privacidade da UniAura e a LGPD."
    },
    {
        titulo: "7. Aceite",
        texto: "Ao marcar a opção de aceite no formulário, o candidato declara que leu e concorda com estes termos e com a política de privacidade."
    }
];

// --- COMPONENTE DA PÁGINA DOS TERMOS DO PROCESSO SELETIVO ---
export default function TermosProcessoSeletivoPage() {
    const router = useRouter();

    return (
        <>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/web/home">Início</Link>
                    <span aria-hidden="true">›</span>
                    <Link href="/web/matriculas">Matrículas</Link>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">Termos do processo seletivo</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Transparência</span>
                    <h1>Termos do processo seletivo</h1>
                    <p className="site-pagina-hero-lead">
                        As regras para se inscrever e ingressar na UniAura, de forma clara e transparente.
                    </p>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container">
                    <div className="site-cards">
                        {SECOES.map((secao) => (
                            <article key={secao.titulo} className="site-card ua-card">
                                <h2>{secao.titulo}</h2>
                                <p className="muted">{secao.texto}</p>
                            </article>
                        ))}
                    </div>
                </div>
            </section>

            <section className="site-section">
                <div className="site-container site-cta-final">
                    <h2>Pronto para se inscrever?</h2>
                    <p className="muted">Volte ao formulário e garanta sua vaga. Veja também a <Link href="/web/privacidade" className="ua-link-destaque">política de privacidade</Link>.</p>
                    <Button label="Ir para a matrícula" size="large" onClick={() => router.push("/web/matriculas")} />
                </div>
            </section>
        </>
    );
}
