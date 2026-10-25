// --- MENU DO PAINEL LATERAL DO PORTAL DO ALUNO ---
export const MENU_ALUNO = [
    {
        id: "home",
        rotulo: "Home",
        icone: "pi pi-home",
        rota: "/portal-do-aluno/home"
    },
    {
        id: "academico",
        rotulo: "Acadêmico",
        icone: "pi pi-graduation-cap",
        base: "/portal-do-aluno/academico",
        itens: [
            { 
                slug: "atividades-complementares", 
                rotulo: "Atividades complementares", 
                icone: "pi pi-check-square", 
                descricao: "Acompanhe as horas de atividades complementares e envie comprovantes." 
            },
            { 
                slug: "avaliacao-academica", 
                rotulo: "Avaliação acadêmica", 
                icone: "pi pi-star", 
                descricao: "Avalie disciplinas e professores e veja o resultado das suas avaliações." 
            },
            { 
                slug: "calendario", 
                rotulo: "Calendário", 
                icone: "pi pi-calendar", 
                descricao: "Provas, entregas, feriados e eventos do calendário acadêmico." 
            },
            { 
                slug: "curriculo", 
                rotulo: "Currículo", 
                icone: "pi pi-sitemap", 
                descricao: "A matriz curricular do seu curso, semestre a semestre." 
            },
            { 
                slug: "disciplinas", 
                rotulo: "Disciplinas", 
                icone: "pi pi-book", 
                descricao: "As disciplinas que você cursa, com plano de ensino e materiais." 
            },
            { 
                slug: "eventos", 
                rotulo: "Eventos", 
                icone: "pi pi-ticket", 
                descricao: "Palestras, workshops e eventos da universidade, com inscrição." 
            },
            { 
                slug: "grade-horaria-curricular", 
                rotulo: "Grade horária curricular", 
                icone: "pi pi-table", 
                descricao: "Os horários e as salas das suas aulas ao longo da semana." 
            },
            { 
                slug: "informacoes", 
                rotulo: "Informações", 
                icone: "pi pi-info-circle", 
                descricao: "Informações gerais sobre o seu curso e a vida acadêmica." 
            },
            { 
                slug: "mensagens", 
                rotulo: "Mensagens", 
                icone: "pi pi-comments", 
                descricao: "Conversas com professores, coordenação e colegas, no estilo de mensagens instantâneas." 
            }
        ]
    },
    {
        id: "biblioteca",
        rotulo: "Biblioteca",
        icone: "pi pi-bookmark",
        rota: "/portal-do-aluno/biblioteca/consultar"
    },
    {
        id: "carreiras",
        rotulo: "Carreiras",
        icone: "pi pi-briefcase",
        rota: "/portal-do-aluno/carreiras"
    },
    {
        id: "matricula",
        rotulo: "Matrícula",
        icone: "pi pi-file-edit",
        rota: "/portal-do-aluno/matricula",
        descricao: "Rematrícula e matrícula em disciplinas a cada semestre."
    },
    {
        id: "parcerias",
        rotulo: "Parcerias",
        icone: "pi pi-share-alt",
        rota: "/portal-do-aluno/parcerias"
    },
    {
        id: "secretaria",
        rotulo: "Secretaria Virtual",
        icone: "pi pi-id-card",
        base: "/portal-do-aluno/secretaria-virtual",
        itens: [
            { 
                slug: "carteira-estudantil", 
                rotulo: "Carteira Estudantil", 
                icone: "pi pi-id-card", 
                descricao: "Solicite e acompanhe a emissão da sua carteira estudantil." 
            },
            { 
                slug: "declaracoes", 
                rotulo: "Declarações", 
                icone: "pi pi-file", 
                descricao: "Peça declarações de matrícula, de vínculo e de conclusão." 
            },
            { 
                slug: "informativos", 
                rotulo: "Informativos", 
                icone: "pi pi-megaphone", 
                descricao: "Comunicados e avisos oficiais da secretaria." 
            },
            { 
                slug: "ouvidoria", 
                rotulo: "Ouvidoria", 
                icone: "pi pi-comment", 
                descricao: "Registre reclamações, sugestões e elogios, com número de protocolo." 
            },
            { 
                slug: "processos", 
                rotulo: "Processos", 
                icone: "pi pi-folder-open", 
                descricao: "Abra e acompanhe processos, como solicitações e pedidos." 
            }
        ]
    },
    {
        id: "financeiro",
        rotulo: "Financeiro",
        icone: "pi pi-wallet",
        rota: "/portal-do-aluno/financeiro",
        descricao: "Mensalidades, boletos, pagamentos e histórico financeiro."
    }
];

// --- ACHA O ITEM PELO CAMINHO ---
export function acharItemDoMenu(caminho) {
    const rota = `/portal-do-aluno/${caminho.join("/")}`;

    for (const entrada of MENU_ALUNO) {
        if (entrada.rota === rota) {
            return { rotulo: entrada.rotulo, icone: entrada.icone, descricao: entrada.descricao, grupo: null };
        }
        
        const filho = entrada.itens?.find((item) => `${entrada.base}/${item.slug}` === rota);
        if (filho) {
            return { rotulo: filho.rotulo, icone: filho.icone, descricao: filho.descricao, grupo: entrada.rotulo };
        }
    }
    return null;
}
