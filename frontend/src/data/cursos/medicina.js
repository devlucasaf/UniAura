// --- CONTEUDO EXTRAIDO FIELMENTE DO TEMPLATE ORIGINAL frontend/src/templates/web/graduacao/saude/medicina.html ---
const dados = {
    "slug": "medicina",
    "subnavLabel": "Medicina",
    "eyebrow": "Graduação · Bacharelado",
    "tituloPrincipal": "Curso de",
    "tituloDestaque": "Medicina",
    "descricao": "Uma formação médica generalista, humanista e baseada em evidências, que une ciência, ética e cuidado integral com a pessoa. Do ciclo básico às habilidades clínicas, até o internato em hospitais e unidades de saúde da rede pública.",
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
    "codigoArquivo": "prontuario.txt",
    "codigoLinhas": [
        "<span class=\"blue\">PACIENTE:</span> <span class=\"green\">M. A. S., sexo feminino</span>",
        "<span class=\"blue\">IDADE:</span> <span class=\"yellow\">54</span> <span class=\"green\">anos</span>",
        "<span class=\"blue\">QUEIXA:</span> <span class=\"green\">cefaleia há 3 dias</span>",
        "<span class=\"blue\">PA:</span> <span class=\"yellow\">130x85</span> <span class=\"green\">mmHg</span>",
        "<span class=\"blue\">FC:</span> <span class=\"yellow\">88</span> <span class=\"green\">bpm</span>",
        "<span class=\"blue\">TEMP:</span> <span class=\"yellow\">37,2</span> <span class=\"green\">°C</span>",
        "<span class=\"blue\">HIPÓTESE:</span> <span class=\"green\">hipertensão em investigação</span>",
        "<span class=\"blue\">CONDUTA:</span> <span class=\"green\">MAPA + retorno em 15 dias</span><span class=\"grad-janela-cursor\" aria-hidden=\"true\"></span>"
    ],
    "stats": [
        {
            "contador": true,
            "valor": "12",
            "sufixo": "",
            "label": "semestres"
        },
        {
            "contador": true,
            "valor": "7200",
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
    "formacaoTexto": "Uma formação médica completa: base sólida em ciências morfofuncionais, raciocínio clínico construído desde os primeiros semestres e prática supervisionada em ambulatórios, hospitais e unidades básicas de saúde.",
    "formacaoCards": [
        {
            "iconeSvg": "<circle cx=\"12\" cy=\"5\" r=\"2.5\"></circle> <path d=\"M12 7.5v6\"></path> <path d=\"M6 10.5h12\"></path> <path d=\"M9.5 13.5L8 21M14.5 13.5L16 21\"></path>",
            "titulo": "Ciências Morfofuncionais",
            "texto": "Anatomia, histologia e fisiologia integradas para compreender o corpo humano em funcionamento."
        },
        {
            "iconeSvg": "<path d=\"M6 3v6a5 5 0 0 0 10 0V3\"></path> <path d=\"M4 3h3M15 3h3\"></path> <path d=\"M11 14v2a5 5 0 0 0 9 3\"></path> <circle cx=\"20\" cy=\"17\" r=\"2\"></circle>",
            "titulo": "Semiologia e Exame Clínico",
            "texto": "Anamnese, exame físico e raciocínio diagnóstico construídos junto ao paciente."
        },
        {
            "iconeSvg": "<path d=\"M3 12h4l2-5 3 10 2-5h7\"></path>",
            "titulo": "Clínica Médica",
            "texto": "Diagnóstico e tratamento das principais doenças do adulto, com terapêutica baseada em evidências."
        },
        {
            "iconeSvg": "<path d=\"M4 20l9-9\"></path> <path d=\"M14 10l6-6-2-2-6 6\"></path> <circle cx=\"6\" cy=\"6\" r=\"3\"></circle> <path d=\"M8.5 8.5L20 20\"></path>",
            "titulo": "Cirurgia e Urgências",
            "texto": "Técnica cirúrgica, atendimento ao paciente grave e condutas em urgência e emergência."
        },
        {
            "iconeSvg": "<path d=\"M12 21s-7-4.4-7-9.5A4.5 4.5 0 0 1 12 8a4.5 4.5 0 0 1 7 3.5C19 16.6 12 21 12 21Z\"></path>",
            "titulo": "Saúde da Mulher e da Criança",
            "texto": "Pré-natal, parto, puerpério, crescimento e desenvolvimento infantil ao longo do ciclo de vida."
        },
        {
            "iconeSvg": "<circle cx=\"9\" cy=\"7\" r=\"3\"></circle> <path d=\"M2 21v-1.5A4.5 4.5 0 0 1 6.5 15h5a4.5 4.5 0 0 1 4.5 4.5V21\"></path> <path d=\"M17 8h4M19 6v4\"></path>",
            "titulo": "Saúde Coletiva e Atenção Primária",
            "texto": "Epidemiologia, políticas públicas e cuidado longitudinal das famílias no SUS."
        }
    ],
    "matrizEyebrow": "Matriz curricular",
    "matrizTitulo": "Uma jornada de 12 semestres",
    "matrizTexto": "Clique em cada semestre para visualizar as principais disciplinas.",
    "semestres": [
        {
            "titulo": "1º semestre · Ciclo básico",
            "disciplinas": [
                "Anatomia Humana I",
                "Biologia Celular",
                "Histologia",
                "Bioquímica",
                "Introdução à Medicina e Saúde"
            ]
        },
        {
            "titulo": "2º semestre · Morfologia",
            "disciplinas": [
                "Anatomia Humana II",
                "Embriologia",
                "Fisiologia I",
                "Genética Médica",
                "Bioestatística e Epidemiologia"
            ]
        },
        {
            "titulo": "3º semestre · Agentes e defesa",
            "disciplinas": [
                "Fisiologia II",
                "Imunologia",
                "Microbiologia",
                "Parasitologia",
                "Saúde Coletiva"
            ]
        },
        {
            "titulo": "4º semestre · Mecanismos da doença",
            "disciplinas": [
                "Patologia Geral",
                "Farmacologia",
                "Fisiopatologia",
                "Semiologia Médica I",
                "Habilidades e Atitudes Médicas"
            ]
        },
        {
            "titulo": "5º semestre · Semiologia",
            "disciplinas": [
                "Semiologia Médica II",
                "Patologia Especial",
                "Farmacologia Clínica",
                "Diagnóstico por Imagem",
                "Medicina Baseada em Evidências"
            ]
        },
        {
            "titulo": "6º semestre · Atenção primária",
            "disciplinas": [
                "Clínica Médica I",
                "Atenção Primária à Saúde",
                "Psicologia Médica",
                "Nutrição Clínica",
                "Métodos Diagnósticos Laboratoriais"
            ]
        },
        {
            "titulo": "7º semestre · Clínica e cirurgia",
            "disciplinas": [
                "Clínica Médica II",
                "Cirurgia I",
                "Pediatria",
                "Psiquiatria",
                "Terapêutica Clínica"
            ]
        },
        {
            "titulo": "8º semestre · Ciclo da vida",
            "disciplinas": [
                "Clínica Médica III",
                "Cirurgia II",
                "Ginecologia e Obstetrícia",
                "Urgência e Emergência",
                "Medicina Legal e Bioética"
            ]
        },
        {
            "titulo": "9º semestre · Especialidades",
            "disciplinas": [
                "Cardiologia",
                "Neurologia Clínica",
                "Ortopedia e Traumatologia",
                "Dermatologia",
                "Oftalmologia e Otorrinolaringologia"
            ]
        },
        {
            "titulo": "10º semestre · Pré-internato",
            "disciplinas": [
                "Infectologia",
                "Geriatria",
                "Oncologia",
                "Medicina do Trabalho",
                "Trabalho de Conclusão de Curso I"
            ]
        },
        {
            "titulo": "11º semestre · Internato I",
            "disciplinas": [
                "Internato em Clínica Médica",
                "Internato em Cirurgia",
                "Internato em Pediatria",
                "Internato em Urgência e Emergência",
                "Estágio em Saúde Mental"
            ]
        },
        {
            "titulo": "12º semestre · Internato II",
            "disciplinas": [
                "Internato em Ginecologia e Obstetrícia",
                "Internato em Saúde da Família",
                "Internato em Terapia Intensiva",
                "Estágio Eletivo Supervisionado",
                "Trabalho de Conclusão de Curso II"
            ]
        }
    ],
    "carreiraEyebrow": "Mercado de trabalho",
    "carreiraTitulo": "Onde você pode chegar",
    "carreiraTexto": "O médico formado na UniAura está preparado para o cuidado em todos os níveis de atenção — do posto de saúde ao hospital terciário — e para seguir na residência médica, na pesquisa clínica ou na gestão em saúde.",
    "carreiraCardTitulo": "Possíveis áreas de atuação",
    "carreiraItens": [
        "Clínica Médica",
        "Cirurgia",
        "Pediatria",
        "Ginecologia e Obstetrícia",
        "Medicina de Família",
        "Urgência e Emergência",
        "Pesquisa Clínica",
        "Docência e Gestão em Saúde"
    ],
    "ctaTitulo": "Pronto para começar sua jornada?",
    "ctaTexto": "As inscrições para o próximo semestre estão abertas. Faça sua matrícula on-line e garanta sua vaga em Medicina."
};

export default dados;
