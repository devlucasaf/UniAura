// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/engenharia/engenharia-florestal.html ---
const dados = {
    "slug": "engenharia-florestal",
    "subnavLabel": "Engenharia Florestal",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Engenharia",
    "tituloDestaque": "Florestal",
    "descricao": "Manejo sustentável de florestas, silvicultura e conservação dos recursos naturais. Uma engenharia que une ecologia, inventário de campo, geoprocessamento e tecnologia da madeira para produzir sem destruir.",
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
    "codigoArquivo": "inventario.R",
    "codigoLinhas": [
        "parcelas &lt;- <span class=\"yellow\">read.csv</span>(<span class=\"green\">\"parcelas.csv\"</span>)",
        " ",
        "parcelas$g &lt;- pi * (parcelas$dap / 200)^2",
        " ",
        "smalian &lt;- <span class=\"blue\">function</span>(g1, g2, l) (g1 + g2) / 2 * l",
        "parcelas$vol &lt;- <span class=\"yellow\">smalian</span>(parcelas$g1, parcelas$g2, parcelas$L)",
        " ",
        "area_basal &lt;- <span class=\"yellow\">sum</span>(parcelas$g) / 0.05  <span class=\"green\"># m2/ha</span>",
        "<span class=\"yellow\">cat</span>(<span class=\"green\">\"Volume medio:\"</span>, <span class=\"yellow\">mean</span>(parcelas$vol))<span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
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
    "formacaoTexto": "Uma formação de engenharia completa: base sólida em exatas e ciências biológicas, domínio técnico do manejo florestal e prática intensiva de campo, viveiro e laboratório.",
    "formacaoCards": [
        {
            "iconeSvg": "<path d=\"M12 22v-7\"></path> <path d=\"M12 15c0-4 3-7 7-7 0 4-3 7-7 7Z\"></path> <path d=\"M12 17c0-3-2.5-5.5-5.5-5.5C6.5 14.5 9 17 12 17Z\"></path>",
            "titulo": "Silvicultura e Viveiros",
            "texto": "Produção de mudas, sementes florestais, plantio, tratos culturais e nutrição de povoamentos."
        },
        {
            "iconeSvg": "<path d=\"M3 20h18\"></path> <path d=\"M6 20V9M12 20V4M18 20v-7\"></path> <path d=\"M3 9l3-3 3 3M9 4l3-3 3 3M15 13l3-3 3 3\"></path>",
            "titulo": "Manejo Florestal Sustentável",
            "texto": "Dendrometria, inventário, regulação da produção e planos de manejo de florestas nativas e plantadas."
        },
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"9\"></circle> <path d=\"M3 12h18\"></path> <path d=\"M12 3a15 15 0 0 1 0 18a15 15 0 0 1 0-18Z\"></path>",
            "titulo": "Ecologia e Conservação",
            "texto": "Dinâmica de ecossistemas, biodiversidade, fauna silvestre e restauração de paisagens."
        },
        {
            "iconeSvg": "<path d=\"M12 2.7S6.5 9 6.5 13a5.5 5.5 0 0 0 11 0c0-4-5.5-10.3-5.5-10.3Z\"></path> <path d=\"M3 21h18\"></path>",
            "titulo": "Solos e Hidrologia Florestal",
            "texto": "Gênese e física do solo, fertilidade, erosão, ciclo da água e manejo de bacias hidrográficas."
        },
        {
            "iconeSvg": "<polygon points=\"1 6 8 3 16 6 23 3 23 18 16 21 8 18 1 21 1 6\"></polygon> <path d=\"M8 3v15M16 6v15\"></path>",
            "titulo": "Geoprocessamento e Sensoriamento Remoto",
            "texto": "SIG, imagens de satélite, LiDAR e monitoramento do desmatamento e da cobertura florestal."
        },
        {
            "iconeSvg": "<rect x=\"3\" y=\"7\" width=\"18\" height=\"10\" rx=\"2\"></rect> <circle cx=\"8\" cy=\"12\" r=\"2.5\"></circle> <path d=\"M14 10h5M14 14h5\"></path>",
            "titulo": "Tecnologia e Produtos da Madeira",
            "texto": "Anatomia e secagem da madeira, painéis, celulose, papel e energia da biomassa florestal."
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
                "Química Geral",
                "Biologia Vegetal",
                "Introdução à Engenharia Florestal",
                "Desenho Técnico e Geometria Descritiva"
            ]
        },
        {
            "titulo": "2º semestre · Ciências básicas",
            "disciplinas": [
                "Cálculo Diferencial e Integral II",
                "Física Aplicada",
                "Química Orgânica e Analítica",
                "Botânica Florestal",
                "Estatística Básica"
            ]
        },
        {
            "titulo": "3º semestre · Meio físico",
            "disciplinas": [
                "Gênese e Física do Solo",
                "Dendrologia",
                "Meteorologia e Climatologia Florestal",
                "Topografia",
                "Experimentação Florestal"
            ]
        },
        {
            "titulo": "4º semestre · Ecologia",
            "disciplinas": [
                "Ecologia Florestal",
                "Fisiologia Vegetal",
                "Fertilidade e Nutrição Florestal",
                "Genética Florestal",
                "Geologia e Geomorfologia"
            ]
        },
        {
            "titulo": "5º semestre · Silvicultura",
            "disciplinas": [
                "Silvicultura",
                "Viveiros e Produção de Mudas",
                "Sementes Florestais",
                "Dendrometria",
                "Microbiologia do Solo"
            ]
        },
        {
            "titulo": "6º semestre · Inventário",
            "disciplinas": [
                "Inventário Florestal",
                "Entomologia e Patologia Florestal",
                "Hidrologia e Manejo de Bacias Hidrográficas",
                "Sensoriamento Remoto e SIG",
                "Anatomia e Identificação da Madeira"
            ]
        },
        {
            "titulo": "7º semestre · Manejo",
            "disciplinas": [
                "Manejo Florestal Sustentável",
                "Melhoramento Genético Florestal",
                "Tecnologia da Madeira",
                "Mecanização Florestal",
                "Conservação da Natureza e Fauna Silvestre"
            ]
        },
        {
            "titulo": "8º semestre · Produção",
            "disciplinas": [
                "Colheita e Transporte Florestal",
                "Produtos Florestais e Celulose",
                "Economia e Política Florestal",
                "Sistemas Agroflorestais",
                "Legislação e Licenciamento Ambiental"
            ]
        },
        {
            "titulo": "9º semestre · Restauração",
            "disciplinas": [
                "Recuperação de Áreas Degradadas",
                "Prevenção e Combate a Incêndios Florestais",
                "Avaliação de Impactos e Perícia Ambiental",
                "Optativa I",
                "Trabalho de Conclusão I"
            ]
        },
        {
            "titulo": "10º semestre · Conclusão",
            "disciplinas": [
                "Certificação Florestal e Auditoria",
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
    "carreiraTexto": "O engenheiro florestal atua onde há floresta, madeira ou recurso natural a ser manejado: empresas de celulose e papel, reflorestamento, órgãos ambientais, consultorias, unidades de conservação e pesquisa.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Manejo Florestal",
        "Silvicultura e Reflorestamento",
        "Licenciamento e Perícia Ambiental",
        "Indústria de Base Florestal",
        "Certificação Florestal (FSC)",
        "Unidades de Conservação",
        "Geoprocessamento",
        "Pesquisa e Melhoramento Genético"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Engenharia Florestal."
};

export default dados;
