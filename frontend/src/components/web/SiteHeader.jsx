"use client";

import { useEffect, useState }  from "react";
import Link                     from "next/link";
import { useRouter }            from "next/navigation";
import { Button }               from "primereact/button";

// --- CURSOS DE GRADUAÇÃO PRESENCIAL AGRUPADOS POR ÁREA ---
const AREAS_CURSOS = [
    {
        area: "Tecnologia",
        cursos: [
            {
                nome: "Ciência da Computação",
                rota: "/web/tecnologia/ciencia-da-computacao"
            },
            {
                nome: "Análise e Desenvolvimento de Sistemas",
                rota: "/web/tecnologia/analise-e-desenvolvimento-de-sistemas"
            },
            {
                nome: "Engenharia de Software",
                rota: "/web/tecnologia/engenharia-de-software"
            },
            {
                nome: "Ciência de Dados",
                rota: "/web/tecnologia/ciencia-de-dados"
            }
        ]
    },
    {
        area: "Engenharias",
        cursos: [
            {
                nome: "Engenharia Mecatrônica",
                rota: "/web/engenharias/engenharia-mecatronica"
            },
            {
                nome: "Engenharia Civil",
                rota: "/web/engenharias/engenharia-civil"
            },
            {
                nome: "Engenharia Mecânica",
                rota: "/web/engenharias/engenharia-mecanica"
            },
            {
                nome: "Engenharia de Produção",
                rota: "/web/engenharias/engenharia-producao"
            },
            {
                nome: "Engenharia Elétrica",
                rota: "/web/engenharias/engenharia-eletrica"
            },
            {
                nome: "Engenharia Química",
                rota: "/web/engenharias/engenharia-quimica"
            },
            {
                nome: "Engenharia Florestal",
                rota: "/web/engenharias/engenharia-florestal"
            },
            {
                nome: "Ciências Aeronáuticas",
                rota: "/web/engenharias/ciencias-aeronauticas"
            }
        ]
    },
    {
        area: "Saúde",
        cursos: [
            {
                nome: "Medicina",
                rota: "/web/saude/medicina"
            },
            {
                nome: "Odontologia",
                rota: "/web/saude/odontologia"
            },
            {
                nome: "Farmácia",
                rota: "/web/saude/farmacia"
            },
            {
                nome: "Fisioterapia",
                rota: "/web/saude/fisioterapia"
            },
            {
                nome: "Nutrição",
                rota: "/web/saude/nutricao"
            },
            {
                nome: "Biomedicina",
                rota: "/web/saude/biomedicina"
            },
            {
                nome: "Ciências Biológicas",
                rota: "/web/saude/biologia"
            },
            {
                nome: "Educação Física",
                rota: "/web/saude/educacao-fisica"
            },
            {
                nome: "Fonoaudiologia",
                rota: "/web/saude/fonoaudiologia"
            },
            {
                nome: "Terapia Ocupacional",
                rota: "/web/saude/terapia-ocupacional"
            },
            {
                nome: "Medicina Veterinária",
                rota: "/web/saude/medicina-veterinaria"
            }
        ]
    },
    {
        area: "Negócios",
        cursos: [
            {
                nome: "Administração",
                rota: "/web/negocios/administracao"
            },
            {
                nome: "Ciências Contábeis",
                rota: "/web/negocios/ciencias-contabeis"
            },
            {
                nome: "Ciências Econômicas",
                rota: "/web/negocios/ciencias-economicas"
            },
            {
                nome: "Marketing",
                rota: "/web/negocios/marketing"
            },
            {
                nome: "Gestão Comercial",
                rota: "/web/negocios/gestao-comercial"
            }
        ]
    },
    {
        area: "Humanas",
        cursos: [
            {
                nome: "Direito",
                rota: "/web/humanas/direito"
            },
            {
                nome: "Relações Internacionais",
                rota: "/web/humanas/relacoes-internacionais"
            }
        ]
    },
    {
        area: "Artes",
        cursos: [
            {
                nome: "Design",
                rota: "/web/artes/design"
            },
            {
                nome: "Publicidade e Propaganda",
                rota: "/web/artes/publicidade-propaganda"
            },
            {
                nome: "Artes Visuais",
                rota: "/web/artes/artes-visuais"
            },
            {
                nome: "Artes Cênicas",
                rota: "/web/artes/artes-cenicas"
            },
            {
                nome: "Design de Moda",
                rota: "/web/artes/moda"
            },
            {
                nome: "Fotografia",
                rota: "/web/artes/fotografia"
            }
        ]
    }
];

