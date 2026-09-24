// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/engenharia/engenharia-mecatronica.html ---
const dados = {
    "slug": "engenharia-mecatronica",
    "subnavLabel": "Engenharia Mecatrônica",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Engenharia",
    "tituloDestaque": "Mecatrônica",
    "descricao": "Onde mecânica, eletrônica e computação se encontram. Projete máquinas que enxergam, decidem e agem — de células robotizadas de manufatura a sistemas embarcados que controlam o mundo físico.",
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
    "codigoArquivo": "controle.c",
    "codigoLinhas": [
        "<span class=\"blue\">#include</span> <span class=\"green\">\"sensor.h\"</span>",
        " ",
        "<span class=\"blue\">void</span> <span class=\"yellow\">loop</span>(<span class=\"blue\">void</span>)",
        "{",
        "    <span class=\"blue\">float</span> alvo = 60.0f;",
        "    <span class=\"blue\">float</span> lido = <span class=\"yellow\">ler_temperatura</span>();",
        " ",
        "    <span class=\"yellow\">acionar</span>(AQUECEDOR, lido &lt; alvo);",
        "}<span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
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
    "formacaoTexto": "Uma formação de engenharia completa: base sólida em exatas, domínio das três áreas que compõem a mecatrônica e prática intensiva em laboratório.",
    "formacaoCards": [
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"3\"></circle> <path d=\"M12 2v3M12 19v3M2 12h3M19 12h3M4.9 4.9l2.1 2.1M17 17l2.1 2.1M4.9 19.1L7 17M17 7l2.1-2.1\"></path>",
            "titulo": "Mecânica e Materiais",
            "texto": "Estática, dinâmica, resistência dos materiais e projeto de elementos de máquinas."
        },
        {
            "iconeSvg": "<path d=\"M13 2L4 14h7l-1 8 9-12h-7l1-8Z\"></path>",
            "titulo": "Eletrônica e Potência",
            "texto": "Circuitos analógicos e digitais, eletrônica de potência e acionamento de motores."
        },
        {
            "iconeSvg": "<rect x=\"4\" y=\"4\" width=\"16\" height=\"16\" rx=\"2\"></rect> <path d=\"M9 9h6v6H9z\"></path> <path d=\"M9 2v2M15 2v2M9 20v2M15 20v2M2 9h2M2 15h2M20 9h2M20 15h2\"></path>",
            "titulo": "Sistemas Embarcados",
            "texto": "Microcontroladores, firmware em C, sensores, atuadores e protocolos industriais."
        },
        {
            "iconeSvg": "<path d=\"M3 17c3 0 3-10 6-10s3 10 6 10 3-6 6-6\"></path>",
            "titulo": "Sistemas de Controle",
            "texto": "Modelagem dinâmica, controle clássico e moderno, PID e resposta em frequência."
        },
        {
            "iconeSvg": "<rect x=\"5\" y=\"7\" width=\"14\" height=\"11\" rx=\"2\"></rect> <path d=\"M12 3v4\"></path> <circle cx=\"9\" cy=\"12\" r=\"1\"></circle> <circle cx=\"15\" cy=\"12\" r=\"1\"></circle> <path d=\"M9 16h6\"></path>",
            "titulo": "Robótica",
            "texto": "Cinemática, dinâmica de manipuladores, visão computacional e robôs móveis."
        },
        {
            "iconeSvg": "<path d=\"M4 20V8l8-5 8 5v12\"></path> <path d=\"M9 20v-6h6v6\"></path>",
            "titulo": "Automação Industrial",
            "texto": "CLP, SCADA, pneumática, hidráulica e integração de células de manufatura."
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
                "Introdução à Engenharia Mecatrônica",
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
            "titulo": "3º semestre · Mecânica",
            "disciplinas": [
                "Cálculo Diferencial e Integral III",
                "Física II — Eletromagnetismo",
                "Estática e Dinâmica",
                "Probabilidade e Estatística",
                "Programação Aplicada à Engenharia"
            ]
        },
        {
            "titulo": "4º semestre · Eletricidade",
            "disciplinas": [
                "Circuitos Elétricos",
                "Resistência dos Materiais",
                "Equações Diferenciais",
                "Termodinâmica",
                "Metrologia"
            ]
        },
        {
            "titulo": "5º semestre · Eletrônica",
            "disciplinas": [
                "Eletrônica Analógica",
                "Eletrônica Digital",
                "Mecânica dos Fluidos",
                "Elementos de Máquinas",
                "Sensores e Transdutores"
            ]
        },
        {
            "titulo": "6º semestre · Embarcados",
            "disciplinas": [
                "Microcontroladores e Sistemas Embarcados",
                "Sistemas de Controle I",
                "Eletrônica de Potência",
                "Pneumática e Hidráulica",
                "Processos de Fabricação"
            ]
        },
        {
            "titulo": "7º semestre · Controle",
            "disciplinas": [
                "Sistemas de Controle II",
                "Acionamentos Elétricos",
                "Automação Industrial e CLP",
                "Instrumentação Industrial",
                "Modelagem e Simulação de Sistemas"
            ]
        },
        {
            "titulo": "8º semestre · Robótica",
            "disciplinas": [
                "Robótica Industrial",
                "Visão Computacional",
                "Redes Industriais",
                "Projeto Mecatrônico I",
                "Engenharia Econômica"
            ]
        },
        {
            "titulo": "9º semestre · Integração",
            "disciplinas": [
                "Manufatura Integrada por Computador",
                "Indústria 4.0 e IoT Industrial",
                "Projeto Mecatrônico II",
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
    "carreiraTexto": "O engenheiro mecatrônico é procurado onde há linha de produção, máquina ou produto físico inteligente: automotivo, alimentício, farmacêutico, mineração, energia e desenvolvimento de equipamentos.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Automação Industrial",
        "Robótica",
        "Sistemas Embarcados",
        "Projeto de Máquinas",
        "Manutenção e Confiabilidade",
        "Instrumentação e Controle",
        "Indústria 4.0",
        "Pesquisa e Desenvolvimento"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Engenharia Mecatrônica."
};

export default dados;
