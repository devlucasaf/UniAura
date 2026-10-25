// --- SOMA DIAS A HOJE ---
const emDias = (dias) => {
    const data = new Date();

    data.setHours(0, 0, 0, 0);
    data.setDate(data.getDate() + dias);

    return data;
};

// --- CHAVE aaaa-mm-dd DE UMA DATA ---
export const chaveDoDia = (data) => {
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const dia = String(data.getDate()).padStart(2, "0");
    
    return `${data.getFullYear()}-${mes}-${dia}`;
};

// --- NOTÍCIAS DO MURAL ---
export const NOTICIAS = [
    {
        id: 1,
        categoria: "Secretaria",
        titulo: "Rematrícula do próximo semestre está aberta",
        resumo: "Confirme sua rematrícula pelo portal até o fim do mês e garanta suas disciplinas.",
        data: emDias(-1)
    },
    {
        id: 2,
        categoria: "Biblioteca",
        titulo: "Novo acervo digital disponível",
        resumo: "Mais de 2.000 e-books das áreas de tecnologia, saúde e negócios já podem ser consultados.",
        data: emDias(-3)
    },
    {
        id: 3,
        categoria: "Eventos",
        titulo: "Semana de Ciência e Tecnologia",
        resumo: "Palestras, workshops e feira de projetos. As inscrições para apresentar trabalhos vão até sexta.",
        data: emDias(-5)
    },
    {
        id: 4,
        categoria: "Financeiro",
        titulo: "Prazo para negociação de mensalidades",
        resumo: "Quem tem mensalidades em atraso pode negociar com condições especiais até o dia 30.",
        data: emDias(-7)
    }
];

// --- DIAS DA GRADE, DE SEGUNDA A SÁBADO ---
const DIAS_DA_GRADE = [
    "Segunda-feira",
    "Terça-feira",
    "Quarta-feira",
    "Quinta-feira",
    "Sexta-feira",
    "Sábado"
];

// --- HORÁRIOS DAS AULAS ---
export const HORARIOS = [
    "08:00 – 09:40",
    "10:00 – 11:40",
    "19:00 – 20:40",
    "21:00 – 22:40"
];

// --- NOTIFICAÇÕES DO SININHO DO CABEÇALHO ---
export const NOTIFICACOES = [
    {
        id: 1,
        icone: "pi pi-megaphone",
        titulo: "Rematrícula aberta",
        texto: "Confirme sua rematrícula até o fim do mês.",
        quando: "há 1 dia"
    },
    {
        id: 2,
        icone: "pi pi-calendar",
        titulo: "Avaliações do semestre",
        texto: "Acompanhe as datas das provas com os seus professores.",
        quando: "há 2 dias"
    },
    {
        id: 3,
        icone: "pi pi-book",
        titulo: "Devolução de livro",
        texto: "O livro emprestado vence em 5 dias.",
        quando: "há 3 dias"
    },
    {
        id: 4,
        icone: "pi pi-wallet",
        titulo: "Mensalidade",
        texto: "Seu próximo vencimento é dia 10.",
        quando: "há 4 dias"
    }
];

// --- TÓPICOS E INDICADORES DOS DASHBOARDS ---
export const DASHBOARDS = [
    {
        id: "academico",
        titulo: "Desempenho acadêmico",
        icone: "pi pi-chart-line",
        indicadores: [
            {
                rotulo: "Média geral",
                valor: "—"
            },
            {
                rotulo: "Frequência",
                valor: "—"
            },
            {
                rotulo: "Disciplinas em curso",
                valor: "—"
            },
            {
                rotulo: "Créditos concluídos",
                valor: "—"
            }
        ]
    },
    {
        id: "financeiro",
        titulo: "Financeiro",
        icone: "pi pi-wallet",
        indicadores: [
            {
                rotulo: "Situação",
                valor: "Em dia"
            },
            {
                rotulo: "Próximo vencimento",
                valor: "10/11"
            },
            {
                rotulo: "Valor da mensalidade",
                valor: "R$ 890,00"
            },
            {
                rotulo: "Parcelas pagas no ano",
                valor: "10 / 12",
                progresso: 83
            }
        ]
    },
    {
        id: "biblioteca",
        titulo: "Biblioteca",
        icone: "pi pi-book",
        indicadores: [
            {
                rotulo: "Empréstimos ativos",
                valor: "2"
            },
            {
                rotulo: "Reservas",
                valor: "1"
            },
            {
                rotulo: "Próxima devolução",
                valor: "em 5 dias"
            },
            {
                rotulo: "Multas",
                valor: "R$ 0,00"
            }
        ]
    }
];

// --- GRADE DA SEMANA COM AS DISCIPLINAS DO CURSO DO ALUNO ---
export const montarGrade = (disciplinas) => {
    const grade = DIAS_DA_GRADE.map((dia) => ({ dia, aulas: [null, null, null, null] }));

    disciplinas.forEach((nome, posicao) => {
        const linha = grade[posicao % 5];
        const inicio = Math.floor(posicao / 5) % 2 === 0 ? 0 : 2;
        const sala = `Sala ${String(posicao + 1).padStart(2, "0")}`;

        linha.aulas[inicio] = { disciplina: nome, sala };
        linha.aulas[inicio + 1] = { disciplina: nome, sala };
    });

    return grade;
};

// --- EVENTOS DO CALENDÁRIO COM AS DISCIPLINAS DO CURSO DO ALUNO ---
export const montarEventos = (disciplinas) => {
    const eventos = [
        {
            data: emDias(14),
            titulo: "Fim do prazo da rematrícula"
        }
    ];

    if (disciplinas[0]) {
        eventos.push({ data: emDias(2), titulo: `Entrega de trabalho de ${disciplinas[0]}` });
    }

    if (disciplinas[1]) {
        eventos.push({ data: emDias(5), titulo: `Avaliação de ${disciplinas[1]}` });
    }

    if (disciplinas[2]) {
        eventos.push({ data: emDias(14), titulo: `Avaliação de ${disciplinas[2]}` });
    }

    return eventos.sort((a, b) => a.data - b.data);
};
