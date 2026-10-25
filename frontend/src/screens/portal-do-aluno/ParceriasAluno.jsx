"use client";

import { Tag } from "primereact/tag";

// --- PARCERIAS FICTÍCIAS ---
const PARCERIAS = [
    { 
        id: 1, 
        nome: "TechNova Soluções", 
        categoria: "Estágio e trainee", 
        icone: "pi pi-building", 
        beneficio: "Vagas exclusivas de estágio e programa de trainee em desenvolvimento e dados para alunos da UniAura." 
    },
    { 
        id: 2, 
        nome: "Banco Aurora", 
        categoria: "Carreira", 
        icone: "pi pi-wallet", 
        beneficio: "Programa de jovens talentos com mentoria, e conta universitária sem tarifas durante o curso." 
    },
    { 
        id: 3, 
        nome: "Hospital Vida", 
        categoria: "Estágio", 
        icone: "pi pi-heart", 
        beneficio: "Convênio de estágio supervisionado para os cursos da área da saúde." 
    },
    { 
        id: 4, 
        nome: "Global English", 
        categoria: "Idiomas", 
        icone: "pi pi-globe", 
        beneficio: "Bolsa de 30% em cursos de inglês e espanhol, com turmas em horários flexíveis." 
    },
    { 
        id: 5, 
        nome: "Livraria Saber", 
        categoria: "Descontos", 
        icone: "pi pi-book", 
        beneficio: "15% de desconto em livros técnicos e acadêmicos, na loja física e na loja on-line." 
    },
    { 
        id: 6, 
        nome: "FitCampus Academia", 
        categoria: "Bem-estar", 
        icone: "pi pi-bolt", 
        beneficio: "Mensalidade reduzida e acesso a aulas coletivas para alunos e colaboradores." 
    }
];

// --- PÁGINA PARCERIAS ---
export default function ParceriasAluno() {
    return (
        <div className="ua-aluno-home">
            <div className="ua-aluno-secao-cabecalho">
                <h1>Parcerias</h1>
                <span className="muted">Benefícios para alunos da UniAura (parcerias fictícias de exemplo)</span>
            </div>

            <div className="ua-aluno-cartoes">
                {PARCERIAS.map((parceria) => (
                    <article key={parceria.id} className="ua-card ua-aluno-carreira">
                        <div className="ua-aluno-carreira__topo">
                            <span className="ua-aluno-carreira__icone" aria-hidden="true">
                                <i className={parceria.icone} />
                            </span>
                            <h2>{parceria.nome}</h2>
                        </div>
                        <Tag value={parceria.categoria} rounded />
                        <p className="muted">{parceria.beneficio}</p>
                    </article>
                ))}
            </div>
        </div>
    );
}
