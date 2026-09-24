// --- ESTRUTURA DO MENU LATERAL DA ÁREA INTERNA, POR PERFIL ---
export const GRUPOS_MENU = [
    {
        secao: null,
        itens: [
            { rota: "/admin/dashboard", label: "Dashboard", perfis: ["ADMIN"] },
            { rota: "/aluno/dashboard", label: "Dashboard", perfis: ["ALUNO"] },
            { rota: "/professor/dashboard", label: "Dashboard", perfis: ["PROFESSOR"] },
            { rota: "/coordenacao/dashboard", label: "Dashboard", perfis: ["COORDENADOR"] },
            { rota: "/secretaria/dashboard", label: "Dashboard", perfis: ["SECRETARIA"] },
            { rota: "/biblioteca/dashboard", label: "Dashboard", perfis: ["BIBLIOTECARIO"] },
            { rota: "/financeiro/dashboard", label: "Dashboard", perfis: ["FINANCEIRO"] },
            { rota: "/responsavel/dashboard", label: "Dashboard", perfis: ["RESPONSAVEL"] }
        ]
    },
    {
        secao: "Cadastros",
        perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"],
        itens: [
            { rota: "/secretaria/alunos", label: "Alunos", perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"] },
            { rota: "/secretaria/professores", label: "Professores", perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"] },
            { rota: "/secretaria/responsaveis", label: "Responsáveis", perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"] },
            { rota: "/secretaria/funcionarios", label: "Funcionários", perfis: ["SECRETARIA", "COORDENADOR", "ADMIN"] }
        ]
    },
    {
        secao: "Biblioteca",
        perfis: ["BIBLIOTECARIO", "ADMIN"],
        itens: [
            { rota: "/biblioteca/acervo", label: "Acervo", perfis: ["BIBLIOTECARIO", "ADMIN"] },
            { rota: "/biblioteca/exemplares", label: "Exemplares", perfis: ["BIBLIOTECARIO", "ADMIN"] },
            { rota: "/biblioteca/emprestimos", label: "Empréstimos", perfis: ["BIBLIOTECARIO", "ADMIN"] },
            { rota: "/biblioteca/devolucoes", label: "Devoluções", perfis: ["BIBLIOTECARIO", "ADMIN"] },
            { rota: "/biblioteca/reservas", label: "Reservas", perfis: ["BIBLIOTECARIO", "ADMIN"] },
            { rota: "/biblioteca/multas", label: "Multas", perfis: ["BIBLIOTECARIO", "ADMIN"] },
            { rota: "/biblioteca/configuracoes", label: "Configurações", perfis: ["BIBLIOTECARIO", "ADMIN"] }
        ]
    },
    {
        secao: "Biblioteca",
        perfis: ["ALUNO"],
        itens: [
            { rota: "/aluno/biblioteca/consultar", label: "Consultar acervo", perfis: ["ALUNO"] },
            { rota: "/aluno/biblioteca/meus-emprestimos", label: "Meus empréstimos", perfis: ["ALUNO"] },
            { rota: "/aluno/biblioteca/minhas-reservas", label: "Minhas reservas", perfis: ["ALUNO"] }
        ]
    }
];
