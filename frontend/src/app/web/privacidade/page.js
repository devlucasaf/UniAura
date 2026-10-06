"use client";

import Link         from "next/link";

// --- SEÇÕES DA POLÍTICA DE PRIVACIDADE COM TÍTULO E TEXTO ---
const SECOES = [
    {
        titulo: "Quais dados coletamos",
        texto: "Nome, e-mail e telefone (quando informado) e o conteúdo da mensagem enviada pelo formulário de contato."
    },
    {
        titulo: "Para que usamos os dados",
        texto: "Exclusivamente para responder à sua solicitação e encaminhá-la ao setor responsável pelo assunto escolhido."
    },
    {
        titulo: "Quem tem acesso",
        texto: "Somente a equipe da UniAura responsável pelo atendimento. Não vendemos nem cedemos seus dados a terceiros para fins comerciais."
    },
    {
        titulo: "Seus direitos",
        texto: "Você pode pedir a confirmação do tratamento, o acesso, a correção ou a exclusão dos seus dados, a qualquer momento, pelo canal de contato."
    },
    {
        titulo: "Autorização",
        texto: "Ao enviar o formulário, você autoriza o contato da UniAura por e-mail ou telefone para tratar da sua solicitação."
    }
];

// --- COMPONENTE DA PÁGINA DE POLÍTICA DE PRIVACIDADE ---
export default function PrivacidadePage() {
    return (
        <>
            <nav className="site-breadcrumb" aria-label="Você está aqui">
                <div className="site-container">
                    <Link href="/web/home">Início</Link>
                    <span aria-hidden="true">›</span>
                    <Link href="/web/contato">Contato</Link>
                    <span aria-hidden="true">›</span>
                    <span className="site-breadcrumb-atual">Política de privacidade</span>
                </div>
            </nav>

            <section className="site-pagina-hero">
                <div className="site-container">
                    <span className="site-eyebrow">Transparência</span>
                    <h1>Política de privacidade</h1>
                    <p className="site-pagina-hero-lead">
                        Como a UniAura trata os dados pessoais recebidos pelo site, em conformidade com a LGPD.
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
        </>
    );
}