// --- TÓPICOS DO MENU CURSOS ---
const TOPICOS_CURSOS = [
    {
        id: "presencial",
        titulo: "Graduação presencial", areas: AREAS_CURSOS
    },
    {
        id: "ead",
        titulo: "Graduação EAD"
    },
    {
        id: "hibrida",
        titulo: "Graduação híbrida"
    },
    {
        id: "pos",
        titulo: "Pós-graduação"
    },
    {
        id: "mestrado",
        titulo: "Mestrado"
    },
    {
        id: "doutorado",
        titulo: "Doutorado"
    },
    {
        id: "modalidades",
        titulo: "Outras modalidades"
    }
];

const MEGA_UNIVERSIDADE = [
    {
        titulo: "Nossa História",
        descricao: "35 anos de excelência acadêmica",
        rota: "/web/sobre/historia"
    },
    {
        titulo: "Estrutura Física",
        descricao: "Salas, laboratórios e biblioteca",
        rota: "/web/sobre/estrutura-fisica"
    },
    {
        titulo: "Regimento Universitário",
        descricao: "Normas e diretrizes",
        rota: "/web/sobre/regimento-universitario"
    },
    {
        titulo: "Prêmios e Rankings",
        descricao: "Reconhecimentos nacionais",
        rota: "/web/sobre/premios-e-rankings"
    }
];

const MODALIDADES = [
    {
        titulo: "Cursos Técnicos",
        descricao: "Formação profissional rápida"
    },
    {
        titulo: "Idiomas",
        descricao: "Inglês, espanhol e libras"
    },
    {
        titulo: "Extensão Universitária",
        descricao: "Projetos comunitários"
    },
    {
        titulo: "Pesquisa",
        descricao: "Grupos e laboratórios"
    }
];

// --- MENU SUSPENSO ---
function MenuSuspenso({ chave, rotulo, abertoChave, definirAberto, classe = "", children }) {
    const aberto = abertoChave === chave;

    return (
        <div className="ua-nav-item" onMouseEnter={() => definirAberto(chave)} onMouseLeave={() => definirAberto(null)}>
            <button type="button" className="ua-nav-trigger" aria-expanded={aberto}
                    aria-haspopup="true" onClick={() => definirAberto(aberto ? null : chave)}>
                {rotulo}
                <i className="pi pi-chevron-down ua-nav-seta" aria-hidden="true" />
            </button>
            {aberto && <div className={`ua-nav-painel ${classe}`}>{children(() => definirAberto(null))}</div>}
        </div>
    );
}

// --- PAINEL DE CURSOS ---
function PainelCursos({ fechar }) {
    const [topicoId, setTopicoId] = useState("presencial");
    const topico = TOPICOS_CURSOS.find((item) => item.id === topicoId);

    return (
        <>
            <div className="ua-painel-topicos" role="tablist" aria-label="Tópicos de cursos">
                {TOPICOS_CURSOS.map((item) => (
                    <button key={item.id} type="button" role="tab" aria-selected={item.id === topicoId} className="ua-painel-topico"
                            onMouseEnter={() => setTopicoId(item.id)} onFocus={() => setTopicoId(item.id)} onClick={() => setTopicoId(item.id)}>
                        {item.titulo}
                    </button>
                ))}
            </div>

            <div className="ua-painel-conteudo">
                {topico.areas && topico.areas.map((grupo) => (
                    <section key={grupo.area} className="ua-painel-bloco">
                        <h3>{grupo.area}</h3>
                        <ul>
                            {grupo.cursos.map((curso) => (
                                <li key={curso.rota}>
                                    <Link href={curso.rota} onClick={fechar}>{curso.nome}</Link>
                                </li>
                            ))}
                        </ul>
                    </section>
                ))}

                {topico.id === "modalidades" && (
                    <section className="ua-painel-bloco">
                        <h3>{topico.titulo}</h3>
                        <ul>
                            {MODALIDADES.map((item) => (
                                <li key={item.titulo}>
                                    <span className="ua-painel-texto">{item.titulo}</span>
                                    <small className="ua-painel-texto"> — {item.descricao}</small>
                                </li>
                            ))}
                        </ul>
                    </section>
                )}

                {!topico.areas && topico.id !== "modalidades" && (
                    <p className="ua-painel-texto">Cursos em breve.</p>
                )}
            </div>
        </>
    );
}

