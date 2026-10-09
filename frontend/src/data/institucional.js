// --- ITENS DO SUBMENU "INSTITUCIONAL" (A UNIVERSIDADE). `rota` aponta para uma página que já existe; sem `rota`, ---
// --- o link abre a página genérica /web/institucional/<slug>, montada a partir de `resumo`. ---
export const INSTITUCIONAL = [
    {
        slug: "a-instituicao",
        titulo: "A Instituição",
        rota: "/web/sobre/historia"
    },
    {
        slug: "atividades-complementares",
        titulo: "Atividades complementares",
        resumo: "Reúne as regras e as formas de cumprir as horas de atividades complementares exigidas na graduação, como cursos, eventos e projetos."
    },
    {
        slug: "autoavaliacao",
        titulo: "Autoavaliação",
        resumo: "Apresenta como a UniAura avalia a si mesma, com a participação de alunos, professores e colaboradores."
    },
    {
        slug: "avaliacao-externa",
        titulo: "Avaliação externa",
        resumo: "Informa as avaliações feitas por órgãos externos sobre a instituição e sobre os seus cursos."
    },
    {
        slug: "central-da-marca",
        titulo: "Central da Marca",
        resumo: "Concentra o uso da marca UniAura: logotipos, cores e orientações de aplicação."
    },
    {
        slug: "corpo-diretivo",
        titulo: "Corpo diretivo",
        resumo: "Apresenta as pessoas que coordenam e dirigem a universidade."
    },
    {
        slug: "extensao",
        titulo: "Extensão",
        resumo: "Projetos e ações que levam o conhecimento produzido na universidade para a comunidade."
    },
    {
        slug: "grupos-de-estudo",
        titulo: "Grupos de Estudo",
        resumo: "Grupos de alunos e professores que se reúnem para estudar e pesquisar temas em comum."
    },
    {
        slug: "lgpd",
        titulo: "LGPD",
        rota: "/web/privacidade"
    },
    {
        slug: "monitoria",
        titulo: "Monitoria",
        resumo: "Programa em que alunos apoiam colegas em disciplinas, com a orientação de um professor."
    },
    {
        slug: "nucleo-de-acolhimento-academico",
        titulo: "Núcleo de Acolhimento Acadêmico",
        resumo: "Apoio pedagógico e psicossocial a alunos e professores, com acolhimento, orientação e acessibilidade."
    },
    {
        slug: "nucleo-de-internacionalizacao-e-mobilidade-academica",
        titulo: "Núcleo de Internacionalização e Mobilidade Acadêmica",
        resumo: "Intercâmbios, convênios internacionais e mobilidade de alunos e professores."
    },
    {
        slug: "pesquisa",
        titulo: "Pesquisa",
        resumo: "Linhas de pesquisa, grupos e laboratórios da universidade."
    },
    {
        slug: "relatorio-de-transparencia-e-igualdade-salarial",
        titulo: "Relatório de Transparência e Igualdade Salarial de Mulheres e Homens",
        resumo: "Publica o relatório de transparência e de igualdade salarial entre mulheres e homens."
    },
    {
        slug: "trabalhe-conosco",
        titulo: "Trabalhe conosco",
        resumo: "Informa como se candidatar a vagas de professores e de colaboradores."
    }
];

// --- ROTA FINAL DE UM ITEM: A PÁGINA PRÓPRIA, SE EXISTIR, OU A PÁGINA GENÉRICA DO INSTITUCIONAL ---
export const rotaInstitucional = (item) => item.rota ?? `/web/institucional/${item.slug}`;
