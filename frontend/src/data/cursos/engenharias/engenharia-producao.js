// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/engenharia/engenharia-producao.html ---
const dados = {
    "slug": "engenharia-producao",
    "subnavLabel": "Engenharia de Produção",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Engenharia de",
    "tituloDestaque": "Produção",
    "descricao": "A engenharia que otimiza processos, pessoas e recursos. Aprenda a usar pesquisa operacional, logística e gestão da qualidade para decidir com dados e reduzir custos em operações industriais e de serviços.",
    "acoes": [
        {
            "href": "#grad-matriz",
            "texto": "Conhecer a matriz curricular"
        },
        {
            "href": "#grad-carreira",
            "texto": "Ver possibilidades de carreira"
        }
    ],
    "codigoArquivo": "otimizacao.py",
    "codigoLinhas": [
        "<span class=\"blue\">from</span> pulp <span class=\"blue\">import</span> *",
        " ",
        "plano = <span class=\"yellow\">LpProblem</span>(<span class=\"green\">\"lucro\"</span>, LpMaximize)",
        "a = <span class=\"yellow\">LpVariable</span>(<span class=\"green\">\"produto_a\"</span>, lowBound=0)",
        "b = <span class=\"yellow\">LpVariable</span>(<span class=\"green\">\"produto_b\"</span>, lowBound=0)",
        " ",
        "plano += 45 * a + 30 * b",
        "plano += 2 * a + 1 * b &lt;= 480<span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
    ],
    "stats": [
        {
            "contador": true,
            "valor": "10",
            "sufixo": "",
            "label": "semestres"
        },
        {
            "contador": true,
            "valor": "3600",
            "sufixo": "h",
            "label": "carga horária"
        },
        {
            "contador": false,
            "valor": "Bacharelado",
            "sufixo": "",
            "label": "nível superior"
        },
        {
            "contador": false,
            "valor": "Presencial",
            "sufixo": "",
            "label": "modalidade"
        }
    ],
    "formacaoEyebrow": "Formação",
    "formacaoTitulo": "O que você vai aprender",
    "formacaoTexto": "Uma formação de engenharia completa: base sólida em exatas, domínio de métodos quantitativos de decisão e prática em gestão de operações industriais e de serviços.",
    "formacaoCards": [
        {
            "iconeSvg": "<path d=\"M3 3v18h18\"></path> <path d=\"M7 15l4-5 3 3 5-7\"></path>",
            "titulo": "Pesquisa Operacional",
            "texto": "Programação linear e inteira, otimização de redes e modelos de apoio à decisão."
        },
        {
            "iconeSvg": "<rect x=\"3\" y=\"4\" width=\"18\" height=\"16\" rx=\"2\"></rect> <path d=\"M3 10h18\"></path> <path d=\"M8 4v6M16 14h3M8 14h4\"></path>",
            "titulo": "Planejamento e Controle da Produção",
            "texto": "Previsão de demanda, MRP, sequenciamento, capacidade e gestão de estoques."
        },
        {
            "iconeSvg": "<path d=\"M1 16V7h13v9\"></path> <path d=\"M14 10h4l3 3v3h-7\"></path> <circle cx=\"6\" cy=\"18\" r=\"2\"></circle> <circle cx=\"17\" cy=\"18\" r=\"2\"></circle>",
            "titulo": "Logística e Cadeia de Suprimentos",
            "texto": "Transporte, armazenagem, distribuição e integração de fornecedores e clientes."
        },
        {
            "iconeSvg": "<path d=\"M12 3l7 3v6c0 4-3 7-7 9-4-2-7-5-7-9V6l7-3Z\"></path> <polyline points=\"9 12 11 14 15 10\"></polyline>",
            "titulo": "Gestão da Qualidade",
            "texto": "Lean, Six Sigma, DMAIC, controle estatístico do processo e normas ISO."
        },
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"9\"></circle> <path d=\"M14.5 9.5a2.6 2.6 0 0 0-2.5-1.5c-1.5 0-2.5.8-2.5 2s1 1.8 2.5 2 2.5.6 2.5 2-1 2-2.5 2a2.6 2.6 0 0 1-2.5-1.5\"></path> <path d=\"M12 6.5v11\"></path>",
            "titulo": "Engenharia Econômica e Custos",
            "texto": "Viabilidade de investimentos, fluxo de caixa, formação de preço e custeio industrial."
        },
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"5\" r=\"2.5\"></circle> <path d=\"M12 7.5v6\"></path> <path d=\"M7 10h10\"></path> <path d=\"M12 13.5L9 21M12 13.5L15 21\"></path>",
            "titulo": "Ergonomia e Segurança do Trabalho",
            "texto": "Análise ergonômica, prevenção de riscos, normas regulamentadoras e saúde ocupacional."
        }
    ],
    "matrizEyebrow": "Matriz curricular",
    "matrizTitulo": "Uma jornada de 10 semestres",
    "matrizTexto": "Clique em cada semestre para visualizar as principais disciplinas.",
    "semestres": [
        {
            "titulo": "1º semestre · Fundamentos",
            "disciplinas": [
                "Cálculo Diferencial e Integral I",
                "Geometria Analítica",
                "Química Geral",
                "Introdução à Engenharia de Produção",
                "Desenho Técnico"
            ]
        },
        {
            "titulo": "2º semestre · Exatas",
            "disciplinas": [
                "Cálculo Diferencial e Integral II",
                "Álgebra Linear",
                "Física I — Mecânica",
                "Algoritmos e Programação",
                "Ciência dos Materiais"
            ]
        },
        {
            "titulo": "3º semestre · Métodos quantitativos",
            "disciplinas": [
                "Cálculo Diferencial e Integral III",
                "Física II — Eletromagnetismo",
                "Probabilidade e Estatística",
                "Mecânica dos Sólidos Aplicada",
                "Teoria Geral da Administração"
            ]
        },
        {
            "titulo": "4º semestre · Processos e custos",
            "disciplinas": [
                "Estatística Aplicada",
                "Processos de Fabricação",
                "Contabilidade e Custos Industriais",
                "Metrologia e Normalização",
                "Programação Aplicada à Engenharia"
            ]
        },
        {
            "titulo": "5º semestre · Pesquisa operacional",
            "disciplinas": [
                "Pesquisa Operacional I",
                "Engenharia Econômica",
                "Gestão da Qualidade",
                "Engenharia de Métodos e Tempos",
                "Gestão de Pessoas nas Organizações"
            ]
        },
        {
            "titulo": "6º semestre · Produção",
            "disciplinas": [
                "Pesquisa Operacional II",
                "Planejamento e Controle da Produção I",
                "Controle Estatístico do Processo",
                "Ergonomia",
                "Automação e Sistemas Produtivos"
            ]
        },
        {
            "titulo": "7º semestre · Logística",
            "disciplinas": [
                "Planejamento e Controle da Produção II",
                "Logística Empresarial",
                "Simulação de Sistemas Produtivos",
                "Engenharia de Segurança do Trabalho",
                "Gestão de Estoques e Suprimentos"
            ]
        },
        {
            "titulo": "8º semestre · Gestão",
            "disciplinas": [
                "Gestão da Cadeia de Suprimentos",
                "Produção Enxuta (Lean Manufacturing)",
                "Gestão de Projetos",
                "Análise de Dados e Processos",
                "Gestão Ambiental e Sustentabilidade"
            ]
        },
        {
            "titulo": "9º semestre · Integração",
            "disciplinas": [
                "Six Sigma e Melhoria Contínua",
                "Projeto de Fábrica e Arranjo Físico",
                "Gestão de Operações em Serviços",
                "Optativa I",
                "Trabalho de Conclusão I"
            ]
        },
        {
            "titulo": "10º semestre · Conclusão",
            "disciplinas": [
                "Empreendedorismo e Inovação",
                "Estratégia e Competitividade Industrial",
                "Optativa II",
                "Atividades Complementares",
                "Estágio Supervisionado",
                "Trabalho de Conclusão II"
            ]
        }
    ],
    "carreiraEyebrow": "Mercado de trabalho",
    "carreiraTitulo": "Onde você pode chegar",
    "carreiraTexto": "O engenheiro de produção atua onde existem processos a melhorar: indústria, varejo, logística, saúde, agronegócio, bancos e consultorias — sempre conectando dados, custos e resultado operacional.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Planejamento e Controle da Produção",
        "Logística e Supply Chain",
        "Gestão da Qualidade",
        "Melhoria Contínua e Lean",
        "Consultoria Empresarial",
        "Gestão de Projetos",
        "Análise de Dados e Processos",
        "Gestão Industrial"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Engenharia de Produção."
};

export default dados;
