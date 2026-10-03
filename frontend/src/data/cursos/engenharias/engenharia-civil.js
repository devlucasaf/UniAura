// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/engenharia/engenharia-civil.html ---
const dados = {
    "slug": "engenharia-civil",
    "subnavLabel": "Engenharia Civil",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Engenharia",
    "tituloDestaque": "Civil",
    "descricao": "Da fundação ao acabamento, com cálculo por trás de cada decisão. Projete estruturas seguras, planeje obras e construa a infraestrutura que sustenta cidades — pontes, edifícios, estradas e saneamento.",
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
    "codigoArquivo": "viga-biapoiada.calc",
    "codigoLinhas": [
        "<span class=\"muted\">// Viga biapoiada, carga distribuída</span>",
        " ",
        "<span class=\"blue\">Vão livre</span>       L = 6,00 m",
        "<span class=\"blue\">Carga uniforme</span>  q = 20,00 kN/m",
        " ",
        "<span class=\"yellow\">Momento máximo</span>  M = q · L² / 8",
        "                M = 20 · 36 / 8",
        " ",
        "<span class=\"green\">&gt;&gt; M = 90,00 kN·m</span><span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
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
    "formacaoTexto": "Base sólida em exatas nos primeiros semestres e, a partir do quarto, as seis grandes áreas da engenharia civil com laboratório e canteiro.",
    "formacaoCards": [
        {
            "iconeSvg": "<path d=\"M3 8h18M3 16h18\"></path> <path d=\"M6 8v8M12 8v8M18 8v8\"></path>",
            "titulo": "Estruturas",
            "texto": "Concreto armado, estruturas metálicas e de madeira, análise e dimensionamento."
        },
        {
            "iconeSvg": "<path d=\"M3 14h18\"></path> <path d=\"M5 14V9l7-5 7 5v5\"></path> <path d=\"M3 18h18M3 22h18\"></path>",
            "titulo": "Geotecnia",
            "texto": "Mecânica dos solos, sondagem, fundações, contenções e estabilidade de taludes."
        },
        {
            "iconeSvg": "<path d=\"M12 3s6 6.5 6 10.5a6 6 0 0 1-12 0C6 9.5 12 3 12 3Z\"></path>",
            "titulo": "Hidráulica e Saneamento",
            "texto": "Hidrologia, redes de água e esgoto, drenagem urbana e tratamento de efluentes."
        },
        {
            "iconeSvg": "<rect x=\"3\" y=\"3\" width=\"18\" height=\"18\" rx=\"2\"></rect> <path d=\"M3 9h18M9 9v12\"></path>",
            "titulo": "Materiais e Construção",
            "texto": "Concreto, aço, agregados, ensaios tecnológicos e técnicas construtivas."
        },
        {
            "iconeSvg": "<path d=\"M4 19l6-14 4 9 2-4 4 9Z\"></path>",
            "titulo": "Transportes e Infraestrutura",
            "texto": "Topografia, estradas, pavimentação, mobilidade urbana e obras de arte."
        },
        {
            "iconeSvg": "<path d=\"M3 21h18\"></path> <rect x=\"5\" y=\"11\" width=\"3\" height=\"7\"></rect> <rect x=\"10.5\" y=\"8\" width=\"3\" height=\"10\"></rect> <rect x=\"16\" y=\"5\" width=\"3\" height=\"13\"></rect>",
            "titulo": "Gestão de Obras",
            "texto": "Orçamento, cronograma, produtividade, qualidade e segurança no canteiro."
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
                "Introdução à Engenharia Civil",
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
                "Topografia I"
            ]
        },
        {
            "titulo": "3º semestre · Mecânica",
            "disciplinas": [
                "Cálculo Diferencial e Integral III",
                "Física II — Fluidos e Calor",
                "Estática das Estruturas",
                "Probabilidade e Estatística",
                "Topografia II"
            ]
        },
        {
            "titulo": "4º semestre · Materiais",
            "disciplinas": [
                "Resistência dos Materiais I",
                "Materiais de Construção I",
                "Mecânica dos Fluidos",
                "Equações Diferenciais",
                "Geologia Aplicada"
            ]
        },
        {
            "titulo": "5º semestre · Estruturas",
            "disciplinas": [
                "Resistência dos Materiais II",
                "Materiais de Construção II",
                "Hidráulica",
                "Teoria das Estruturas I",
                "Mecânica dos Solos I"
            ]
        },
        {
            "titulo": "6º semestre · Concreto",
            "disciplinas": [
                "Teoria das Estruturas II",
                "Concreto Armado I",
                "Mecânica dos Solos II",
                "Hidrologia",
                "Construção Civil I"
            ]
        },
        {
            "titulo": "7º semestre · Projeto",
            "disciplinas": [
                "Concreto Armado II",
                "Estruturas Metálicas e de Madeira",
                "Fundações",
                "Saneamento Básico",
                "Construção Civil II"
            ]
        },
        {
            "titulo": "8º semestre · Infraestrutura",
            "disciplinas": [
                "Estradas e Pavimentação",
                "Instalações Prediais",
                "Sistemas Estruturais",
                "Engenharia de Custos e Orçamento",
                "Projeto Integrado I"
            ]
        },
        {
            "titulo": "9º semestre · Gestão",
            "disciplinas": [
                "Planejamento e Controle de Obras",
                "Engenharia Ambiental",
                "Patologia das Construções",
                "Optativa I",
                "Trabalho de Conclusão I"
            ]
        },
        {
            "titulo": "10º semestre · Conclusão",
            "disciplinas": [
                "Gerenciamento de Obras",
                "Segurança do Trabalho",
                "Optativa II",
                "Atividades Complementares",
                "Estágio Supervisionado",
                "Trabalho de Conclusão II"
            ]
        }
    ],
    "carreiraEyebrow": "Mercado de trabalho",
    "carreiraTitulo": "Onde você pode chegar",
    "carreiraTexto": "O engenheiro civil atua em construtoras, incorporadoras, escritórios de projeto, concessionárias de infraestrutura e no setor público — e responde tecnicamente por aquilo que assina, com registro no CREA.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Cálculo Estrutural",
        "Gerenciamento de Obras",
        "Geotecnia e Fundações",
        "Saneamento",
        "Infraestrutura e Transportes",
        "Orçamento e Custos",
        "Perícia e Patologia",
        "Setor Público"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Engenharia Civil."
};

export default dados;
