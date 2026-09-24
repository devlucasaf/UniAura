// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/tecnologia/ciencia-de-dados.html ---
const dados = {
    "slug": "ciencia-de-dados",
    "subnavLabel": "Ciência de Dados",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Ciência de",
    "tituloDestaque": "Dados",
    "descricao": "Transforme dados em decisão. Estatística, programação e aprendizado de máquina aplicados a problemas reais de negócio, saúde, indústria e governo — do primeiro SELECT ao modelo em produção.",
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
    "codigoArquivo": "desempenho_por_curso.sql",
    "codigoLinhas": [
        "<span class=\"blue\">SELECT</span> c.nome <span class=\"blue\">AS</span> curso,",
        "       <span class=\"yellow\">COUNT</span>(<span class=\"blue\">DISTINCT</span> m.aluno_id) <span class=\"blue\">AS</span> alunos,",
        "       <span class=\"yellow\">ROUND</span>(<span class=\"yellow\">AVG</span>(n.valor), 2) <span class=\"blue\">AS</span> media",
        "  <span class=\"blue\">FROM</span> matricula m",
        "  <span class=\"blue\">JOIN</span> turma t <span class=\"blue\">ON</span> t.id = m.turma_id",
        "  <span class=\"blue\">JOIN</span> curso c <span class=\"blue\">ON</span> c.id = t.curso_id",
        "  <span class=\"blue\">JOIN</span> nota  n <span class=\"blue\">ON</span> n.aluno_id = m.aluno_id",
        " <span class=\"blue\">WHERE</span> m.status = <span class=\"green\">'ATIVA'</span>",
        " <span class=\"blue\">GROUP BY</span> c.nome",
        " <span class=\"blue\">ORDER BY</span> media <span class=\"blue\">DESC</span>;<span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
    ],
    "stats": [
        {
            "contador": true,
            "valor": "8",
            "sufixo": "",
            "label": "semestres"
        },
        {
            "contador": true,
            "valor": "3200",
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
    "formacaoTexto": "Da coleta e limpeza dos dados até modelos preditivos em produção, passando por estatística, engenharia de dados e comunicação de resultados.",
    "formacaoCards": [
        {
            "iconeSvg": "<ellipse cx=\"12\" cy=\"6\" rx=\"8\" ry=\"3\"></ellipse> <path d=\"M4 6v6c0 1.7 3.6 3 8 3s8-1.3 8-3V6\"></path> <path d=\"M4 12v6c0 1.7 3.6 3 8 3s8-1.3 8-3v-6\"></path>",
            "titulo": "Banco de Dados e SQL",
            "texto": "Modelagem relacional, SQL avançado, data warehouse e bancos NoSQL."
        },
        {
            "iconeSvg": "<path d=\"M3 21h18\"></path> <rect x=\"5\" y=\"11\" width=\"3\" height=\"7\"></rect> <rect x=\"10.5\" y=\"8\" width=\"3\" height=\"10\"></rect> <rect x=\"16\" y=\"5\" width=\"3\" height=\"13\"></rect>",
            "titulo": "Estatística e Probabilidade",
            "texto": "Inferência, testes de hipótese, regressão e delineamento de experimentos."
        },
        {
            "iconeSvg": "<path d=\"M9 4a3 3 0 0 0-3 3v1a3 3 0 0 0 0 6v1a3 3 0 0 0 3 3\"></path> <path d=\"M15 4a3 3 0 0 1 3 3v1a3 3 0 0 1 0 6v1a3 3 0 0 1-3 3\"></path> <path d=\"M9 12h6\"></path>",
            "titulo": "Aprendizado de Máquina",
            "texto": "Modelos supervisionados e não supervisionados, avaliação e ajuste de hiperparâmetros."
        },
        {
            "iconeSvg": "<path d=\"M4 17V9M9 17V5M14 17v-6M19 17v-9\"></path> <path d=\"M3 21h18\"></path>",
            "titulo": "Visualização de Dados",
            "texto": "Gráficos que comunicam, dashboards e narrativa para apoiar a tomada de decisão."
        },
        {
            "iconeSvg": "<path d=\"M18 10a5 5 0 0 0-9.6-1.8A4 4 0 1 0 7 18h10.5a3.5 3.5 0 0 0 .5-7Z\"></path>",
            "titulo": "Engenharia de Dados",
            "texto": "Pipelines, ETL, big data, processamento distribuído e nuvem."
        },
        {
            "iconeSvg": "<path d=\"M12 3l8 4.5v9L12 21l-8-4.5v-9L12 3Z\"></path> <path d=\"M12 12v9\"></path> <path d=\"M12 12l8-4.5\"></path>",
            "titulo": "Ética e Governança",
            "texto": "LGPD, privacidade, viés algorítmico e uso responsável de dados."
        }
    ],
    "matrizEyebrow": "Matriz curricular",
    "matrizTitulo": "Uma jornada de 8 semestres",
    "matrizTexto": "Clique em cada semestre para visualizar as principais disciplinas.",
    "semestres": [
        {
            "titulo": "1º semestre · Fundamentos",
            "disciplinas": [
                "Algoritmos e Programação I",
                "Cálculo Diferencial e Integral I",
                "Introdução à Ciência de Dados",
                "Matemática Discreta",
                "Comunicação e Expressão"
            ]
        },
        {
            "titulo": "2º semestre · Programação e Álgebra",
            "disciplinas": [
                "Algoritmos e Programação II",
                "Estruturas de Dados",
                "Álgebra Linear",
                "Cálculo Diferencial e Integral II",
                "Probabilidade"
            ]
        },
        {
            "titulo": "3º semestre · Bases de Dados",
            "disciplinas": [
                "Banco de Dados I",
                "Inferência Estatística",
                "Programação para Análise de Dados",
                "Sistemas Operacionais",
                "Métodos Numéricos"
            ]
        },
        {
            "titulo": "4º semestre · Modelagem",
            "disciplinas": [
                "Banco de Dados II",
                "Modelos de Regressão",
                "Visualização de Dados",
                "Redes de Computadores",
                "Séries Temporais"
            ]
        },
        {
            "titulo": "5º semestre · Aprendizado de Máquina",
            "disciplinas": [
                "Aprendizado de Máquina I",
                "Mineração de Dados",
                "Engenharia de Dados",
                "Computação em Nuvem",
                "Otimização"
            ]
        },
        {
            "titulo": "6º semestre · Big Data",
            "disciplinas": [
                "Aprendizado de Máquina II",
                "Big Data e Processamento Distribuído",
                "Processamento de Linguagem Natural",
                "Ética, Privacidade e LGPD",
                "Business Intelligence"
            ]
        },
        {
            "titulo": "7º semestre · Especialização",
            "disciplinas": [
                "Aprendizado Profundo",
                "MLOps e Modelos em Produção",
                "Tópicos Avançados em Ciência de Dados",
                "Optativa I",
                "Projeto de Conclusão I"
            ]
        },
        {
            "titulo": "8º semestre · Conclusão",
            "disciplinas": [
                "Ciência de Dados Aplicada",
                "Empreendedorismo e Inovação",
                "Optativa II",
                "Atividades Complementares",
                "Estágio Supervisionado",
                "Projeto de Conclusão II"
            ]
        }
    ],
    "carreiraEyebrow": "Mercado de trabalho",
    "carreiraTitulo": "Onde você pode chegar",
    "carreiraTexto": "Dado é insumo de toda empresa moderna. O cientista de dados atua em bancos, varejo, saúde, agronegócio, telecom e governo — em qualquer lugar onde decisão precise ser baseada em evidência.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Cientista de Dados",
        "Engenharia de Dados",
        "Analista de BI",
        "Machine Learning Engineer",
        "Estatístico",
        "Analista de Risco",
        "Data Product Manager",
        "Pesquisa e Inovação"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Ciência de Dados."
};

export default dados;
