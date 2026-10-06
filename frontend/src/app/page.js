"use client";

import { useEffect, useState } from "react";
import { useRouter }           from "next/navigation";
import { Dropdown }            from "primereact/dropdown";

// --- MÓDULOS DO ERP EXIBIDOS NA CENTRAL (a classe `modulo-<id>` define a cor do ícone em erp-central.css) ---
// Módulos sem `rota` ainda não têm tela e aparecem como "Em breve".
const MODULOS = [
    {
        id: "web",
        nome: "Web",
        categoria: "Institucional",
        descricao: "Acesse o portal institucional, notícias, cursos e informações da universidade.",
        busca: "Portal institucional notícias cursos e contato",
        rota: "/web/home",
        icone: (
            <>
                <rect x="3" y="4" width="18" height="16" rx="2"></rect>
                <path d="M3 9h18"></path>
                <path d="M8 4v5"></path>
            </>
        )
    },
    {
        id: "biblioteca",
        nome: "Biblioteca",
        categoria: "Acadêmico",
        descricao: "Consulte o acervo, empréstimos, reservas, multas e exemplares disponíveis.",
        busca: "Acervo empréstimos reservas multas livros",
        rota: "/biblioteca/dashboard",
        icone: (
            <>
                <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
                <path d="M4 4.5A2.5 2.5 0 0 1 6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5z"></path>
            </>
        )
    },
    {
        id: "secretaria",
        nome: "Secretaria",
        categoria: "Administrativo",
        descricao: "Gerencie alunos, documentos, vínculos, solicitações e registros acadêmicos.",
        busca: "Alunos documentos vínculos solicitações registros",
        rota: "/secretaria/dashboard",
        icone: (
            <>
                <path d="M9 5H6a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-3"></path>
                <rect x="9" y="3" width="6" height="4" rx="1"></rect>
                <path d="M8 12h8M8 16h6"></path>
            </>
        )
    },
    {
        id: "coordenacao",
        nome: "Coordenação",
        categoria: "Gestão",
        descricao: "Acompanhe cursos, turmas, disciplinas, matrizes curriculares e indicadores.",
        busca: "Cursos turmas disciplinas matrizes indicadores",
        rota: "/coordenacao/dashboard",
        icone: (
            <>
                <path d="M3 3v18h18"></path>
                <path d="M7 15l4-4 3 3 5-6"></path>
            </>
        )
    },
    {
        id: "professor",
        nome: "Professor",
        categoria: "Acadêmico",
        descricao: "Acesse turmas, conteúdos, atividades, frequência, avaliações e notas.",
        busca: "Turmas conteúdos atividades frequência avaliações notas",
        rota: "/professor/dashboard",
        icone: (
            <>
                <path d="M22 10 12 4 2 10l10 6 10-6Z"></path>
                <path d="M6 12v5c3 3 9 3 12 0v-5"></path>
            </>
        )
    },
    {
        id: "espaco-aluno",
        nome: "Espaço do Aluno",
        categoria: "Acadêmico",
        descricao: "Acompanhe notas, frequência, empréstimos e reservas em um só lugar.",
        busca: "Aluno notas frequência empréstimos reservas painel",
        rota: "/portal-do-aluno/dashboard",
        icone: (
            <>
                <circle cx="12" cy="8" r="4"></circle>
                <path d="M4 21c0-4 4-6 8-6s8 2 8 6"></path>
            </>
        )
    },
    {
        id: "financeiro",
        nome: "Financeiro",
        categoria: "Administrativo",
        descricao: "Controle mensalidades, boletos, pagamentos, bolsas e relatórios financeiros.",
        busca: "Mensalidades boletos pagamentos bolsas multas relatórios",
        rota: "/financeiro/dashboard",
        icone: (
            <>
                <rect x="2" y="6" width="20" height="12" rx="2"></rect>
                <circle cx="12" cy="12" r="2.5"></circle>
                <path d="M6 10v.01M18 14v.01"></path>
            </>
        )
    },
    {
        id: "processos",
        nome: "Processos",
        categoria: "Gestão",
        descricao: "Abra, tramite e responda processos da faculdade: solicitações de alunos, pedidos de férias e encaminhamentos entre áreas.",
        busca: "Processos tramitação solicitações pedidos férias encaminhamento protocolo setores",
        rota: null,
        icone: (
            <>
                <circle cx="6" cy="6" r="2.5"></circle>
                <circle cx="18" cy="12" r="2.5"></circle>
                <circle cx="6" cy="18" r="2.5"></circle>
                <path d="M8.5 6H13a3 3 0 0 1 3 3v.5M8.5 18H13a3 3 0 0 0 3-3v-.5"></path>
            </>
        )
    },
    {
        id: "ouvidoria",
        nome: "Ouvidoria",
        categoria: "Serviços",
        descricao: "Registre e acompanhe reclamações, denúncias, sugestões e elogios com protocolo.",
        busca: "Ouvidoria reclamações denúncias sugestões elogios protocolo atendimento",
        rota: null,
        icone: (
            <>
                <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                <path d="M8 9h8M8 13h5"></path>
            </>
        )
    },
    {
        id: "aplicativo",
        nome: "Aplicativo",
        categoria: "Serviços",
        descricao: "Conheça e acesse o aplicativo acadêmico móvel da Universidade Aura.",
        busca: "Aplicativo acadêmico móvel download estudante",
        rota: null,
        icone: (
            <>
                <rect x="7" y="2" width="10" height="20" rx="2"></rect>
                <path d="M11 18h2"></path>
            </>
        )
    },
    {
        id: "matriculas",
        nome: "Matrículas",
        categoria: "Acadêmico",
        descricao: "Realize matrículas, consulte ofertas e acompanhe solicitações acadêmicas.",
        busca: "Matrículas ofertas disciplinas solicitações acadêmicas",
        rota: "/web/matriculas",
        icone: (
            <>
                <path d="M14 3H7a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8l-5-5Z"></path>
                <path d="M14 3v5h5"></path>
                <path d="m9 15 2 2 4-4"></path>
            </>
        )
    }
];

