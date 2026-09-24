"use client";

import { useEffect, useRef, useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import SiteChrome from "@/components/site/SiteChrome";
import { notificar } from "@/lib/notificar";

const SLIDES = [
    {
        tag: "Vestibular 2027",
        classeTag: "",
        titulo: "Inscrições abertas para o vestibular da UniAura",
        texto: "Mais de 2.000 vagas em 30 cursos de graduação. Provas em agosto e novembro. Inscreva-se e dê o primeiro passo para sua carreira.",
        icone: (
            <svg viewBox="0 0 24 24" width="72" height="72" fill="none" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
                <path d="M22 10 12 4 2 10l10 6 10-6Z"></path>
                <path d="M6 12v5c3 3 9 3 12 0v-5"></path>
                <path d="M22 10v6"></path>
            </svg>
        )
    },
    {
        tag: "Evento",
        classeTag: "site-noticia-tag-accent",
        titulo: "Semana de Ciência e Tecnologia 2026",
        texto: "Palestras, workshops e feira de projetos com alunos de graduação e pós. Participe e compartilhe conhecimento.",
        icone: (
            <svg viewBox="0 0 24 24" width="72" height="72" fill="none" stroke="currentColor"
                 strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
                <path d="M9 2v6L3.5 18a2.5 2.5 0 0 0 2.2 3.5h12.6A2.5 2.5 0 0 0 20.5 18L15 8V2"></path>
                <path d="M8 2h8"></path>
                <path d="M7 14h10"></path>
            </svg>
        )
    },
    {
        tag: "Conquista",
        classeTag: "site-noticia-tag-highlight",
        titulo: "UniAura no topo do ranking de inovação",
        texto: "Pelo segundo ano consecutivo, nossa universidade é destaque nacional em pesquisa aplicada e parcerias com o setor produtivo.",
        icone: (
            <svg viewBox="0 0 24 24" width="72" height="72" fill="none" stroke="currentColor"
                 strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
                <path d="M6 9H4a2 2 0 0 1-2-2V5h4"></path>
                <path d="M18 9h2a2 2 0 0 0 2-2V5h-4"></path>
                <path d="M6 5h12v5a6 6 0 0 1-12 0V5Z"></path>
                <path d="M9 21h6"></path>
                <path d="M12 17v4"></path>
            </svg>
        )
    }
];

export default function HomePage() {
    const router = useRouter();
    const [slideAtual, setSlideAtual] = useState(0);
    const [mensagemEnviada, setMensagemEnviada] = useState(false);
    const temporizadorRef = useRef(null);

    const irPara = (indice) => {
        setSlideAtual((indice + SLIDES.length) % SLIDES.length);
    };

    const iniciarRotacao = () => {
        pararRotacao();
        temporizadorRef.current = setInterval(() => {
            setSlideAtual((atual) => (atual + 1) % SLIDES.length);
        }, 6000);
    };

    const pararRotacao = () => {
        if (temporizadorRef.current) {
            clearInterval(temporizadorRef.current);
            temporizadorRef.current = null;
        }
    };

    useEffect(() => {
        iniciarRotacao();
        return pararRotacao;
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const rolarPara = (id) => (evento) => {
        evento.preventDefault();
        document.getElementById(id)?.scrollIntoView({ behavior: "smooth", block: "start" });
    };

    const irParaPortal = () => router.push("/portal-do-aluno/login");

    const enviarContato = (evento) => {
        evento.preventDefault();
        const formulario = evento.target;

        if (!formulario.checkValidity()) {
            formulario.reportValidity();
            return;
        }

        formulario.reset();
        setMensagemEnviada(true);
        notificar("Mensagem enviada com sucesso!", "success");
    };

    return (
        <SiteChrome ancoras>
            <section id="noticias" className="site-noticias-hero">
                <div className="site-container">
                    <div
                        className="site-noticias-slider"
                        onMouseEnter={pararRotacao}
                        onMouseLeave={iniciarRotacao}
                    >
                        {SLIDES.map((slide, indice) => (
                            <article
                                key={slide.titulo}
                                className={`site-noticia-slide${indice === slideAtual ? " ativo" : ""}`}
                                data-slide={indice}
                            >
                                <div className="site-noticia-conteudo">
                                    <span className={`site-noticia-tag ${slide.classeTag}`}>{slide.tag}</span>
                                    <h1>{slide.titulo}</h1>
                                    <p>{slide.texto}</p>
                                    <div className="site-noticia-acoes">
                                        <button className="btn btn-primary btn-lg" type="button" onClick={rolarPara("contato")}>
                                            Fale com a secretaria
                                        </button>
                                        <button className="btn btn-outline btn-lg" type="button" onClick={rolarPara("ensino")}>
                                            Conheça os cursos
                                        </button>
                                    </div>
                                </div>
                                <div className="site-noticia-arte">
                                    <span className="site-noticia-icone" aria-hidden="true">{slide.icone}</span>
                                </div>
                            </article>
                        ))}
                    </div>

                    <div className="site-slider-controles">
                        <button className="site-slider-btn" type="button" aria-label="Notícia anterior"
                                onClick={() => { irPara(slideAtual - 1); iniciarRotacao(); }}>
                            ‹
                        </button>
                        <div className="site-slider-pontos" role="tablist">
                            {SLIDES.map((slide, indice) => (
                                <button
                                    key={slide.titulo}
                                    className={`site-slider-ponto${indice === slideAtual ? " ativo" : ""}`}
                                    type="button"
                                    aria-label={`Ir para notícia ${indice + 1}`}
                                    onClick={() => { irPara(indice); iniciarRotacao(); }}
                                />
                            ))}
                        </div>
                        <button className="site-slider-btn" type="button" aria-label="Próxima notícia"
                                onClick={() => { irPara(slideAtual + 1); iniciarRotacao(); }}>
                            ›
                        </button>
                    </div>
                </div>
            </section>

            <section id="sobre" className="site-section site-section-alt">
                <div className="site-container">
                    <div className="site-section-head">
                        <span className="site-eyebrow">Nossa universidade</span>
                        <h2>Sobre a UniAura</h2>
                        <p className="muted">
                            Há mais de 35 anos formando cidadãos críticos, éticos e preparados para os desafios do mundo
                            contemporâneo, com ensino de qualidade, pesquisa inovadora e extensão comprometida com a
                            sociedade.
                        </p>
                    </div>

                    <div className="site-sobre-grid">
                        <div className="site-sobre-item">
                            <div className="site-sobre-icone" data-cor="azul">
                                <svg viewBox="0 0 24 24" width="28" height="28" fill="none" stroke="currentColor"
                                     strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                    <circle cx="12" cy="12" r="10"></circle>
                                    <circle cx="12" cy="12" r="6"></circle>
                                    <circle cx="12" cy="12" r="2"></circle>
                                </svg>
                            </div>
                            <h3>Missão</h3>
                            <p className="muted">
                                Promover o ensino, a pesquisa e a extensão para formar profissionais competentes e
                                cidadãos comprometidos com o bem comum.
                            </p>
                        </div>

                        <div className="site-sobre-item">
                            <div className="site-sobre-icone" data-cor="vermelho">
                                <svg viewBox="0 0 24 24" width="28" height="28" fill="none" stroke="currentColor"
                                     strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                    <path d="M9 18h6"></path>
                                    <path d="M10 22h4"></path>
                                    <path d="M12 2a7 7 0 0 0-4 12.7c.7.6 1 1.5 1 2.3v1h6v-1c0-.8.3-1.7 1-2.3A7 7 0 0 0 12 2Z"></path>
                                </svg>
                            </div>
                            <h3>Visão</h3>
                            <p className="muted">
                                Ser referência em educação superior no Centro-Oeste, reconhecida pela inovação, inclusão
                                e impacto social.
                            </p>
                        </div>

                        <div className="site-sobre-item">
                            <div className="site-sobre-icone" data-cor="amarelo">
                                <svg viewBox="0 0 24 24" width="28" height="28" fill="none" stroke="currentColor"
                                     strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                    <path d="M12 2l3 6.5 7 1-5 4.8 1.2 7L12 17.8 5.8 21.3 7 14.3 2 9.5l7-1L12 2Z"></path>
                                </svg>
                            </div>
                            <h3>Valores</h3>
                            <p className="muted">
                                Excelência acadêmica, ética, pluralidade, respeito à diversidade, autonomia e
                                compromisso com a transformação social.
                            </p>
                        </div>
                    </div>
                </div>
            </section>

            <section id="ensino" className="site-section">
                <div className="site-container">
                    <div className="site-section-head">
                        <span className="site-eyebrow">Oferta acadêmica</span>
                        <h2>Nossos Cursos</h2>
                        <p className="muted">Graduação, pós-graduação e extensão para todas as áreas do conhecimento.</p>
                    </div>

                    <div className="site-cards">
                        <article className="site-card site-card-level" data-cor="azul">
                            <div className="site-card-icon">
                                <svg viewBox="0 0 24 24" width="32" height="32" fill="none" stroke="currentColor"
                                     strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                    <path d="M12 2L2 7l10 5 10-5-10-5Z"></path>
                                    <path d="M2 17l10 5 10-5"></path>
                                    <path d="M2 12l10 5 10-5"></path>
                                </svg>
                            </div>
                            <h3>Ciências Humanas</h3>
                            <p className="muted">
                                Graduação e pós em Direito, Psicologia, História, Filosofia e Educação. Formação
                                crítica e cidadã.
                            </p>
                        </article>

                        <article className="site-card site-card-level" data-cor="vermelho">
                            <div className="site-card-icon">
                                <svg viewBox="0 0 24 24" width="32" height="32" fill="none" stroke="currentColor"
                                     strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                    <rect x="3" y="3" width="18" height="18" rx="2"></rect>
                                    <path d="M3 9h18"></path>
                                    <path d="M3 15h18"></path>
                                    <path d="M9 3v18"></path>
                                </svg>
                            </div>
                            <h3>Ciências Exatas e Tecnologia</h3>
                            <p className="muted">
                                Engenharias, Computação, Matemática e Física. Laboratórios modernos e parcerias com o
                                setor produtivo.
                            </p>
                        </article>

                        <article className="site-card site-card-level" data-cor="amarelo">
                            <div className="site-card-icon">
                                <svg viewBox="0 0 24 24" width="32" height="32" fill="none" stroke="currentColor"
                                     strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                    <path d="M4 4h16v16H4z"></path>
                                    <path d="M9 8h6"></path>
                                    <path d="M9 12h10"></path>
                                    <path d="M9 16h6"></path>
                                </svg>
                            </div>
                            <h3>Ciências da Saúde e Biológicas</h3>
                            <p className="muted">
                                Medicina, Enfermagem, Farmácia, Biologia e Educação Física. Prática desde o primeiro
                                semestre.
                            </p>
                        </article>
                    </div>

                    <div className="site-cards-acao">
                        <Link href="/graduacao/cursos" className="btn btn-primary btn-lg">Ver cursos</Link>
                    </div>
                </div>
            </section>

            <section id="portal" className="site-section site-section-alt">
                <div className="site-container">
                    <div className="site-split">
                        <div className="site-split-text">
                            <span className="site-eyebrow">Área do aluno</span>
                            <h2>Portal do Aluno</h2>
                            <p className="muted">Todo o dia a dia acadêmico em um só lugar, acessível 24 horas por dia.</p>

                            <ul className="site-list">
                                <li>Notas e frequência em tempo real</li>
                                <li>Biblioteca virtual e reserva de acervo</li>
                                <li>Matrícula em disciplinas eletivas</li>
                                <li>Comunicados da coordenação e professores</li>
                                <li>Calendário de provas e eventos</li>
                            </ul>

                            <button id="btnPortalAlunos" className="btn btn-primary btn-lg" type="button" onClick={irParaPortal}>
                                Acessar o Portal
                            </button>
                        </div>

                        <div className="site-split-art">
                            <div className="site-portal-mock" aria-hidden="true">
                                <div className="site-portal-mock-bar"></div>
                                <div className="site-portal-mock-row"></div>
                                <div className="site-portal-mock-row short"></div>
                                <div className="site-portal-mock-grid">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section id="contato" className="site-section">
                <div className="site-container">
                    <div className="site-section-head">
                        <span className="site-eyebrow">Atendimento</span>
                        <h2>Fale Conosco</h2>
                        <p className="muted">Tire suas dúvidas, agende uma visita ou solicite informações sobre nossos cursos.</p>
                    </div>

                    <div className="site-split">
                        <div className="site-split-text">
                            <ul className="site-contact">
                                <li>
                                    <span className="site-contact-icone" aria-hidden="true">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                                             strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                            <path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0Z"></path>
                                            <circle cx="12" cy="10" r="3"></circle>
                                        </svg>
                                    </span>
                                    <div>
                                        <strong>Endereço</strong>
                                        <br />SGAS 000, Bloco A, Brasília - DF
                                    </div>
                                </li>
                                <li>
                                    <span className="site-contact-icone" aria-hidden="true">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                                             strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                            <path d="M22 16.9v3a2 2 0 0 1-2.2 2 19.8 19.8 0 0 1-8.6-3.1 19.5 19.5 0 0 1-6-6A19.8 19.8 0 0 1 2.1 4.2 2 2 0 0 1 4.1 2h3a2 2 0 0 1 2 1.7c.1.9.3 1.8.6 2.6a2 2 0 0 1-.5 2.1L8 9.6a16 16 0 0 0 6 6l1.2-1.2a2 2 0 0 1 2.1-.5c.8.3 1.7.5 2.6.6a2 2 0 0 1 1.7 2Z"></path>
                                        </svg>
                                    </span>
                                    <div>
                                        <strong>Telefone</strong>
                                        <br />(61) 3000-0000
                                    </div>
                                </li>
                                <li>
                                    <span className="site-contact-icone" aria-hidden="true">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                                             strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                            <rect x="2" y="4" width="20" height="16" rx="2"></rect>
                                            <path d="m2 7 10 6 10-6"></path>
                                        </svg>
                                    </span>
                                    <div>
                                        <strong>E-mail</strong>
                                        <br />contato@uniaura.edu.br
                                    </div>
                                </li>
                                <li>
                                    <span className="site-contact-icone" aria-hidden="true">
                                        <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                                             strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                            <circle cx="12" cy="12" r="10"></circle>
                                            <path d="M12 6v6l4 2"></path>
                                        </svg>
                                    </span>
                                    <div>
                                        <strong>Horário</strong>
                                        <br />Segunda a sexta, das 8h às 20h
                                    </div>
                                </li>
                            </ul>
                        </div>

                        <form id="siteForm" className="site-form" noValidate onSubmit={enviarContato}>
                            <div className="field">
                                <label htmlFor="siteNome">Nome completo</label>
                                <input type="text" id="siteNome" name="nome" required autoComplete="name" />
                            </div>

                            <div className="field">
                                <label htmlFor="siteEmail">E-mail</label>
                                <input type="email" id="siteEmail" name="email" required autoComplete="email" />
                            </div>

                            <div className="field">
                                <label htmlFor="siteTel">Telefone</label>
                                <input type="tel" id="siteTel" name="telefone" autoComplete="tel" />
                            </div>

                            <div className="field">
                                <label htmlFor="siteMsg">Mensagem</label>
                                <textarea id="siteMsg" name="mensagem" rows={4} required></textarea>
                            </div>

                            <button type="submit" className="btn btn-primary btn-lg">Enviar mensagem</button>
                            {mensagemEnviada && (
                                <p id="siteFormMsg" className="site-form-msg">
                                    Mensagem enviada! Em breve entraremos em contato.
                                </p>
                            )}
                        </form>
                    </div>
                </div>
            </section>
        </SiteChrome>
    );
}
