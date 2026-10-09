"use client";

import { useState }         from "react";
import { useRouter }        from "next/navigation";
import { Carousel }         from "primereact/carousel";
import { Button }           from "primereact/button";
import { InputText }        from "primereact/inputtext";
import { InputTextarea }    from "primereact/inputtextarea";
import { Message }          from "primereact/message";
import { notificar }        from "@/lib/notificar";
import { mascararTelefone } from "@/lib/mascaras";

// --- SLIDES DE NOTÍCIAS ---
const SLIDES = [
    {
        tag: "Vestibular 2027",
        classeTag: "",
        titulo: "Inscrições abertas para o vestibular da UniAura",
        texto: "Mais de 2.000 vagas em 30 cursos de graduação. Provas em agosto e novembro. Inscreva-se e dê o primeiro passo para sua carreira.",
        icone: (
            <svg viewBox="0 0 24 24" width="72" height="72" fill="none" stroke="currentColor" strokeWidth="1.6"
                    strokeLinecap="round" strokeLinejoin="round">
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

// --- INDICADORES DE CONFIANÇA (FAIXA ABAIXO DO CARROSSEL) ---
const INDICADORES = [
    { valor: "35+", rotulo: "anos de excelência acadêmica" },
    { valor: "30", rotulo: "cursos de graduação" },
    { valor: "2.000+", rotulo: "vagas no vestibular 2027" },
    { valor: "1º", rotulo: "em inovação no ranking nacional" }
];

// --- CANAIS DE CONTATO DA SEÇÃO "FALE CONOSCO" (href = null: informação sem link) ---
const CANAIS_CONTATO = [
    {
        rotulo: "Telefone",
        valor: "(61) 3000-0000",
        href: "tel:+556130000000",
        icone: <path d="M22 16.9v3a2 2 0 0 1-2.2 2 19.8 19.8 0 0 1-8.6-3.1 19.5 19.5 0 0 1-6-6A19.8 19.8 0 0 1 2.1 4.2 2 2 0 0 1 4.1 2h3a2 2 0 0 1 2 1.7c.1.9.3 1.8.6 2.6a2 2 0 0 1-.5 2.1L8 9.6a16 16 0 0 0 6 6l1.2-1.2a2 2 0 0 1 2.1-.5c.8.3 1.7.5 2.6.6a2 2 0 0 1 1.7 2Z"></path>
    },
    {
        rotulo: "E-mail",
        valor: "contato@uniaura.edu.br",
        href: "mailto:contato@uniaura.edu.br",
        icone: (<><rect x="2" y="4" width="20" height="16" rx="2"></rect><path d="m2 7 10 6 10-6"></path></>)
    },
    {
        rotulo: "Instagram",
        valor: "@uniaura",
        href: "https://www.instagram.com/__.fr3it4s.__/",
        externo: true,
        icone: (<><rect x="2" y="2" width="20" height="20" rx="5"></rect><circle cx="12" cy="12" r="4"></circle><path d="M17.5 6.5h.01"></path></>)
    },
    {
        rotulo: "Threads",
        valor: "@uniaura",
        href: "https://www.threads.net/@__.fr3it4s.__",
        externo: true,
        icone: (<><circle cx="12" cy="12" r="4"></circle><path d="M16 8v5a3 3 0 0 0 6 0v-1a10 10 0 1 0-4 8"></path></>)
    },
    {
        rotulo: "Endereço",
        valor: "SGAS 000, Bloco A, Brasília - DF",
        href: "https://www.google.com/maps/search/?api=1&query=SGAS+000+Bloco+A+Bras%C3%ADlia+DF",
        externo: true,
        icone: (<><path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0Z"></path><circle cx="12" cy="10" r="3"></circle></>)
    },
    {
        rotulo: "Horário",
        valor: "Segunda a sexta, das 8h às 20h",
        href: null,
        icone: (<><circle cx="12" cy="12" r="10"></circle><path d="M12 6v6l4 2"></path></>)
    }
];

// --- RENDERIZAÇÃO DA PÁGINA ---
export default function HomeInstitucionalPage() {
    const router = useRouter();
    const [mensagemEnviada, setMensagemEnviada] = useState(false);
    const [telefone, setTelefone] = useState("");
    const [erros, setErros] = useState({});

    // --- REMOVE O ERRO DE UM CAMPO ASSIM QUE O USUÁRIO MEXE NELE ---
    const limparErro = (nome) => {
        setErros((atual) => {
            if (!atual[nome]) {
                return atual;
            }
            const { [nome]: _removido, ...resto } = atual;
            return resto;
        });
    };

    // --- MENSAGEM DE ERRO EM VERMELHO ABAIXO DO CAMPO ---
    const erroDe = (nome) => erros[nome] && <small className="ua-erro-campo" role="alert">{erros[nome]}</small>;
    const classeErro = (nome) => (erros[nome] ? "p-invalid" : "");

    // --- ROLA SUAVEMENTE PARA UMA ÂNCORA DA PÁGINA ---
    const rolarPara = (id) => (evento) => {
        evento.preventDefault();
        document.getElementById(id)?.scrollIntoView({ behavior: "smooth", block: "start" });
    };

    // -- FUNÇÃO PARA REDIRECIONAR PARA O PORTAL DO ALUNO ---
    const irParaPortal = () => router.push("/portal-do-aluno/login");

    // --- FUNÇÃO DE ENVIO DE FORMULÁRIO DE CONTATO ---
    const enviarContato = (evento) => {
        evento.preventDefault();
        const formulario = evento.target;

        // --- VALIDA OS CAMPOS OBRIGATÓRIOS E MOSTRA UMA MENSAGEM POR CAMPO ---
        const novos = {};
        const nome = formulario.nome.value.trim();
        const email = formulario.email.value.trim();
        const mensagem = formulario.mensagem.value.trim();

        if (!nome) {
            novos.nome = "Informe seu nome completo.";
        } else if (nome.length < 5) {
            novos.nome = "Use pelo menos 5 caracteres.";
        }

        if (!email) {
            novos.email = "Informe seu e-mail.";
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            novos.email = "Informe um e-mail válido.";
        }

        if (!mensagem) {
            novos.mensagem = "Escreva sua mensagem.";
        }

        setErros(novos);
        if (Object.keys(novos).length > 0) {
            setMensagemEnviada(false);
            return;
        }

        formulario.reset();
        setTelefone("");
        setMensagemEnviada(true);
        notificar("Mensagem enviada com sucesso!", "success");
    };

    // --- TEMPLATE DE CADA SLIDE DO CARROSSEL DE NOTÍCIAS ---
    const templateSlide = (slide) => (
        <article className="site-noticia-slide ativo">
            <div className="site-noticia-conteudo">
                <span className={`site-noticia-tag ${slide.classeTag}`}>{slide.tag}</span>
                <h2>{slide.titulo}</h2>
                <p>{slide.texto}</p>
                <div className="site-noticia-acoes">
                    <Button label="Fale com a secretaria" size="large" onClick={rolarPara("contato")} />
                    <Button label="Conheça os cursos" size="large" outlined onClick={rolarPara("ensino")} />
                </div>
            </div>
            <div className="site-noticia-arte">
                <span className="site-noticia-icone" aria-hidden="true">{slide.icone}</span>
            </div>
        </article>
    );

    // --- RENDERIZAÇÃO DA PÁGINA ---
    return (
        <>
            <h1 className="ua-sr-only">UniAura, Universidade Aura</h1>

            <section id="noticias" className="site-noticias-hero">
                <div className="site-container">
                    <Carousel
                        value={SLIDES}
                        itemTemplate={templateSlide}
                        numVisible={1}
                        numScroll={1}
                        circular
                        autoplayInterval={6000}
                        showIndicators
                    />
                </div>
            </section>

            <section className="site-container ua-faixa-confianca" aria-label="UniAura em números">
                {INDICADORES.map((indicador) => (
                    <div key={indicador.rotulo} className="ua-card ua-indicador">
                        <span className="ua-indicador__valor">{indicador.valor}</span>
                        <span className="ua-indicador__rotulo">{indicador.rotulo}</span>
                    </div>
                ))}
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
                        <article className="site-card site-card-level ua-card" data-cor="azul">
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

                        <article className="site-card site-card-level ua-card" data-cor="vermelho">
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

                        <article className="site-card site-card-level ua-card" data-cor="amarelo">
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
                        <Button label="Ver cursos" size="large" onClick={() => router.push("/web/graduacao/cursos")} />
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

                            <Button label="Acessar o Portal" size="large" onClick={irParaPortal} />
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
                            <ul className="ua-contato-links">
                                {CANAIS_CONTATO.map((canal) => {
                                    const conteudo = (
                                        <>
                                            <span className="ua-contato-link__icone" aria-hidden="true">
                                                <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                                                    {canal.icone}
                                                </svg>
                                            </span>
                                            <span className="ua-contato-link__texto">
                                                <small>{canal.rotulo}</small>
                                                <strong>{canal.valor}</strong>
                                            </span>
                                            {canal.href && <i className="pi pi-arrow-up-right ua-contato-link__seta" aria-hidden="true" />}
                                        </>
                                    );

                                    return (
                                        <li key={canal.rotulo}>
                                            {canal.href ? (
                                                <a
                                                    className="ua-contato-link"
                                                    href={canal.href}
                                                    target={canal.externo ? "_blank" : undefined}
                                                    rel={canal.externo ? "noopener noreferrer" : undefined}
                                                    aria-label={`${canal.rotulo}: ${canal.valor}`}
                                                >
                                                    {conteudo}
                                                </a>
                                            ) : (
                                                <div className="ua-contato-link ua-contato-link--estatico">{conteudo}</div>
                                            )}
                                        </li>
                                    );
                                })}
                            </ul>
                        </div>

                        <form id="siteForm" className="site-form" noValidate onSubmit={enviarContato}>
                            <div className="field">
                                <label htmlFor="siteNome">Nome completo <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                <InputText id="siteNome" name="nome" autoComplete="name" className={classeErro("nome")} onInput={() => limparErro("nome")} />
                                {erroDe("nome")}
                            </div>

                            <div className="field">
                                <label htmlFor="siteEmail">E-mail <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                <InputText type="email" id="siteEmail" name="email" autoComplete="email" className={classeErro("email")} onInput={() => limparErro("email")} />
                                {erroDe("email")}
                            </div>

                            <div className="field">
                                <label htmlFor="siteTel">Telefone</label>
                                <InputText
                                    type="tel"
                                    id="siteTel"
                                    name="telefone"
                                    maxLength={15}
                                    placeholder="(00) 00000-0000"
                                    autoComplete="tel"
                                    value={telefone}
                                    onChange={(e) => setTelefone(mascararTelefone(e.target.value))}
                                />
                            </div>

                            <div className="field">
                                <label htmlFor="siteMsg">Mensagem <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                <InputTextarea id="siteMsg" name="mensagem" rows={4} className={classeErro("mensagem")} onInput={() => limparErro("mensagem")} />
                                {erroDe("mensagem")}
                            </div>

                            <Button type="submit" label="Enviar mensagem" size="large" />
                            {mensagemEnviada && <Message severity="success" text="Mensagem enviada! Em breve entraremos em contato." />}
                        </form>
                    </div>
                </div>
            </section>
        </>
    );
}