// --- PERFIS DE USO E OS MÓDULOS QUE CADA UM ACESSA (`todos` = admin enxerga tudo) ---
const PERFIS = [
    { value: "admin",        label: "Administrador", modulos: "todos" },
    { value: "aluno",        label: "Aluno",         modulos: ["web", "biblioteca", "aplicativo", "matriculas", "espaco-aluno", "processos", "ouvidoria"] },
    { value: "professor",    label: "Professor",     modulos: ["web", "professor", "aplicativo", "processos", "ouvidoria"] },
    { value: "secretario",   label: "Secretário(a)", modulos: ["web", "secretaria", "matriculas", "processos", "ouvidoria"] },
    { value: "coordenador",  label: "Coordenador(a)", modulos: ["coordenacao", "web", "aplicativo", "processos", "ouvidoria"] },
    { value: "financeiro",   label: "Financeiro",    modulos: ["financeiro", "processos", "ouvidoria"] },
    { value: "bibliotecario", label: "Bibliotecário(a)", modulos: ["web", "biblioteca", "processos", "ouvidoria"] }
];

const CHAVE_PERFIL = "perfil-central-erp";

// --- NORMALIZA O TEXTO PARA PESQUISAR SEM DIFERENÇA DE ACENTOS OU MAIÚSCULAS ---
const normalizar = (texto) => texto.normalize("NFD").replace(/[̀-ͯ]/g, "").toLowerCase();

