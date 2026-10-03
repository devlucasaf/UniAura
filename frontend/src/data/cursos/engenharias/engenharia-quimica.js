// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/engenharia/engenharia-quimica.html ---
const dados = {
    "slug": "engenharia-quimica",
    "subnavLabel": "Engenharia Química",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Engenharia",
    "tituloDestaque": "Química",
    "descricao": "Transforme matéria-prima em produto na escala da indústria. Projete reatores, dimensione operações unitárias e domine termodinâmica, controle de processos e sustentabilidade para produzir mais com menos impacto.",
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
    "codigoArquivo": "reator.py",
    "codigoLinhas": [
        "<span class=\"blue\">from</span> processo <span class=\"blue\">import</span> Corrente",
        " ",
        "<span class=\"blue\">def</span> <span class=\"yellow\">conversao_cstr</span>(k, tau):",
        "    <span class=\"green\">\"\"\"Balanço de massa em regime permanente.\"\"\"</span>",
        "    x = k * tau / (<span class=\"blue\">1</span> + k * tau)",
        "    <span class=\"blue\">if</span> x &lt; <span class=\"blue\">0.85</span>:",
        "        <span class=\"yellow\">alertar</span>(<span class=\"green\">\"aumentar tempo de residência\"</span>)",
        "    <span class=\"blue\">return</span> x<span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
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
    "formacaoTexto": "Uma formação de engenharia completa: base sólida em exatas e química, domínio das operações que sustentam a indústria de processos e prática intensiva em laboratório e simulação.",
    "formacaoCards": [
        {
            "iconeSvg": "<path d=\"M14 4v6l5 8a2 2 0 0 1-1.7 3H6.7A2 2 0 0 1 5 18l5-8V4\"></path> <path d=\"M9 4h6\"></path> <path d=\"M8 14h8\"></path>",
            "titulo": "Termodinâmica Química",
            "texto": "Energia, entropia, equilíbrio de fases e químico aplicados a sistemas reais."
        },
        {
            "iconeSvg": "<path d=\"M3 8h13a3 3 0 1 1 3 3\"></path> <path d=\"M3 14h11a3 3 0 1 0-3-3\"></path> <path d=\"M3 19h9\"></path>",
            "titulo": "Fenômenos de Transporte",
            "texto": "Transporte de quantidade de movimento, calor e massa em escoamentos industriais."
        },
        {
            "iconeSvg": "<path d=\"M6 3v7L3 20a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1l-3-10V3\"></path> <path d=\"M16 3v7l-3 10a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1l-3-10V3\"></path> <path d=\"M4 3h5M14 3h5\"></path>",
            "titulo": "Operações Unitárias",
            "texto": "Destilação, absorção, extração, secagem, filtração e trocadores de calor."
        },
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"8\"></circle> <path d=\"M12 4a8 8 0 0 1 0 16\"></path> <circle cx=\"12\" cy=\"12\" r=\"2\"></circle>",
            "titulo": "Reatores e Cinética Química",
            "texto": "Velocidade de reação, catálise e projeto de reatores batelada, CSTR e PFR."
        },
        {
            "iconeSvg": "<path d=\"M3 16c3 0 3-8 6-8s3 8 6 8 3-5 6-5\"></path> <path d=\"M3 20h18\"></path> <circle cx=\"9\" cy=\"8\" r=\"1\"></circle>",
            "titulo": "Controle e Simulação de Processos",
            "texto": "Instrumentação, malhas PID, identificação de sistemas e simuladores de planta."
        },
        {
            "iconeSvg": "<path d=\"M4 21V10l5-3v14\"></path> <path d=\"M9 14h6a2 2 0 0 1 2 2v5\"></path> <path d=\"M17 21h3V3h-3\"></path>",
            "titulo": "Processos Industriais e Sustentabilidade",
            "texto": "Rotas produtivas, eficiência energética, tratamento de efluentes e economia circular."
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
                "Introdução à Engenharia Química",
                "Desenho Técnico"
            ]
        },
        {
            "titulo": "2º semestre · Exatas e Química",
            "disciplinas": [
                "Cálculo Diferencial e Integral II",
                "Álgebra Linear",
                "Física I — Mecânica",
                "Química Inorgânica",
                "Algoritmos e Programação"
            ]
        },
        {
            "titulo": "3º semestre · Química Analítica",
            "disciplinas": [
                "Cálculo Diferencial e Integral III",
                "Física II — Fluidos e Termologia",
                "Química Orgânica I",
                "Química Analítica",
                "Probabilidade e Estatística"
            ]
        },
        {
            "titulo": "4º semestre · Balanços",
            "disciplinas": [
                "Equações Diferenciais",
                "Física III — Eletromagnetismo",
                "Química Orgânica II",
                "Físico-Química I",
                "Balanços de Massa e Energia"
            ]
        },
        {
            "titulo": "5º semestre · Transporte",
            "disciplinas": [
                "Fenômenos de Transporte",
                "Mecânica dos Fluidos",
                "Físico-Química II",
                "Cálculo Numérico",
                "Ciência dos Materiais"
            ]
        },
        {
            "titulo": "6º semestre · Operações Unitárias",
            "disciplinas": [
                "Transferência de Calor",
                "Operações Unitárias I",
                "Termodinâmica Aplicada",
                "Instrumentação e Medição",
                "Microbiologia Industrial"
            ]
        },
        {
            "titulo": "7º semestre · Reatores",
            "disciplinas": [
                "Transferência de Massa",
                "Operações Unitárias II",
                "Cinética Química e Reatores I",
                "Processos Químicos Industriais",
                "Modelagem e Simulação de Processos"
            ]
        },
        {
            "titulo": "8º semestre · Controle de Processos",
            "disciplinas": [
                "Cinética Química e Reatores II",
                "Controle de Processos",
                "Bioprocessos",
                "Engenharia Econômica",
                "Laboratório de Engenharia Química I"
            ]
        },
        {
            "titulo": "9º semestre · Projeto de Plantas",
            "disciplinas": [
                "Projeto de Plantas Químicas",
                "Tratamento de Efluentes",
                "Laboratório de Engenharia Química II",
                "Optativa I",
                "Trabalho de Conclusão I"
            ]
        },
        {
            "titulo": "10º semestre · Conclusão",
            "disciplinas": [
                "Segurança de Processos Industriais",
                "Gestão Ambiental e Sustentabilidade",
                "Optativa II",
                "Atividades Complementares",
                "Estágio Supervisionado",
                "Trabalho de Conclusão II"
            ]
        }
    ],
    "carreiraEyebrow": "Mercado de trabalho",
    "carreiraTitulo": "Onde você pode chegar",
    "carreiraTexto": "O engenheiro químico atua onde existe transformação de matéria em escala: refinarias e petroquímicas, alimentos, fármacos, cosméticos, celulose, saneamento, biocombustíveis e centros de pesquisa.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Petróleo e Petroquímica",
        "Alimentos e Bebidas",
        "Farmacêutica e Cosméticos",
        "Papel e Celulose",
        "Tratamento de Água e Efluentes",
        "Bioprocessos e Biocombustíveis",
        "Controle de Processos",
        "Pesquisa e Desenvolvimento"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Engenharia Química."
};

export default dados;
