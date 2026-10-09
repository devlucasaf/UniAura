// --- SOMA DIAS A HOJE, PARA OS EVENTOS DE EXEMPLO SEMPRE CAÍREM PERTO DA DATA ATUAL ---
const emDias = (dias) => {
    const data = new Date();
    data.setHours(0, 0, 0, 0);
    data.setDate(data.getDate() + dias);
    return data;
};

// --- CHAVE aaaa-mm-dd DE UMA DATA, USADA PARA COMPARAR DIAS ---
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

// --- EVENTOS DO CALENDÁRIO ACADÊMICO ---
export const EVENTOS = [
    { data: emDias(2),  titulo: "Entrega do trabalho de Banco de Dados" },
    { data: emDias(5),  titulo: "Prova de Engenharia de Software" },
    { data: emDias(9),  titulo: "Palestra: carreiras em tecnologia" },
    { data: emDias(14), titulo: "Fim do prazo da rematrícula" },
    { data: emDias(14), titulo: "Prova de Redes de Computadores" }
];

// --- HORÁRIOS DAS AULAS (COLUNAS DA GRADE) ---
export const HORARIOS = ["08:00 – 09:40", "10:00 – 11:40", "19:00 – 20:40", "21:00 – 22:40"];

// --- GRADE SEMANAL: UMA LINHA POR DIA DA SEMANA, UM ITEM POR HORÁRIO ---
export const GRADE = [
    { dia: "Segunda-feira", aulas: [{ disciplina: "Banco de Dados", sala: "Lab 03" }, { disciplina: "Banco de Dados", sala: "Lab 03" }, null, null] },
    { dia: "Terça-feira", aulas: [null, null, { disciplina: "Engenharia de Software", sala: "B-204" }, { disciplina: "Engenharia de Software", sala: "B-204" }] },
    { dia: "Quarta-feira", aulas: [{ disciplina: "Redes de Computadores", sala: "A-102" }, { disciplina: "Redes de Computadores", sala: "A-102" }, null, null] },
    { dia: "Quinta-feira", aulas: [null, null, { disciplina: "Estrutura de Dados", sala: "Lab 01" }, { disciplina: "Estrutura de Dados", sala: "Lab 01" }] },
    { dia: "Sexta-feira", aulas: [{ disciplina: "Inglês Técnico", sala: "C-010" }, null, { disciplina: "Projeto Integrador", sala: "Lab 02" }, null] },
    { dia: "Sábado", aulas: [null, { disciplina: "Atividades Complementares", sala: "Auditório" }, null, null] }
];

// --- NOTAS E PRESENÇA POR DISCIPLINA (PÁGINA "MEU PERFIL") ---
export const DESEMPENHO = [
    { disciplina: "Banco de Dados",         nota1: 8.5, nota2: 9.0, aulas: 40, faltas: 2 },
    { disciplina: "Engenharia de Software", nota1: 7.5, nota2: 8.0, aulas: 40, faltas: 4 },
    { disciplina: "Redes de Computadores",  nota1: 6.0, nota2: 7.0, aulas: 40, faltas: 6 },
    { disciplina: "Estrutura de Dados",     nota1: 9.0, nota2: 9.5, aulas: 40, faltas: 0 },
    { disciplina: "Inglês Técnico",         nota1: 8.0, nota2: 8.5, aulas: 20, faltas: 1 },
    { disciplina: "Projeto Integrador",     nota1: 9.5, nota2: 9.5, aulas: 20, faltas: 2 }
];

// --- TÓPICOS E INDICADORES DOS DASHBOARDS ---
export const DASHBOARDS = [
    {
        id: "academico",
        titulo: "Desempenho acadêmico",
        icone: "pi pi-chart-line",
        indicadores: [
            { rotulo: "Média geral", valor: "8,4" },
            { rotulo: "Frequência", valor: "92%", progresso: 92 },
            { rotulo: "Disciplinas em curso", valor: "6" },
            { rotulo: "Créditos concluídos", valor: "128 / 200", progresso: 64 }
        ]
    },
    {
        id: "financeiro",
        titulo: "Financeiro",
        icone: "pi pi-wallet",
        indicadores: [
            { rotulo: "Situação", valor: "Em dia" },
            { rotulo: "Próximo vencimento", valor: "10/11" },
            { rotulo: "Valor da mensalidade", valor: "R$ 890,00" },
            { rotulo: "Parcelas pagas no ano", valor: "10 / 12", progresso: 83 }
        ]
    },
    {
        id: "biblioteca",
        titulo: "Biblioteca",
        icone: "pi pi-book",
        indicadores: [
            { rotulo: "Empréstimos ativos", valor: "2" },
            { rotulo: "Reservas", valor: "1" },
            { rotulo: "Próxima devolução", valor: "em 5 dias" },
            { rotulo: "Multas", valor: "R$ 0,00" }
        ]
    }
];