// --- CENTRAL DE ACESSO AOS MÓDULOS DO ERP (RAIZ DO SISTEMA) ---
export default function CentralErpPage() {
    const router = useRouter();
    const [busca, setBusca] = useState("");
    const [tema, setTema] = useState("light");
    const [perfil, setPerfil] = useState(null);

    // --- CARREGA O TEMA SALVO (O MESMO DO SITE, CHAVE "theme") ---
    useEffect(() => {
        const salvo = localStorage.getItem("theme") || "light";
        setTema(salvo);
        document.documentElement.setAttribute("data-theme", salvo);
        setPerfil(localStorage.getItem(CHAVE_PERFIL));
    }, []);

    // --- GUARDA O PERFIL ESCOLHIDO PARA A PRÓXIMA VISITA ---
    const escolherPerfil = (valor) => {
        setPerfil(valor);
        localStorage.setItem(CHAVE_PERFIL, valor);
    };

    // --- ALTERNA ENTRE TEMA CLARO E ESCURO ---
    const alternarTema = () => {
        const novo = tema === "dark" ? "light" : "dark";
        document.documentElement.setAttribute("data-theme", novo);
        localStorage.setItem("theme", novo);
        setTema(novo);
    };

    const termo = normalizar(busca.trim());
    const perfilAtual = PERFIS.find((item) => item.value === perfil);
    const permitidos = !perfilAtual
        ? []
        : MODULOS.filter((modulo) => perfilAtual.modulos === "todos" || perfilAtual.modulos.includes(modulo.id));
    const visiveis = permitidos.filter((modulo) =>
        normalizar(`${modulo.nome} ${modulo.categoria} ${modulo.busca}`).includes(termo)
    );

    return (
        <div className="pagina">
            <header className="cabecalho">
                <div className="container cabecalho-conteudo">
                    <a className="marca" href="/uniaura/app" aria-label="Universidade Aura de Xique Xique - Central de acesso">
                        <span className="marca-icone" aria-hidden="true">
                            <svg viewBox="0 0 24 24" width="26" height="26" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                                <path d="M3 21h18"></path>
                                <path d="M5 21V10"></path>
                                <path d="M19 21V10"></path>
                                <path d="M9 21v-6h6v6"></path>
                                <path d="m3 10 9-7 9 7"></path>
                            </svg>
                        </span>
                        <span className="marca-texto">
                            <strong>Universidade Aura</strong>
                            <small>de Xique Xique</small>
                        </span>
                    </a>

                    <div className="cabecalho-acoes">
                    <button
                        className="botao-tema"
                        type="button"
                        aria-label={tema === "dark" ? "Ativar tema claro" : "Ativar tema escuro"}
                        title="Alternar tema"
                        onClick={alternarTema}
                    >
                        <span className="icone-sol" aria-hidden="true">
                            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                <circle cx="12" cy="12" r="4"></circle>
                                <path d="M12 2v2M12 20v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M2 12h2M20 12h2M4.93 19.07l1.41-1.41M17.66 6.34l1.41-1.41"></path>
                            </svg>
                        </span>
                        <span className="icone-lua" aria-hidden="true">
                            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path>
                            </svg>
                        </span>
                    </button>

                        <Dropdown
                            value={perfil}
                            options={PERFIS}
                            onChange={(e) => escolherPerfil(e.value)}
                            placeholder="Escolha seu perfil"
                            className="seletor-perfil"
                            aria-label="Tipo de usuário"
                        />
                    </div>
                </div>
            </header>

            <main>
                <section className="apresentacao">
                    <div className="container">
                        <div className="apresentacao-conteudo">
                            <span className="etiqueta">Sistema integrado de gestão universitária</span>
                            <h1>Qual ambiente você deseja acessar?</h1>
                            <p>Selecione um dos módulos disponíveis para continuar no ERP da Universidade Aura de Xique Xique.</p>
                        </div>

                        <div className="pesquisa">
                            <label className="sr-only" htmlFor="pesquisaModulo">Pesquisar um módulo</label>
                            <span className="pesquisa-icone" aria-hidden="true">
                                <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                    <circle cx="11" cy="11" r="8"></circle>
                                    <path d="m21 21-4.35-4.35"></path>
                                </svg>
                            </span>
                            <input
                                id="pesquisaModulo"
                                type="search"
                                placeholder="Pesquisar um módulo..."
                                autoComplete="off"
                                value={busca}
                                onChange={(e) => setBusca(e.target.value)}
                            />
                        </div>
                    </div>
                </section>

                <section className="modulos" aria-labelledby="tituloModulos">
                    <div className="container">
                        <div className="secao-cabecalho">
                            <div>
                                <span className="secao-sobretitulo">Ambientes disponíveis</span>
                                <h2 id="tituloModulos">Central de acesso</h2>
                            </div>
                            <p className="contador-modulos" aria-live="polite">
                                {visiveis.length === 1 ? "1 módulo disponível" : `${visiveis.length} módulos disponíveis`}
                            </p>
                        </div>

                        <div className="grade-modulos">
                            {visiveis.map((modulo) => (
                                <article key={modulo.id} className="cartao-modulo">
                                    <div className="cartao-topo">
                                        <span className={`modulo-icone modulo-${modulo.id}`} aria-hidden="true">
                                            <svg viewBox="0 0 24 24" width="28" height="28" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                                                {modulo.icone}
                                            </svg>
                                        </span>
                                        <span className="categoria">{modulo.categoria}</span>
                                    </div>
                                    <h3>{modulo.nome}</h3>
                                    <p>{modulo.descricao}</p>
                                    <button
                                        className="botao-acesso"
                                        type="button"
                                        disabled={!modulo.rota}
                                        onClick={() => modulo.rota && router.push(modulo.rota)}
                                    >
                                        {modulo.rota ? "Acessar módulo" : "Em breve"}
                                        {modulo.rota && <span aria-hidden="true">→</span>}
                                    </button>
                                </article>
                            ))}
                        </div>

                        {visiveis.length === 0 && (
                            <div className="mensagem-vazia">
                                {perfilAtual ? (
                                    <>
                                        <strong>Nenhum módulo encontrado.</strong>
                                        <span>Tente pesquisar utilizando outro nome ou categoria.</span>
                                    </>
                                ) : (
                                    <>
                                        <strong>Escolha seu perfil para começar.</strong>
                                        <span>Use o seletor no topo da página para ver os módulos disponíveis para você.</span>
                                    </>
                                )}
                            </div>
                        )}
                    </div>
                </section>
            </main>

            <footer className="rodape">
                <div className="container rodape-conteudo">
                    <p>© 2026 Universidade Aura de Xique Xique</p>
                    <p>Central de acesso aos módulos do ERP UAXX</p>
                </div>
            </footer>
        </div>
    );
}
