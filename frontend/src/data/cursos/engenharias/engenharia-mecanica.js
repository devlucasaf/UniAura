// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/engenharia/engenharia-mecanica.html ---
const dados = {
    "slug": "engenharia-mecanica",
    "subnavLabel": "Engenharia Mecânica",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Engenharia",
    "tituloDestaque": "Mecânica",
    "descricao": "Projete máquinas, componentes e sistemas que movem a indústria, unindo resistência dos materiais, termodinâmica e mecânica dos fluidos. Domine processos de manufatura e a simulação computacional de cada peça.",
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
    "codigoArquivo": "viga.py",
    "codigoLinhas": [
        "<span class=\"blue\">def</span> <span class=\"yellow\">tensao_flexao</span>(carga, vao, c, I):",
        "    momento = carga * vao / 4      <span class=\"green\"># N.m</span>",
        "    sigma = momento * c / I",
        "    <span class=\"blue\">return</span> sigma / 1e6            <span class=\"green\"># MPa</span>",
        " ",
        "sigma = <span class=\"yellow\">tensao_flexao</span>(12000, 3.0, 0.10, 8.5e-6)",
        "<span class=\"blue\">if</span> sigma &lt; 250 / 1.5:",
        "    <span class=\"yellow\">print</span>(<span class=\"green\">\"Viga aprovada\"</span>)<span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
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
    "formacaoTexto": "Uma formação de engenharia completa: base sólida em exatas, domínio das ciências térmicas e mecânicas e prática intensiva em laboratório, projeto e simulação computacional.",
    "formacaoCards": [
        {
            "iconeSvg": "<path d=\"M3 20h18\"></path> <path d=\"M5 20V9l7-5 7 5v11\"></path> <path d=\"M5 13h14\"></path>",
            "titulo": "Mecânica dos Sólidos",
            "texto": "Estática, dinâmica, resistência dos materiais, tensões, deformações e critérios de falha."
        },
        {
            "iconeSvg": "<path d=\"M14 14.76V4a2 2 0 0 0-4 0v10.76a4 4 0 1 0 4 0Z\"></path>",
            "titulo": "Termodinâmica e Transferência de Calor",
            "texto": "Ciclos térmicos, energia, entropia, condução, convecção e radiação em máquinas e processos."
        },
        {
            "iconeSvg": "<path d=\"M3 8c2.5-2 5-2 7.5 0S15.5 10 18 8s3 0 3 0\"></path> <path d=\"M3 14c2.5-2 5-2 7.5 0s5 2 7.5 0 3 0 3 0\"></path> <path d=\"M3 20c2.5-2 5-2 7.5 0s5 2 7.5 0 3 0 3 0\"></path>",
            "titulo": "Mecânica dos Fluidos",
            "texto": "Escoamentos, perda de carga, bombas, turbinas e fundamentos de aerodinâmica."
        },
        {
            "iconeSvg": "<path d=\"M14.7 6.3a4 4 0 0 1-5 5L4 17v3h3l5.7-5.7a4 4 0 0 1 5-5l-2.5 2.5\"></path> <path d=\"M20.5 3.5 17 7\"></path>",
            "titulo": "Materiais e Processos de Fabricação",
            "texto": "Metais, polímeros e compósitos, tratamentos térmicos, usinagem, soldagem e conformação."
        },
        {
            "iconeSvg": "<path d=\"M4 4h16v16H4z\"></path> <path d=\"M4 9h16M9 4v16\"></path> <path d=\"M13 13l4 4\"></path> <circle cx=\"13\" cy=\"13\" r=\"1\"></circle>",
            "titulo": "Projeto de Máquinas e CAD/CAE",
            "texto": "Elementos de máquinas, dimensionamento, modelagem 3D e análise por elementos finitos."
        },
        {
            "iconeSvg": "<path d=\"M3 12h3l2-6 4 14 3-9 2 3h4\"></path>",
            "titulo": "Manutenção e Vibrações",
            "texto": "Análise de vibrações, balanceamento, manutenção preditiva e confiabilidade de ativos."
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
                "Introdução à Engenharia Mecânica",
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
                "Desenho Assistido por Computador (CAD)"
            ]
        },
        {
            "titulo": "3º semestre · Mecânica geral",
            "disciplinas": [
                "Cálculo Diferencial e Integral III",
                "Física II — Fluidos e Termologia",
                "Estática dos Corpos Rígidos",
                "Ciência dos Materiais",
                "Probabilidade e Estatística"
            ]
        },
        {
            "titulo": "4º semestre · Sólidos e calor",
            "disciplinas": [
                "Equações Diferenciais",
                "Física III — Eletromagnetismo",
                "Dinâmica dos Corpos Rígidos",
                "Resistência dos Materiais I",
                "Termodinâmica I"
            ]
        },
        {
            "titulo": "5º semestre · Fluidos",
            "disciplinas": [
                "Mecânica dos Fluidos",
                "Resistência dos Materiais II",
                "Termodinâmica II",
                "Metrologia e Ensaios Mecânicos",
                "Circuitos Elétricos e Eletrotécnica"
            ]
        },
        {
            "titulo": "6º semestre · Manufatura",
            "disciplinas": [
                "Transferência de Calor",
                "Processos de Fabricação",
                "Elementos de Máquinas I",
                "Métodos Numéricos Aplicados",
                "Pneumática e Hidráulica"
            ]
        },
        {
            "titulo": "7º semestre · Máquinas",
            "disciplinas": [
                "Máquinas Térmicas",
                "Máquinas de Fluxo",
                "Elementos de Máquinas II",
                "Sistemas de Controle",
                "Usinagem e Comando Numérico (CNC)"
            ]
        },
        {
            "titulo": "8º semestre · Simulação",
            "disciplinas": [
                "Método dos Elementos Finitos",
                "Vibrações Mecânicas",
                "Refrigeração e Ar-Condicionado",
                "Projeto Mecânico I",
                "Engenharia Econômica"
            ]
        },
        {
            "titulo": "9º semestre · Manutenção e projeto",
            "disciplinas": [
                "Manutenção Industrial e Confiabilidade",
                "Automação e Manufatura Integrada",
                "Projeto Mecânico II",
                "Optativa I",
                "Trabalho de Conclusão I"
            ]
        },
        {
            "titulo": "10º semestre · Conclusão",
            "disciplinas": [
                "Segurança do Trabalho",
                "Empreendedorismo e Inovação",
                "Optativa II",
                "Atividades Complementares",
                "Estágio Supervisionado",
                "Trabalho de Conclusão II"
            ]
        }
    ],
    "carreiraEyebrow": "Mercado de trabalho",
    "carreiraTitulo": "Onde você pode chegar",
    "carreiraTexto": "O engenheiro mecânico atua onde existe máquina, energia ou produto fabricado: indústria automotiva, metalmecânica, petróleo e gás, geração de energia, climatização, alimentos e bens de capital.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Projeto Mecânico",
        "Manufatura e Processos",
        "Automotivo",
        "Óleo, Gás e Energia",
        "HVAC e Refrigeração",
        "Manutenção e Confiabilidade",
        "Simulação e Análise (CAE)",
        "Pesquisa e Desenvolvimento"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Engenharia Mecânica."
};

export default dados;
