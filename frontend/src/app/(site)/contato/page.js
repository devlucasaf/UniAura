"use client";

import { useState } from "react";
import SiteChrome from "@/components/site/SiteChrome";
import Dropdown from "@/components/ui/Dropdown";
import { useEfeitosDePagina } from "@/hooks/useEfeitosDePagina";
import { mascararTelefone } from "@/lib/mascaras";

const ASSUNTOS = [
    { value: "MATRICULA", label: "Matrícula e ingresso" },
    { value: "SECRETARIA", label: "Secretaria acadêmica" },
    { value: "FINANCEIRO", label: "Financeiro" },
    { value: "COORDENACAO", label: "Coordenação de curso" },
    { value: "BIBLIOTECA", label: "Biblioteca" },
    { value: "OUVIDORIA", label: "Ouvidoria" },
    { value: "OUTROS", label: "Outros" }
];

const CANAIS = [
    {
        cor: "#e1306c", nome: "Instagram", handle: "@uniaura", href: "https://www.instagram.com/__.fr3it4s.__/",
        externo: true, texto: "O dia a dia do campus, eventos, bastidores e as datas do vestibular.",
        icone: (
            <svg viewBox="0 0 24 24" width="23" height="23" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <rect x="2" y="2" width="20" height="20" rx="5"></rect>
                <circle cx="12" cy="12" r="4"></circle>
                <path d="M17.5 6.5h.01"></path>
            </svg>
        )
    },
    {
        cor: "#0a66c2", nome: "LinkedIn", handle: "/company/uniaura", href: "#", externo: true,
        texto: "Vagas, parcerias com empresas e a trajetória dos nossos egressos.",
        icone: (
            <svg viewBox="0 0 24 24" width="23" height="23" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <path d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-4 0v7h-4v-7a6 6 0 0 1 6-6Z"></path>
                <rect x="2" y="9" width="4" height="12"></rect>
                <circle cx="4" cy="4" r="2"></circle>
            </svg>
        )
    },
    {
        cor: "#1db954", nome: "Spotify", handle: "AuraCast · UniAura", href: "#", externo: true,
        texto: "O AuraCast, nosso podcast com professores, pesquisadores e alunos.",
        icone: (
            <svg viewBox="0 0 24 24" width="23" height="23" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <circle cx="12" cy="12" r="10"></circle>
                <path d="M7 9c3.5-1 7.5-.6 10 1"></path>
                <path d="M7.5 12.5c3-.8 6.3-.5 8.5 1"></path>
                <path d="M8 16c2.4-.6 5-.4 6.8.8"></path>
            </svg>
        )
    },
    {
        cor: "#5865f2", nome: "Discord", handle: "discord.gg/uniaura", href: "#", externo: true,
        texto: "A comunidade dos alunos: grupos de estudo, monitoria e avisos por curso.",
        icone: (
            <svg viewBox="0 0 24 24" width="23" height="23" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <path d="M8 4.5C6 4.8 4.2 5.6 3 6.5 1.6 10 1 13.6 1.3 17.4c1.7 1.3 3.4 2.1 5 2.6l1.1-1.7"></path>
                <path d="M16 4.5c2 .3 3.8 1.1 5 2 1.4 3.5 2 7.1 1.7 10.9-1.7 1.3-3.4 2.1-5 2.6l-1.1-1.7"></path>
                <path d="M7.5 17c3 1.3 6 1.3 9 0"></path>
                <ellipse cx="9" cy="12" rx="1.6" ry="2"></ellipse>
                <ellipse cx="15" cy="12" rx="1.6" ry="2"></ellipse>
            </svg>
        )
    },
    {
        cor: "#1e40af", nome: "E-mail", handle: "contato@uniaura.edu.br", href: "mailto:contato@uniaura.edu.br", externo: false,
        texto: "Para assuntos formais, envio de documentos e solicitações da secretaria.",
        icone: (
            <svg viewBox="0 0 24 24" width="23" height="23" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <rect x="2" y="4" width="20" height="16" rx="2"></rect>
                <path d="m2 7 10 6 10-6"></path>
            </svg>
        )
    },
    {
        cor: "#0f766e", nome: "Telefone", handle: "(74) 3000-1500", href: "tel:+557430001500", externo: false,
        texto: "Atendimento imediato da central, de segunda a sexta, das 08h às 21h.",
        icone: (
            <svg viewBox="0 0 24 24" width="23" height="23" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                <path d="M22 16.9v3a2 2 0 0 1-2.2 2 19.8 19.8 0 0 1-8.6-3.1 19.5 19.5 0 0 1-6-6A19.8 19.8 0 0 1 2.1 4.2 2 2 0 0 1 4.1 2h3a2 2 0 0 1 2 1.7c.1 1 .4 1.9.7 2.8a2 2 0 0 1-.5 2.1L8.1 9.9a16 16 0 0 0 6 6l1.3-1.3a2 2 0 0 1 2.1-.4c.9.3 1.8.6 2.8.7a2 2 0 0 1 1.7 2Z"></path>
            </svg>
        )
    }
];

