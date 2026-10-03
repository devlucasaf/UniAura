// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/engenharia/engenharia-eletrica.html ---
const dados = {
    "slug": "engenharia-eletrica",
    "subnavLabel": "Engenharia Elétrica",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Engenharia",
    "tituloDestaque": "Elétrica",
    "descricao": "Da geração à transmissão e distribuição de energia, projete os sistemas que mantêm cidades e indústrias em funcionamento. Domine eletrônica, máquinas elétricas, energias renováveis e sistemas de potência.",
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
    "codigoArquivo": "fluxo_potencia.py",
    "codigoLinhas": [
        "<span class=\"blue\">from</span> math <span class=\"blue\">import</span> sqrt, acos",
        " ",
        "<span class=\"blue\">def</span> <span class=\"yellow\">queda_tensao</span>(v, p, fp, r, x, l):",
        "    i = p / (sqrt(3) * v * fp)",
        "    dv = sqrt(3) * i * (r * fp + x * <span class=\"yellow\">sin</span>(<span class=\"yellow\">acos</span>(fp))) * l",
        "    <span class=\"blue\">if</span> dv / v &gt; 0.05:",
        "        <span class=\"yellow\">print</span>(<span class=\"green\">\"Bitola insuficiente\"</span>)",
        "    <span class=\"blue\">return</span> dv<span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
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
    "formacaoTexto": "Uma formação de engenharia completa: base sólida em exatas, domínio dos fenômenos elétricos e magnéticos e prática intensiva em laboratórios de circuitos, máquinas e sistemas de energia.",
    "formacaoCards": [
        {
            "iconeSvg": "<path d=\"M2 12h4l3-7 6 14 3-7h4\"></path>",
            "titulo": "Circuitos Elétricos",
            "texto": "Leis de Kirchhoff, análise em corrente contínua e alternada, transitórios e regime permanente senoidal."
        },
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"2.5\"></circle> <ellipse cx=\"12\" cy=\"12\" rx=\"10\" ry=\"4.5\"></ellipse> <ellipse cx=\"12\" cy=\"12\" rx=\"4.5\" ry=\"10\"></ellipse>",
            "titulo": "Eletromagnetismo",
            "texto": "Campos elétricos e magnéticos, equações de Maxwell, ondas eletromagnéticas e propagação."
        },
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"7\"></circle> <circle cx=\"12\" cy=\"12\" r=\"2.5\"></circle> <path d=\"M12 2v3M12 19v3M2 12h3M19 12h3\"></path>",
            "titulo": "Máquinas Elétricas e Acionamentos",
            "texto": "Transformadores, motores de indução e síncronos, geradores e acionamento com inversores."
        },
        {
            "iconeSvg": "<path d=\"M12 3v6M8 6l4-3 4 3\"></path> <path d=\"M5 9h14l-2 12H7L5 9Z\"></path>",
            "titulo": "Sistemas de Potência",
            "texto": "Fluxo de carga, curto-circuito, estabilidade, proteção e operação de redes de transmissão."
        },
        {
            "iconeSvg": "<rect x=\"3\" y=\"6\" width=\"18\" height=\"12\" rx=\"2\"></rect> <path d=\"M3 12h4l2-4 3 8 2-4h7\"></path>",
            "titulo": "Eletrônica de Potência",
            "texto": "Retificadores, inversores, conversores CC-CC, chaveamento e qualidade de energia."
        },
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"4\"></circle> <path d=\"M12 2v3M12 19v3M4.9 4.9l2.1 2.1M17 17l2.1 2.1M2 12h3M19 12h3M4.9 19.1L7 17M17 7l2.1-2.1\"></path>",
            "titulo": "Energias Renováveis e Eficiência Energética",
            "texto": "Geração solar e eólica, geração distribuída, armazenamento e gestão do consumo elétrico."
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
                "Geometria Analítica e Álgebra Vetorial",
                "Química Geral e Tecnológica",
                "Introdução à Engenharia Elétrica",
                "Desenho Técnico e Representação Gráfica"
            ]
        },
        {
            "titulo": "2º semestre · Exatas",
            "disciplinas": [
                "Cálculo Diferencial e Integral II",
                "Álgebra Linear",
                "Física I — Mecânica",
                "Algoritmos e Programação",
                "Ciência dos Materiais Elétricos"
            ]
        },
        {
            "titulo": "3º semestre · Eletricidade e Magnetismo",
            "disciplinas": [
                "Cálculo Diferencial e Integral III",
                "Física II — Eletricidade e Magnetismo",
                "Circuitos Elétricos I",
                "Probabilidade e Estatística",
                "Programação Aplicada à Engenharia"
            ]
        },
        {
            "titulo": "4º semestre · Circuitos",
            "disciplinas": [
                "Circuitos Elétricos II",
                "Física III — Ondas e Óptica",
                "Equações Diferenciais",
                "Eletromagnetismo Aplicado",
                "Medidas Elétricas e Metrologia"
            ]
        },
        {
            "titulo": "5º semestre · Eletrônica",
            "disciplinas": [
                "Eletrônica Analógica",
                "Eletrônica Digital",
                "Sinais e Sistemas",
                "Conversão Eletromecânica de Energia",
                "Sensores e Transdutores"
            ]
        },
        {
            "titulo": "6º semestre · Máquinas Elétricas",
            "disciplinas": [
                "Máquinas Elétricas I",
                "Sistemas de Controle I",
                "Microcontroladores e Sistemas Embarcados",
                "Instalações Elétricas Prediais",
                "Materiais e Isolamentos Elétricos"
            ]
        },
        {
            "titulo": "7º semestre · Potência e Controle",
            "disciplinas": [
                "Máquinas Elétricas II e Acionamentos",
                "Sistemas de Controle II",
                "Eletrônica de Potência",
                "Instalações Elétricas Industriais",
                "Instrumentação e Automação"
            ]
        },
        {
            "titulo": "8º semestre · Sistemas de Energia",
            "disciplinas": [
                "Sistemas de Energia Elétrica I",
                "Geração, Transmissão e Distribuição",
                "Subestações e Equipamentos",
                "Projeto Elétrico Integrador I",
                "Engenharia Econômica"
            ]
        },
        {
            "titulo": "9º semestre · Proteção e Renováveis",
            "disciplinas": [
                "Sistemas de Energia Elétrica II",
                "Proteção de Sistemas Elétricos",
                "Energias Renováveis e Geração Distribuída",
                "Optativa I",
                "Trabalho de Conclusão I"
            ]
        },
        {
            "titulo": "10º semestre · Conclusão",
            "disciplinas": [
                "Segurança em Instalações Elétricas (NR-10)",
                "Eficiência Energética e Qualidade de Energia",
                "Optativa II",
                "Atividades Complementares",
                "Estágio Supervisionado",
                "Trabalho de Conclusão II"
            ]
        }
    ],
    "carreiraEyebrow": "Mercado de trabalho",
    "carreiraTitulo": "Onde você pode chegar",
    "carreiraTexto": "O engenheiro eletricista é essencial onde houver energia em movimento: concessionárias de geração, transmissão e distribuição, indústrias, construção civil, usinas renováveis e empresas de projeto e consultoria.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Geração de Energia",
        "Transmissão e Distribuição",
        "Projetos Elétricos Prediais e Industriais",
        "Automação e Controle",
        "Eletrônica de Potência",
        "Energias Renováveis",
        "Manutenção Industrial",
        "Consultoria e Eficiência Energética"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Engenharia Elétrica."
};

export default dados;
