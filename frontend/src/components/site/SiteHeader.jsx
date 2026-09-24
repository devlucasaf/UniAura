"use client";

import { useEffect, useRef, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

// --- CURSOS DE GRADUAÇÃO DO SUBMENU ---
const CURSOS_GRADUACAO = [
    { nome: "Ciência da Computação", rota: "/graduacao/ciencia-da-computacao" },
    { nome: "Análise e Desenvolvimento de Sistemas", rota: "/graduacao/analise-e-desenvolvimento-de-sistemas" },
    { nome: "Engenharia de Software", rota: "/graduacao/engenharia-de-software" },
    { nome: "Ciência de Dados", rota: "/graduacao/ciencia-de-dados" },
    { nome: "Engenharia Mecatrônica", rota: "/graduacao/engenharia-mecatronica" },
    { nome: "Engenharia Civil", rota: "/graduacao/engenharia-civil" },
    { nome: "Engenharia Mecânica", rota: "/graduacao/engenharia-mecanica" },
    { nome: "Engenharia de Produção", rota: "/graduacao/engenharia-producao" },
    { nome: "Engenharia Elétrica", rota: "/graduacao/engenharia-eletrica" },
    { nome: "Engenharia Química", rota: "/graduacao/engenharia-quimica" },
    { nome: "Engenharia Florestal", rota: "/graduacao/engenharia-florestal" },
    { nome: "Ciências Aeronáuticas", rota: "/graduacao/ciencias-aeronauticas" },
    { nome: "Medicina", rota: "/graduacao/medicina" },
    { nome: "Odontologia", rota: "/graduacao/odontologia" },
    { nome: "Farmácia", rota: "/graduacao/farmacia" },
    { nome: "Fisioterapia", rota: "/graduacao/fisioterapia" },
    { nome: "Nutrição", rota: "/graduacao/nutricao" },
    { nome: "Biomedicina", rota: "/graduacao/biomedicina" },
    { nome: "Ciências Biológicas", rota: "/graduacao/biologia" },
    { nome: "Educação Física", rota: "/graduacao/educacao-fisica" },
    { nome: "Fonoaudiologia", rota: "/graduacao/fonoaudiologia" },
    { nome: "Terapia Ocupacional", rota: "/graduacao/terapia-ocupacional" },
    { nome: "Medicina Veterinária", rota: "/graduacao/medicina-veterinaria" },
    { nome: "Design", rota: "/graduacao/design" },
    { nome: "Publicidade e Propaganda", rota: "/graduacao/publicidade-propaganda" },
    { nome: "Artes Visuais", rota: "/graduacao/artes-visuais" },
    { nome: "Artes Cênicas", rota: "/graduacao/artes-cenicas" },
    { nome: "Design de Moda", rota: "/graduacao/moda" },
    { nome: "Fotografia", rota: "/graduacao/fotografia" },
    { nome: "Administração", rota: "/graduacao/administracao" },
    { nome: "Direito", rota: "/graduacao/direito" }
];

const MEGA_UNIVERSIDADE = [
    { titulo: "Nossa História", descricao: "35 anos de excelência acadêmica", rota: "/sobre/historia" },
    { titulo: "Nossa Equipe", descricao: "Quem faz a universidade", rota: "/sobre/equipe" },
    { titulo: "Estrutura Física", descricao: "Salas, laboratórios e biblioteca", rota: null },
    { titulo: "Regimento Universitário", descricao: "Normas e diretrizes", rota: null },
    { titulo: "Prêmios e Rankings", descricao: "Reconhecimentos nacionais", rota: null }
];

const MEGA_MODALIDADES = [
    { titulo: "Pós-Graduação", descricao: "Especialização, mestrado e doutorado", rota: null },
    { titulo: "Cursos Técnicos", descricao: "Formação profissional rápida", rota: null },
    { titulo: "Idiomas", descricao: "Inglês, espanhol e libras", rota: null },
    { titulo: "Extensão Universitária", descricao: "Projetos comunitários", rota: null },
    { titulo: "Pesquisa", descricao: "Grupos e laboratórios", rota: null }
];

export default function SiteHeader({ ancoras = false }) {
    const router = useRouter();
    const [tema, setTema] = useState("light");
    const [menuAberto, setMenuAberto] = useState(false);
    const [megaAberto, setMegaAberto] = useState(null);
    const [submenuAberto, setSubmenuAberto] = useState(false);
    const [comSombra, setComSombra] = useState(false);
    const botaoTemaRef = useRef(null);

    // --- CARREGA O TEMA SALVO E OBSERVA O SCROLL PARA A SOMBRA DO CABEÇALHO ---
    useEffect(() => {
        const salvo = localStorage.getItem("theme") || "light";
        setTema(salvo);
        document.documentElement.setAttribute("data-theme", salvo);

        const aoRolar = () => setComSombra(window.scrollY > 10);
        aoRolar();
        window.addEventListener("scroll", aoRolar, { passive: true });
        return () => window.removeEventListener("scroll", aoRolar);
    }, []);

    // --- ESCAPE E CLIQUE FORA FECHAM QUALQUER MEGA-MENU/SUBMENU ABERTO ---
    useEffect(() => {
        const aoTeclar = (evento) => {
            if (evento.key === "Escape") {
                setMegaAberto(null);
                setSubmenuAberto(false);
            }
        };
        const aoClicar = (evento) => {
            if (!evento.target.closest("[data-mega]")) {
                setMegaAberto(null);
            }

            if (!evento.target.closest("[data-submenu]")) {
                setSubmenuAberto(false);
            }
        };
        document.addEventListener("keydown", aoTeclar);
        document.addEventListener("click", aoClicar);
        return () => {
            document.removeEventListener("keydown", aoTeclar);
            document.removeEventListener("click", aoClicar);
        };
    }, []);

    // --- ALTERNA O TEMA COM UMA ONDA CIRCULAR EXPANSIVA A PARTIR DO BOTÃO ---
    const alternarTema = () => {
        const botao = botaoTemaRef.current;
        if (botao) {
            const retangulo = botao.getBoundingClientRect();
            const onda = document.createElement("span");
            onda.className = "site-tema-onda";
            onda.style.left = `${retangulo.left + retangulo.width / 2}px`;
            onda.style.top = `${retangulo.top + retangulo.height / 2}px`;
            onda.dataset.tema = tema === "dark" ? "light" : "dark";
            document.body.appendChild(onda);
            onda.addEventListener("animationend", () => onda.remove(), { once: true });

            botao.classList.add("girando");
            setTimeout(() => botao.classList.remove("girando"), 600);
        }

        setTimeout(() => {
            const novo = tema === "dark" ? "light" : "dark";
            document.documentElement.setAttribute("data-theme", novo);
            localStorage.setItem("theme", novo);
            setTema(novo);
        }, 150);
    };

    const rolarPara = (idAlvo) => (evento) => {
        evento.preventDefault();
        document.getElementById(idAlvo)?.scrollIntoView({
            behavior: "smooth",
            block: "start"
        });
        setMenuAberto(false);
    };

    // --- EM TOUCH, O TAP NO LINK SÓ ABRE/FECHA O PAINEL ---
    const alternarMegaSeTouch = (chave) => (evento) => {
        const ehTouch = window.matchMedia("(hover: none)").matches;
        if (ehTouch) {
            evento.preventDefault();
            setMegaAberto((atual) => (atual === chave ? null : chave));
        }
    };

    const irParaPortal = () => router.push("/portal-do-aluno/login");

    const hrefUniversidade = ancoras ? "#sobre" : "/sobre/historia";
    const hrefCursos = ancoras ? "#ensino" : "/graduacao/cursos";

    return (
        <header id="siteHeader" className={`site-header${comSombra ? " com-sombra" : ""}`}>
            <div className="site-container site-nav">
                <Link href="/" className="site-brand" aria-label="UniAura - Página inicial">
                    <img
                        src="/img/uniaura.png"
                        alt=""
                        className="site-brand-logo"
                    />
                    <span className="site-brand-text">
                        <strong>UniAura</strong>
                    </span>
                </Link>

                <nav id="siteMenu" className={`site-menu${menuAberto ? " aberto" : ""}`} aria-label="Navegação principal">
                    <div className={`site-menu-item${megaAberto === "universidade" ? " aberto" : ""}`} data-mega>
                        {ancoras ? (
                            <a className="site-link site-link-com-mega" href={hrefUniversidade} onClick={rolarPara("sobre")}
                                aria-haspopup="true" aria-expanded={megaAberto === "universidade"}>
                                A Universidade
                                <SetaMega />
                            </a>
                        ) : (
                            <Link className="site-link site-link-com-mega" href={hrefUniversidade} aria-haspopup="true"
                                aria-expanded={megaAberto === "universidade"} onClick={alternarMegaSeTouch("universidade")}>
                                A Universidade
                                <SetaMega />
                            </Link>
                        )}

                        <div className="site-mega" role="menu" aria-label="Sobre a UniAura">
                            <div className="site-mega-grade">
                                {MEGA_UNIVERSIDADE.map((item) => (
                                    <Link key={item.titulo} href={item.rota || "#"} className="site-mega-link" role="menuitem">
                                        <strong>{item.titulo}</strong>
                                        <span>{item.descricao}</span>
                                    </Link>
                                ))}
                            </div>
                            <div className="site-mega-destaque">
                                <span className="site-mega-eyebrow">Conheça</span>
                                <h4>UniAura, 35 anos de tradição</h4>
                                <p>Uma universidade que forma líderes e transforma a sociedade.</p>
                                <Link href="/sobre/historia" className="site-mega-cta">Nossa história completa →</Link>
                            </div>
                        </div>
                    </div>

                    <div className={`site-menu-item${megaAberto === "cursos" ? " aberto" : ""}`} data-mega>
                        {ancoras ? (
                            <a
                                className="site-link site-link-com-mega"
                                href={hrefCursos}
                                onClick={rolarPara("ensino")}
                                aria-haspopup="true"
                                aria-expanded={megaAberto === "cursos"}
                            >
                                Cursos
                                <SetaMega />
                            </a>
                        ) : (
                            <Link
                                className="site-link site-link-com-mega"
                                onClick={alternarMegaSeTouch("cursos")}
                                href={hrefCursos}
                                aria-haspopup="true"
                                aria-expanded={megaAberto === "cursos"}
                            >
                                Cursos
                                <SetaMega />
                            </Link>
                        )}

                        <div className="site-mega" role="menu" aria-label="Cursos de graduação e pós">
                            <div className="site-mega-grade">
                                <div className={`site-mega-item-flyout${submenuAberto ? " aberto" : ""}`} data-submenu>
                                    <button
                                        type="button"
                                        className="site-mega-link-gatilho"
                                        aria-haspopup="true"
                                        aria-expanded={submenuAberto}
                                        onClick={() => setSubmenuAberto((a) => !a)}
                                    >
                                        <span className="site-mega-link-texto">
                                            <strong>Graduação</strong>
                                            <span>Bacharelados e tecnólogos</span>
                                        </span>
                                        <svg className="site-mega-seta-submenu" viewBox="0 0 24 24" width="16" height="16" fill="none"
                                             stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                            <polyline points="9 6 15 12 9 18"></polyline>
                                        </svg>
                                    </button>
                                    <div className="site-submenu" role="menu" aria-label="Cursos de Graduação">
                                        {CURSOS_GRADUACAO.map((curso) => (
                                            <Link key={curso.nome} href={curso.rota || "#"} className="site-submenu-link" role="menuitem">
                                                {curso.nome}
                                            </Link>
                                        ))}
                                    </div>
                                </div>
                                {MEGA_MODALIDADES.map((item) => (
                                    <Link key={item.titulo} href={item.rota || "#"} className="site-mega-link" role="menuitem">
                                        <strong>{item.titulo}</strong>
                                        <span>{item.descricao}</span>
                                    </Link>
                                ))}
                            </div>
                            <div className="site-mega-destaque">
                                <span className="site-mega-eyebrow">Calendário</span>
                                <h4>Ano letivo 2027</h4>
                                <p>Inscrições abertas para o vestibular e para a pós-graduação.</p>
                                <Link href="/matriculas" className="site-mega-cta">Fazer minha matrícula →</Link>
                            </div>
                        </div>
                    </div>

                    <Link className="site-link" href="/">Notícias</Link>
                    <Link className="site-link" href="/contato">Contato</Link>
                    <Link className="site-link" href="/portal-do-aluno/login">Portal do Aluno</Link>
                </nav>

                <div className="site-actions">
                    <button
                        ref={botaoTemaRef}
                        id="btnTemaSite"
                        className={`site-tema-toggle${tema === "dark" ? " noturno" : ""}`}
                        type="button"
                        aria-label="Alternar tema claro e escuro"
                        title="Alternar tema"
                        onClick={alternarTema}
                    >
                        <span className="site-tema-icone site-tema-sol" aria-hidden="true">
                            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                                 strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                <circle cx="12" cy="12" r="4"></circle>
                                <path d="M12 2v2M12 20v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M2 12h2M20 12h2M4.93 19.07l1.41-1.41M17.66 6.34l1.41-1.41"></path>
                            </svg>
                        </span>
                        <span className="site-tema-icone site-tema-lua" aria-hidden="true">
                            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                                 strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path>
                            </svg>
                        </span>
                    </button>

                    <button id="btnPortal" className="btn btn-primary site-btn-entrar" type="button" onClick={irParaPortal}>
                        Portal do Aluno
                    </button>

                    <button id="siteBurger" className={`site-burger${menuAberto ? " ativo" : ""}`} type="button"
                            aria-label="Abrir menu" aria-expanded={menuAberto} onClick={() => setMenuAberto((a) => !a)}>
                        <span></span>
                        <span></span>
                        <span></span>
                    </button>
                </div>
            </div>
        </header>
    );
}

function SetaMega() {
    return (
        <svg className="site-link-seta" viewBox="0 0 24 24" width="14" height="14" fill="none"
             stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
            <polyline points="6 9 12 15 18 9"></polyline>
        </svg>
    );
}
