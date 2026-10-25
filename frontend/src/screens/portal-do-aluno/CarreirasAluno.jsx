"use client";

import { Tag } from "primereact/tag";

// --- ÁREAS DE ATUAÇÃO DETALHADAS ---
const AREAS = [
    {
        id: "desenvolvimento",
        titulo: "Desenvolvimento de software",
        icone: "pi pi-code",
        resumo: "Projeta, programa e mantém sistemas web, mobile e corporativos, do requisito à entrega.",
        cargos: [
            "Desenvolvedor(a) back-end", 
            "Desenvolvedor(a) front-end", 
            "Desenvolvedor(a) full stack", 
            "Engenheiro(a) de software"
        ],
        habilidades: [
            "Lógica e algoritmos", 
            "Java / JavaScript", 
            "Banco de dados", 
            "Testes e versionamento"
        ]
    },
    {
        id: "dados",
        titulo: "Dados e inteligência artificial",
        icone: "pi pi-chart-bar",
        resumo: "Coleta, organiza e analisa dados para apoiar decisões e treinar modelos de aprendizado de máquina.",
        cargos: [
            "Analista de dados", 
            "Cientista de dados", 
            "Engenheiro(a) de dados", 
            "Especialista em IA"
        ],
        habilidades: [
            "Estatística", 
            "Python e SQL", 
            "Visualização de dados", 
            "Aprendizado de máquina"
        ]
    },
    {
        id: "seguranca",
        titulo: "Segurança da informação",
        icone: "pi pi-shield",
        resumo: "Protege sistemas, redes e dados contra ataques e vazamentos, e garante a conformidade com a LGPD.",
        cargos: [
            "Analista de segurança", 
            "Analista de conformidade (LGPD)", 
            "Pentester", 
            "Auditor(a) de TI"
        ],
        habilidades: [
            "Redes e criptografia", 
            "Análise de vulnerabilidades", 
            "Normas e legislação", 
            "Resposta a incidentes"
        ]
    },
    {
        id: "infraestrutura",
        titulo: "Infraestrutura, redes e nuvem",
        icone: "pi pi-cloud",
        resumo: "Mantém os ambientes onde os sistemas rodam, automatiza entregas e garante disponibilidade.",
        cargos: [
            "Analista de infraestrutura", 
            "Engenheiro(a) DevOps", 
            "Administrador(a) de redes", 
            "Arquiteto(a) de nuvem"
        ],
        habilidades: [
            "Linux e redes", 
            "Contêineres e CI/CD", 
            "Provedores de nuvem", 
            "Monitoramento"
        ]
    },
    {
        id: "gestao",
        titulo: "Gestão de TI e de produtos",
        icone: "pi pi-users",
        resumo: "Lidera equipes e define o que será construído, conectando a tecnologia aos objetivos do negócio.",
        cargos: [
            "Gerente de projetos", 
            "Product owner / manager", 
            "Analista de negócios", 
            "Gestor(a) de TI"
        ],
        habilidades: [
            "Metodologias ágeis", 
            "Comunicação", 
            "Planejamento", 
            "Visão de negócio"
        ]
    },
    {
        id: "pesquisa",
        titulo: "Pesquisa e docência",
        icone: "pi pi-book",
        resumo: "Produz conhecimento novo e forma novos profissionais, em universidades e centros de pesquisa.",
        cargos: [
            "Pesquisador(a)", 
            "Professor(a) universitário(a)", 
            "Analista de P&D", 
            "Mestrando / doutorando"
        ],
        habilidades: [
            "Método científico", 
            "Escrita acadêmica", 
            "Pensamento crítico", 
            "Pós-graduação"
        ]
    }
];

// --- PÁGINA CARREIRAS ---
export default function CarreirasAluno() {
    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-secao-cabecalho">
                <h1>Carreiras</h1>
                <span className="muted">Possíveis áreas de atuação (conteúdo de exemplo)</span>
            </div>

            <div className="ua-aluno-cartoes">
                {AREAS.map((area) => (
                    <article key={area.id} className="ua-card ua-aluno-carreira">
                        <div className="ua-aluno-carreira__topo">
                            <span className="ua-aluno-carreira__icone" aria-hidden="true"><i className={area.icone} /></span>
                            <h2>{area.titulo}</h2>
                        </div>
                        <p className="muted">{area.resumo}</p>

                        <h3>Principais cargos</h3>
                        <ul className="ua-aluno-lista">
                            {area.cargos.map((cargo) => <li key={cargo}>{cargo}</li>)}
                        </ul>

                        <h3>Habilidades</h3>
                        <div className="ua-aluno-tags">
                            {area.habilidades.map((habilidade) => <Tag key={habilidade} value={habilidade} rounded />)}
                        </div>
                    </article>
                ))}
            </div>
        </div>
    );
}