// --- COMPONENTE DE CABEÇALHO DO SITE ---
export default function SiteHeader() {
    const router = useRouter();
    const [tema,        setTema]        = useState("light");
    const [abertoChave, setAbertoChave] = useState(null);

    // --- CARREGA O TEMA SALVO ---
    useEffect(() => {
        const salvo = localStorage.getItem("theme") || "light";
        setTema(salvo);
        document.documentElement.setAttribute("data-theme", salvo);
    }, []);

    // --- FECHA O MENU COM A TECLA ESC ---
    useEffect(() => {
        const aoTeclar = (evento) => {
            if (evento.key === "Escape") {
                setAbertoChave(null);
            }
        };
        document.addEventListener("keydown", aoTeclar);
        return () => document.removeEventListener("keydown", aoTeclar);
    }, []);

    // --- ALTERNA O TEMA COM UMA ONDA CIRCULAR EXPANSIVA A PARTIR DO BOTÃO ---
    const alternarTema = (evento) => {
        const botao = evento.currentTarget;
        const novo = tema === "dark" ? "light" : "dark";
        const retangulo = botao.getBoundingClientRect();
        const onda = document.createElement("span");

        onda.className = "site-tema-onda";
        onda.style.left = `${retangulo.left + retangulo.width / 2}px`;
        onda.style.top = `${retangulo.top + retangulo.height / 2}px`;
        onda.dataset.tema = novo;
        document.body.appendChild(onda);
        onda.addEventListener("animationend", () => onda.remove(), { once: true });

        botao.classList.add("girando");
        setTimeout(() => botao.classList.remove("girando"), 600);

        setTimeout(() => {
            document.documentElement.setAttribute("data-theme", novo);
            localStorage.setItem("theme", novo);
            setTema(novo);
        }, 150);
    };

    const logo = (
        <a href="/web/home" className="site-brand" aria-label="UniAura - Página inicial" onClick={(e) => { e.preventDefault(); router.push("/web/home"); }}>
            <img
                src="/uniaura/app/img/uniaura.png"
                alt=""
                className="site-brand-logo"
            />
            <span className="site-brand-text"><strong>UniAura</strong></span>
        </a>
    );

    const acoes = (
        <div className="site-actions">
            <Button
                icon={tema === "dark" ? "pi pi-sun" : "pi pi-moon"}
                rounded
                text
                className="site-tema-toggle"
                aria-label="Alternar tema claro e escuro"
                onClick={alternarTema}
            />
        </div>
    );

    return (
        <header id="siteHeader" className="site-header">
            <div className="site-container ua-nav">
                {logo}

                <nav aria-label="Principal">
                    <ul className="ua-nav-lista">
                        <li>
                            <MenuSuspenso chave="universidade" rotulo="A Universidade" classe="ua-nav-painel-compacto" abertoChave={abertoChave} definirAberto={setAbertoChave}>
                                {(fechar) => (
                                    <div className="ua-painel-conteudo ua-painel-lista">
                                        {MEGA_UNIVERSIDADE.map((item) => (
                                            <Link key={item.rota} href={item.rota} onClick={fechar} className="ua-painel-item">
                                                <strong>{item.titulo}</strong>
                                                <small>{item.descricao}</small>
                                            </Link>
                                        ))}
                                    </div>
                                )}
                            </MenuSuspenso>
                        </li>
                        <li>
                            <MenuSuspenso chave="cursos" rotulo="Cursos" abertoChave={abertoChave} definirAberto={setAbertoChave}>
                                {(fechar) => <PainelCursos fechar={fechar} />}
                            </MenuSuspenso>
                        </li>
                        <li>
                            <MenuSuspenso chave="alunos" rotulo="Alunos" classe="ua-nav-painel-compacto" abertoChave={abertoChave} definirAberto={setAbertoChave}>
                                {(fechar) => (
                                    <div className="ua-painel-conteudo ua-painel-lista">
                                        <Link href="/portal-do-aluno/login" onClick={fechar} className="ua-painel-item">
                                            <strong>Portal do Aluno</strong>
                                            <small>Notas, frequência e matrícula</small>
                                        </Link>
                                        <span className="ua-painel-item ua-painel-texto">
                                            <strong>EAD</strong>
                                            <small>Em breve</small>
                                        </span>
                                        <span className="ua-painel-item ua-painel-texto">
                                            <strong>Pós-Graduação</strong>
                                            <small>Em breve</small>
                                        </span>
                                    </div>
                                )}
                            </MenuSuspenso>
                        </li>
                        <li>
                            <Link className="ua-nav-link" href="/web/noticias">Notícias</Link>
                        </li>
                        <li>
                            <Link className="ua-nav-link" href="/web/contato">Contato</Link>
                        </li>
                    </ul>
                </nav>

                {acoes}
            </div>
        </header>
    );
}
