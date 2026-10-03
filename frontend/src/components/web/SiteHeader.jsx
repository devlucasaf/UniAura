"use client";

import { useEffect, useState }  from "react";
import { useRouter }            from "next/navigation";
import { MegaMenu }             from "primereact/megamenu";
import { Button }               from "primereact/button";

// --- CURSOS DE GRADUAÇÃO DO SUBMENU ---
const CURSOS_GRADUACAO = [
    { nome: "Ciência da Computação", rota: "/tecnologia/ciencia-da-computacao" },
    { nome: "Análise e Desenvolvimento de Sistemas", rota: "/tecnologia/analise-e-desenvolvimento-de-sistemas" },
    { nome: "Engenharia de Software", rota: "/tecnologia/engenharia-de-software" },
    { nome: "Ciência de Dados", rota: "/tecnologia/ciencia-de-dados" },
    { nome: "Engenharia Mecatrônica", rota: "/engenharias/engenharia-mecatronica" },
    { nome: "Engenharia Civil", rota: "/engenharias/engenharia-civil" },
    { nome: "Engenharia Mecânica", rota: "/engenharias/engenharia-mecanica" },
    { nome: "Engenharia de Produção", rota: "/engenharias/engenharia-producao" },
    { nome: "Engenharia Elétrica", rota: "/engenharias/engenharia-eletrica" },
    { nome: "Engenharia Química", rota: "/engenharias/engenharia-quimica" },
    { nome: "Engenharia Florestal", rota: "/engenharias/engenharia-florestal" },
    { nome: "Ciências Aeronáuticas", rota: "/engenharias/ciencias-aeronauticas" },
    { nome: "Medicina", rota: "/saude/medicina" },
    { nome: "Odontologia", rota: "/saude/odontologia" },
    { nome: "Farmácia", rota: "/saude/farmacia" },
    { nome: "Fisioterapia", rota: "/saude/fisioterapia" },
    { nome: "Nutrição", rota: "/saude/nutricao" },
    { nome: "Biomedicina", rota: "/saude/biomedicina" },
    { nome: "Ciências Biológicas", rota: "/saude/biologia" },
    { nome: "Educação Física", rota: "/saude/educacao-fisica" },
    { nome: "Fonoaudiologia", rota: "/saude/fonoaudiologia" },
    { nome: "Terapia Ocupacional", rota: "/saude/terapia-ocupacional" },
    { nome: "Medicina Veterinária", rota: "/saude/medicina-veterinaria" },
    { nome: "Design", rota: "/artes/design" },
    { nome: "Publicidade e Propaganda", rota: "/artes/publicidade-propaganda" },
    { nome: "Artes Visuais", rota: "/artes/artes-visuais" },
    { nome: "Artes Cênicas", rota: "/artes/artes-cenicas" },
    { nome: "Design de Moda", rota: "/artes/moda" },
    { nome: "Fotografia", rota: "/artes/fotografia" },
    { nome: "Administração", rota: "/negocios/administracao" },
    { nome: "Direito", rota: "/humanas/direito" }
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

// --- COMPONENTE DE CABEÇALHO DO SITE ---
export default function SiteHeader({ ancoras = false }) {
    const router = useRouter();
    const [tema, setTema] = useState("light");

    // --- CARREGA O TEMA SALVO ---
    useEffect(() => {
        const salvo = localStorage.getItem("theme") || "light";
        setTema(salvo);
        document.documentElement.setAttribute("data-theme", salvo);
    }, []);

    // --- ALTERNA ENTRE TEMA CLARO E ESCURO ---
    const alternarTema = () => {
        const novo = tema === "dark" ? "light" : "dark";
        document.documentElement.setAttribute("data-theme", novo);
        localStorage.setItem("theme", novo);
        setTema(novo);
    };

    // --- ROLA SUAVEMENTE PARA UMA ÂNCORA DA PÁGINA INICIAL ---
    function irOuRolar(idAlvo, rotaAlternativa) {
        if (ancoras) {
            document.getElementById(idAlvo)?.scrollIntoView({ behavior: "smooth", block: "start" });
        } else {
            router.push(rotaAlternativa);
        }
    }

    const itemFolha = (titulo, descricao, rota) => ({
        label: titulo,
        subtext: descricao,
        command: () => router.push(rota || "#")
    });

    const items = [
        {
            label: "A Universidade",
            command: () => irOuRolar("sobre", "/sobre/historia"),
            items: [[
                {
                    label: "Sobre a UniAura",
                    items: MEGA_UNIVERSIDADE.map((item) => itemFolha(item.titulo, item.descricao, item.rota))
                }
            ]]
        },
        {
            label: "Cursos",
            command: () => irOuRolar("ensino", "/graduacao/cursos"),
            items: [
                [
                    {
                        label: "Graduação",
                        items: CURSOS_GRADUACAO.map((curso) => ({ label: curso.nome, command: () => router.push(curso.rota) }))
                    }
                ],
                [
                    {
                        label: "Outras modalidades",
                        items: MEGA_MODALIDADES.map((item) => itemFolha(item.titulo, item.descricao, item.rota))
                    }
                ]
            ]
        },
        { label: "Notícias", command: () => router.push("/") },
        { label: "Contato", command: () => router.push("/contato") },
        { label: "Portal do Aluno", command: () => router.push("/portal-do-aluno/login") }
    ];

    const logo = (
        <a href="/" className="site-brand" aria-label="UniAura - Página inicial" onClick={(e) => { e.preventDefault(); router.push("/"); }}>
            <img src="/img/uniaura.png" alt="" className="site-brand-logo" />
            <span className="site-brand-text"><strong>UniAura</strong></span>
        </a>
    );

    const acoes = (
        <div className="site-actions">
            <Button
                icon={tema === "dark" ? "pi pi-sun" : "pi pi-moon"}
                rounded
                text
                aria-label="Alternar tema claro e escuro"
                onClick={alternarTema}
            />
            <Button label="Portal do Aluno" onClick={() => router.push("/portal-do-aluno/login")} />
        </div>
    );

    return (
        <header id="siteHeader" className="site-header">
            <div className="site-container">
                <MegaMenu model={items} orientation="horizontal" start={logo} end={acoes} breakpoint="960px" />
            </div>
        </header>
    );
}