const SETORES = [
    { nome: "Secretaria Acadêmica", descricao: "Matrícula, histórico, declarações e trancamento", email: "secretaria@uniaura.edu.br" },
    { nome: "Financeiro", descricao: "Mensalidades, boletos, bolsas e negociação", email: "financeiro@uniaura.edu.br" },
    { nome: "Coordenação de Curso", descricao: "Disciplinas, plano de ensino e aproveitamento", email: "coordenacao@uniaura.edu.br" },
    { nome: "Biblioteca", descricao: "Empréstimos, reservas, multas e acervo", email: "biblioteca@uniaura.edu.br" },
    { nome: "Ouvidoria", descricao: "Reclamações, denúncias, sugestões e elogios", email: "ouvidoria@uniaura.edu.br" }
];

export default function ContatoPage() {
    const raizRef = useEfeitosDePagina();
    const [assunto, setAssunto] = useState("");
    const [assuntoInvalido, setAssuntoInvalido] = useState(false);
    const [telefone, setTelefone] = useState("");
    const [sucesso, setSucesso] = useState("");
    const [erro, setErro] = useState("");
    const [enviando, setEnviando] = useState(false);

    const aoEnviar = (evento) => {
        evento.preventDefault();
        const formulario = evento.target;
        setSucesso("");
        setErro("");

        if (!formulario.checkValidity()) {
            formulario.reportValidity();
            setErro("Revise os campos destacados antes de enviar.");
            return;
        }

        if (!assunto) {
            setAssuntoInvalido(true);
            setErro("Escolha o assunto da mensagem.");
            return;
        }
        setAssuntoInvalido(false);

        setEnviando(true);
        // --- AINDA NÃO HÁ ENDPOINT DE CONTATO NO BACKEND: NADA É PERSISTIDO ---
        const primeiroNome = formulario.nome.value.trim().split(" ")[0];
        setTimeout(() => {
            setSucesso(
                `Mensagem enviada, ${primeiroNome}! Nossa equipe responde no e-mail informado em até um dia útil.`
            );
            formulario.reset();
            setAssunto("");
            setTelefone("");
            setEnviando(false);
        }, 300);
    };

    return (
        <SiteChrome>
            <div className="grad-page" ref={raizRef}>
                <main>
                    <section className="grad-hero">
                        <span className="grad-hero-brilho grad-hero-brilho-a" aria-hidden="true"></span>
                        <span className="grad-hero-brilho grad-hero-brilho-b" aria-hidden="true"></span>

                        <div className="grad-container grad-hero-grid">
                            <div className="grad-hero-texto">
                                <span className="grad-eyebrow" data-entrada style={{ "--atraso": "60ms" }}>
                                    <svg viewBox="0 0 24 24" width="15" height="15" fill="none" stroke="currentColor"
                                         strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                        <path d="M4 4h16v12H7l-3 3V4Z"></path>
                                    </svg>
                                    Atendimento
                                </span>

                                <h1 data-entrada style={{ "--atraso": "160ms" }}>Fale com a <span>gente</span></h1>

                                <p data-entrada style={{ "--atraso": "260ms" }}>
                                    Dúvidas sobre matrícula, documentos, disciplinas ou a vida no campus?
                                    Escolha o canal que preferir — respondemos em até um dia útil pelos canais
                                    digitais e na hora pelo telefone.
                                </p>

                                <div className="grad-actions" data-entrada style={{ "--atraso": "360ms" }}>
                                    <a className="grad-btn grad-btn-primary" href="#cc-canais">Ver nossos canais</a>
                                    <a className="grad-btn grad-btn-outline" href="#cc-mensagem">Enviar uma mensagem</a>
                                </div>
                            </div>

                            <div className="grad-hero-card" data-entrada style={{ "--atraso": "460ms" }} aria-label="Endereço e horário de atendimento">
                                <div className="grad-janela">
                                    <div className="grad-janela-topo">
                                        <span className="grad-janela-ponto"></span>
                                        <span className="grad-janela-ponto"></span>
                                        <span className="grad-janela-ponto"></span>
                                        <span className="grad-janela-titulo">atendimento</span>
                                    </div>
                                    <div className="grad-janela-corpo">
                                        <span className="grad-janela-linha"><span className="muted">{"// Campus Xique-Xique"}</span></span>
                                        <span className="grad-janela-linha"> </span>
                                        <span className="grad-janela-linha"><span className="blue">Endereço</span>   <span className="green">Av. Central, 1500</span></span>
                                        <span className="grad-janela-linha"><span className="blue">Bairro</span>     <span className="green">Centro · Xique-Xique/BA</span></span>
                                        <span className="grad-janela-linha"><span className="blue">Telefone</span>   <span className="green">(74) 3000-1500</span></span>
                                        <span className="grad-janela-linha"> </span>
                                        <span className="grad-janela-linha"><span className="blue">Seg a Sex</span>  <span className="green">08h às 21h</span></span>
                                        <span className="grad-janela-linha"><span className="blue">Sábado</span>     <span className="green">08h às 12h</span></span>
                                        <span className="grad-janela-linha">
                                            <span className="muted">{"// Domingos e feriados: fechado"}</span>
                                            <span className="grad-janela-cursor" aria-hidden="true"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    <section className="grad-section" id="cc-canais">
                        <div className="grad-container">
                            <div className="grad-section-title" data-revelar>
                                <span className="grad-eyebrow">Canais</span>
                                <h2>Onde nos encontrar</h2>
                                <p>Siga a UniAura e acompanhe editais, eventos e a rotina do campus.</p>
                            </div>

                            <div className="grad-grid-3">
                                {CANAIS.map((canal, indice) => (
                                    <a
                                        key={canal.nome}
                                        className="grad-card site-canal"
                                        data-revelar
                                        style={{ "--atraso": `${indice * 90}ms`, "--site-canal-cor": canal.cor }}
                                        href={canal.href}
                                        target={canal.externo ? "_blank" : undefined}
                                        rel="noopener"
                                    >
                                        <div className="grad-card-icon">{canal.icone}</div>
                                        <h3>{canal.nome}</h3>
                                        <p>{canal.texto}</p>
                                        <span className="site-canal-handle">{canal.handle}</span>
                                    </a>
                                ))}
                            </div>
                        </div>
                    </section>

                    <section className="grad-section grad-section-alt" id="cc-setores">
                        <div className="grad-container">
                            <div className="grad-section-title" data-revelar>
                                <span className="grad-eyebrow">Setores</span>
                                <h2>Falar direto com quem resolve</h2>
                                <p>Se você já sabe o assunto, escrever para o setor certo agiliza a resposta.</p>
                            </div>

                            <div className="site-setores">
                                {SETORES.map((setor, indice) => (
                                    <div key={setor.nome} className="site-setor" data-revelar style={{ "--atraso": `${indice * 60}ms` }}>
                                        <strong>{setor.nome}</strong>
                                        <span>{setor.descricao}</span>
                                        <a href={`mailto:${setor.email}`}>{setor.email}</a>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </section>

                    <section className="grad-section" id="cc-mensagem">
                        <div className="grad-container">
                            <div className="grad-section-title" data-revelar>
                                <span className="grad-eyebrow">Mensagem</span>
                                <h2>Prefere escrever por aqui?</h2>
                                <p>Os campos marcados com asterisco são obrigatórios.</p>
                            </div>

                            <form id="formContato" className="site-form" noValidate data-revelar onSubmit={aoEnviar}>
                                <div className="site-form-grid">
                                    <div className="field">
                                        <label htmlFor="ctNome">Nome completo *</label>
                                        <input id="ctNome" name="nome" type="text" required minLength={5} autoComplete="name" />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="ctEmail">E-mail *</label>
                                        <input id="ctEmail" name="email" type="email" required autoComplete="email" />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="ctTelefone">Telefone</label>
                                        <input
                                            id="ctTelefone" name="telefone" type="tel" maxLength={15}
                                            placeholder="(00) 00000-0000" autoComplete="tel"
                                            value={telefone}
                                            onChange={(e) => setTelefone(mascararTelefone(e.target.value))}
                                        />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="ctAssunto">Assunto *</label>
                                        <Dropdown
                                            id="ctAssunto"
                                            name="assunto"
                                            options={ASSUNTOS}
                                            value={assunto}
                                            onChange={setAssunto}
                                            placeholder="Selecione o assunto"
                                            invalid={assuntoInvalido}
                                        />
                                    </div>
                                </div>

                                <div className="field">
                                    <label htmlFor="ctMensagem">Mensagem *</label>
                                    <textarea id="ctMensagem" name="mensagem" rows={5} required minLength={20}
                                              placeholder="Conte com detalhes como podemos ajudar."></textarea>
                                </div>

                                <label className="site-form-termos">
                                    <input id="ctTermos" name="termos" type="checkbox" required />
                                    <span>Autorizo o contato da UniAura e li a política de privacidade. *</span>
                                </label>

                                <button id="btnEnviarContato" className="grad-btn grad-btn-primary" type="submit" disabled={enviando}>
                                    {enviando ? "Enviando..." : "Enviar mensagem"}
                                </button>

                                {sucesso && <p id="ctMensagemOk" className="site-form-msg" aria-live="polite">{sucesso}</p>}
                                {erro && <p id="ctErro" className="site-form-erro" aria-live="polite">{erro}</p>}
                            </form>
                        </div>
                    </section>
                </main>
            </div>
        </SiteChrome>
    );
}
