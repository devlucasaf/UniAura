// --- CATALOGO EXTRAIDO FIELMENTE DE frontend/src/templates/web/graduacao/cursos.html ---
export const tituloPagina = "Nossos Cursos";
export const subtituloPagina = "Escolha a graduação que vai impulsionar sua carreira";
export const filtros = [
    {
        "valor": "todos",
        "label": "Todos"
    },
    {
        "valor": "ti",
        "label": "Tecnologia da Informação"
    },
    {
        "valor": "engenharias",
        "label": "Engenharias"
    },
    {
        "valor": "saude",
        "label": "Saúde"
    },
    {
        "valor": "negocios",
        "label": "Negócios"
    },
    {
        "valor": "humanas",
        "label": "Humanas"
    },
    {
        "valor": "artes",
        "label": "Artes e Comunicação"
    }
];
export const cards = [
    {
        "slug": "ciencia-da-computacao",
        "area": "ti",
        "cor": "#1e40af",
        "iconeSvg": "<rect x=\"3\" y=\"4\" width=\"18\" height=\"13\" rx=\"2\"></rect> <path d=\"M8 20h8\"></path> <path d=\"M10 17v3M14 17v3\"></path>",
        "titulo": "Ciência da Computação",
        "descricao": "Formação sólida em algoritmos, estruturas de dados, engenharia de software e sistemas computacionais.",
        "tag": "Bacharelado"
    },
    {
        "slug": "analise-e-desenvolvimento-de-sistemas",
        "area": "ti",
        "cor": "#059669",
        "iconeSvg": "<rect x=\"7\" y=\"2\" width=\"10\" height=\"20\" rx=\"2\"></rect> <path d=\"M11 18h2\"></path>",
        "titulo": "Análise e Desenvolvimento de Sistemas",
        "descricao": "Foco prático em desenvolvimento de software, banco de dados e metodologias ágeis.",
        "tag": "Tecnólogo"
    },
    {
        "slug": "engenharia-de-software",
        "area": "ti",
        "cor": "#b45309",
        "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"3\"></circle> <path d=\"M19.4 15a1.7 1.7 0 0 0 .3 1.9l.1.1a2 2 0 1 1-2.8 2.8l-.1-.1a1.7 1.7 0 0 0-1.9-.3 1.7 1.7 0 0 0-1 1.5V21a2 2 0 1 1-4 0v-.2a1.7 1.7 0 0 0-1-1.5 1.7 1.7 0 0 0-1.9.3l-.1.1a2 2 0 1 1-2.8-2.8l.1-.1a1.7 1.7 0 0 0 .3-1.9 1.7 1.7 0 0 0-1.5-1H3a2 2 0 1 1 0-4h.2a1.7 1.7 0 0 0 1.5-1 1.7 1.7 0 0 0-.3-1.9l-.1-.1a2 2 0 1 1 2.8-2.8l.1.1a1.7 1.7 0 0 0 1.9.3h0a1.7 1.7 0 0 0 1-1.5V3a2 2 0 1 1 4 0v.2a1.7 1.7 0 0 0 1 1.5h0a1.7 1.7 0 0 0 1.9-.3l.1-.1a2 2 0 1 1 2.8 2.8l-.1.1a1.7 1.7 0 0 0-.3 1.9v0a1.7 1.7 0 0 0 1.5 1H21a2 2 0 1 1 0 4h-.2a1.7 1.7 0 0 0-1.5 1z\"></path>",
        "titulo": "Engenharia de Software",
        "descricao": "Projeto, desenvolvimento e manutenção de sistemas de grande escala com qualidade e produtividade.",
        "tag": "Bacharelado"
    },
    {
        "slug": "ciencia-de-dados",
        "area": "ti",
        "cor": "#7c3aed",
        "iconeSvg": "<path d=\"M3 21h18\"></path> <rect x=\"5\" y=\"11\" width=\"3\" height=\"7\"></rect> <rect x=\"10.5\" y=\"8\" width=\"3\" height=\"10\"></rect> <rect x=\"16\" y=\"5\" width=\"3\" height=\"13\"></rect>",
        "titulo": "Ciência de Dados",
        "descricao": "Análise, modelagem e extração de insights a partir de grandes volumes de dados.",
        "tag": "Bacharelado"
    },
    {
        "slug": "engenharia-mecatronica",
        "area": "engenharias",
        "cor": "#475569",
        "iconeSvg": "<rect x=\"5\" y=\"7\" width=\"14\" height=\"11\" rx=\"2\"></rect> <path d=\"M12 3v4\"></path> <circle cx=\"9\" cy=\"12\" r=\"1\"></circle> <circle cx=\"15\" cy=\"12\" r=\"1\"></circle> <path d=\"M9 16h6\"></path>",
        "titulo": "Engenharia Mecatrônica",
        "descricao": "Integração de mecânica, eletrônica e computação para automação e robótica.",
        "tag": "Bacharelado"
    },
    {
        "slug": "engenharia-civil",
        "area": "engenharias",
        "cor": "#0f766e",
        "iconeSvg": "<path d=\"M3 21h18\"></path> <path d=\"M6 21V8l6-4 6 4v13\"></path> <path d=\"M10 21v-6h4v6\"></path>",
        "titulo": "Engenharia Civil",
        "descricao": "Projeto e execução de obras, estruturas, saneamento e infraestrutura urbana.",
        "tag": "Bacharelado"
    },
    {
        "slug": "engenharia-mecanica",
        "area": "engenharias",
        "cor": "#b91c1c",
        "iconeSvg": "<circle cx=\"12\" cy=\"12\" r=\"3\"></circle> <path d=\"M12 2v3M12 19v3M2 12h3M19 12h3M4.9 4.9l2.1 2.1M17 17l2.1 2.1M4.9 19.1L7 17M17 7l2.1-2.1\"></path>",
        "titulo": "Engenharia Mecânica",
        "descricao": "Projeto de máquinas, materiais, termodinâmica, fluidos e processos de fabricação.",
        "tag": "Bacharelado"
    },
    {
        "slug": "engenharia-producao",
        "area": "engenharias",
        "cor": "#c2410c",
        "iconeSvg": "<path d=\"M3 20V9l5 3V9l5 3V9l5 3v8Z\"></path> <path d=\"M3 20h18\"></path>",
        "titulo": "Engenharia de Produção",
        "descricao": "Otimização de processos, logística, qualidade e gestão de operações industriais.",
        "tag": "Bacharelado"
    },
    {
        "slug": "engenharia-eletrica",
        "area": "engenharias",
        "cor": "#a16207",
        "iconeSvg": "<path d=\"M13 2L4 14h7l-1 8 9-12h-7l1-8Z\"></path>",
        "titulo": "Engenharia Elétrica",
        "descricao": "Geração, transmissão e distribuição de energia, além de eletrônica e automação.",
        "tag": "Bacharelado"
    },
    {
        "slug": "engenharia-quimica",
        "area": "engenharias",
        "cor": "#7e22ce",
        "iconeSvg": "<path d=\"M9 3h6\"></path> <path d=\"M10 3v6L4.5 18A2 2 0 0 0 6.2 21h11.6a2 2 0 0 0 1.7-3L14 9V3\"></path> <path d=\"M7.5 15h9\"></path>",
        "titulo": "Engenharia Química",
        "descricao": "Reatores, operações unitárias e processos industriais de transformação da matéria.",
        "tag": "Bacharelado"
    },
    {
        "slug": "engenharia-florestal",
        "area": "engenharias",
        "cor": "#15803d",
        "iconeSvg": "<path d=\"M12 22v-6\"></path> <path d=\"M12 16 6.5 9h3L12 2l2.5 7h3L12 16Z\"></path>",
        "titulo": "Engenharia Florestal",
        "descricao": "Manejo sustentável, silvicultura, conservação e tecnologia de produtos florestais.",
        "tag": "Bacharelado"
    },
    {
        "slug": "ciencias-aeronauticas",
        "area": "engenharias",
        "cor": "#0369a1",
        "iconeSvg": "<path d=\"M17.8 19.2 16 11l3.5-3.5a2.1 2.1 0 0 0-3-3L13 8 4.8 6.2a1 1 0 0 0-.9 1.7L8 11l-2 3H3.5a1 1 0 0 0-.7 1.7l2.3 2.3 2.3 2.3a1 1 0 0 0 1.7-.7V17l3-2 3.1 4.1a1 1 0 0 0 1.7-.9Z\"></path>",
        "titulo": "Ciências Aeronáuticas",
        "descricao": "Formação de pilotos e gestores da aviação civil: navegação, meteorologia e operações.",
        "tag": "Bacharelado"
    },
    {
        "slug": null,
        "area": "engenharias",
        "cor": "#4d7c0f",
        "iconeSvg": "<path d=\"M4 21V7l8-4 8 4v14\"></path> <path d=\"M4 12h16M12 3v18\"></path>",
        "titulo": "Arquitetura e Urbanismo",
        "descricao": "Projeto arquitetônico, planejamento urbano, paisagismo e patrimônio construído.",
        "tag": "Bacharelado"
    },
    {
        "slug": "medicina",
        "area": "saude",
        "cor": "#dc2626",
        "iconeSvg": "<path d=\"M12 5v14\"></path> <path d=\"M5 12h14\"></path>",
        "titulo": "Medicina",
        "descricao": "Formação médica completa com internato, prática clínica e cuidado integral do paciente.",
        "tag": "Bacharelado"
    },
    {
        "slug": "odontologia",
        "area": "saude",
        "cor": "#0891b2",
        "iconeSvg": "<path d=\"M12 5.5C10.5 4 8.5 3.5 7 4.5 5 6 5 9 6 13c.6 2.4 1 7 2.5 7s1.4-4 3.5-4 2 4 3.5 4 1.9-4.6 2.5-7c1-4 1-7-1-8.5-1.5-1-3.5-.5-5 1z\"></path>",
        "titulo": "Odontologia",
        "descricao": "Saúde bucal, dentística, endodontia, prótese e cirurgia com clínica-escola própria.",
        "tag": "Bacharelado"
    },
    {
        "slug": "farmacia",
        "area": "saude",
        "cor": "#7c3aed",
        "iconeSvg": "<rect x=\"3\" y=\"8\" width=\"18\" height=\"13\" rx=\"3\"></rect> <path d=\"M8 14h8\"></path> <path d=\"M12 10v8\"></path> <path d=\"M8 8V5a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v3\"></path>",
        "titulo": "Farmácia",
        "descricao": "Medicamentos, análises clínicas, farmacologia e assistência farmacêutica.",
        "tag": "Bacharelado"
    },
    {
        "slug": "fisioterapia",
        "area": "saude",
        "cor": "#2563eb",
        "iconeSvg": "<circle cx=\"13\" cy=\"4\" r=\"2\"></circle> <path d=\"M6 21l3-6 4-2 2 4 4 1\"></path> <path d=\"M9 15l-1-5 5-2 3 3 3 1\"></path>",
        "titulo": "Fisioterapia",
        "descricao": "Reabilitação motora, cinesioterapia, traumato-ortopedia e fisioterapia respiratória.",
        "tag": "Bacharelado"
    },
    {
        "slug": "nutricao",
        "area": "saude",
        "cor": "#65a30d",
        "iconeSvg": "<path d=\"M12 21c-4 0-7-3.5-7-8 0-3.5 2.5-6 5-6 1.2 0 1.7.5 2 .5s.8-.5 2-.5c2.5 0 5 2.5 5 6 0 4.5-3 8-7 8z\"></path> <path d=\"M12 7c0-2 1.5-4 4-4\"></path>",
        "titulo": "Nutrição",
        "descricao": "Alimentação, dietoterapia, nutrição clínica, esportiva e saúde coletiva.",
        "tag": "Bacharelado"
    },
    {
        "slug": "biomedicina",
        "area": "saude",
        "cor": "#4f46e5",
        "iconeSvg": "<path d=\"M3 12h4l2 5 4-10 2 5h6\"></path>",
        "titulo": "Biomedicina",
        "descricao": "Análises clínicas, biotecnologia, imagenologia e pesquisa em saúde humana.",
        "tag": "Bacharelado"
    },
    {
        "slug": "biologia",
        "area": "saude",
        "cor": "#16a34a",
        "iconeSvg": "<path d=\"M5 21c0-8 4-14 14-18\"></path> <path d=\"M5 21c8 0 14-4 18-14\"></path> <path d=\"M7 13c4 0 7 1 9 4\"></path>",
        "titulo": "Ciências Biológicas",
        "descricao": "Biodiversidade, genética, ecologia e biotecnologia com forte prática de campo.",
        "tag": "Bacharelado"
    },
    {
        "slug": "educacao-fisica",
        "area": "saude",
        "cor": "#d97706",
        "iconeSvg": "<path d=\"M6 8v8\"></path> <path d=\"M18 8v8\"></path> <path d=\"M3 10v4\"></path> <path d=\"M21 10v4\"></path> <path d=\"M6 12h12\"></path>",
        "titulo": "Educação Física",
        "descricao": "Treinamento esportivo, fisiologia do exercício, saúde e performance humana.",
        "tag": "Bacharelado"
    },
    {
        "slug": "fonoaudiologia",
        "area": "saude",
        "cor": "#9d174d",
        "iconeSvg": "<path d=\"M4 9v6h4l5 4V5L8 9H4z\"></path> <path d=\"M17 8.5a5 5 0 0 1 0 7\"></path> <path d=\"M19.5 6a8 8 0 0 1 0 12\"></path>",
        "titulo": "Fonoaudiologia",
        "descricao": "Linguagem, voz, audição, motricidade orofacial e disfagia em todas as idades.",
        "tag": "Bacharelado"
    },
    {
        "slug": "terapia-ocupacional",
        "area": "saude",
        "cor": "#9333ea",
        "iconeSvg": "<path d=\"M9 11V4.5a1.5 1.5 0 0 1 3 0V11\"></path> <path d=\"M12 11V6a1.5 1.5 0 0 1 3 0v5\"></path> <path d=\"M15 11V8a1.5 1.5 0 0 1 3 0v6a7 7 0 0 1-7 7h-1a6 6 0 0 1-6-6v-4a1.5 1.5 0 0 1 3 0\"></path>",
        "titulo": "Terapia Ocupacional",
        "descricao": "Autonomia, reabilitação funcional, tecnologia assistiva e inclusão social.",
        "tag": "Bacharelado"
    },
    {
        "slug": "medicina-veterinaria",
        "area": "saude",
        "cor": "#92400e",
        "iconeSvg": "<circle cx=\"6\" cy=\"9\" r=\"2\"></circle> <circle cx=\"10.5\" cy=\"5.5\" r=\"2\"></circle> <circle cx=\"15.5\" cy=\"5.5\" r=\"2\"></circle> <circle cx=\"19\" cy=\"10\" r=\"2\"></circle> <path d=\"M12.5 11c2.5 0 4.5 2.2 4.5 4.7 0 2-1.4 3.3-3.2 3.3-1 0-1.4-.4-2.3-.4s-1.3.4-2.3.4C7.4 19 6 17.7 6 15.7 6 13.2 10 11 12.5 11z\"></path>",
        "titulo": "Medicina Veterinária",
        "descricao": "Clínica de pequenos e grandes animais, cirurgia, produção e saúde pública.",
        "tag": "Bacharelado"
    },
    {
        "slug": "administracao",
        "area": "negocios",
        "cor": "#4338ca",
        "iconeSvg": "<rect x=\"2\" y=\"7\" width=\"20\" height=\"14\" rx=\"2\"></rect> <path d=\"M9 7V5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2v2\"></path>",
        "titulo": "Administração",
        "descricao": "Gestão de pessoas, finanças, marketing e estratégia para liderar organizações.",
        "tag": "Bacharelado"
    },
    {
        "slug": "direito",
        "area": "humanas",
        "cor": "#9f1239",
        "iconeSvg": "<path d=\"M12 3v18\"></path> <path d=\"M5 8h14\"></path> <path d=\"M7 8l-3 5h6l-3-5Z\"></path> <path d=\"M17 8l-3 5h6l-3-5Z\"></path>",
        "titulo": "Direito",
        "descricao": "Formação jurídica sólida em direito público, privado e penal, com prática processual desde o 8º semestre.",
        "tag": "Bacharelado"
    },
    {
        "slug": "design",
        "area": "artes",
        "cor": "#0d9488",
        "iconeSvg": "<path d=\"m2 22 5-5\"></path> <path d=\"M12 2 6.5 15.5\"></path> <path d=\"M12 2l5.5 13.5\"></path> <circle cx=\"12\" cy=\"4\" r=\"2\"></circle>",
        "titulo": "Design",
        "descricao": "Identidade visual, tipografia, UX/UI e design de produto para resolver problemas com forma e função.",
        "tag": "Bacharelado"
    },
    {
        "slug": "publicidade-propaganda",
        "area": "artes",
        "cor": "#ea580c",
        "iconeSvg": "<path d=\"M3 11v2a1 1 0 0 0 1 1h3l6 4V6L7 10H4a1 1 0 0 0-1 1Z\"></path> <path d=\"M17 9a4 4 0 0 1 0 6\"></path> <path d=\"M20 6.5a8 8 0 0 1 0 11\"></path>",
        "titulo": "Publicidade e Propaganda",
        "descricao": "Criação, redação, direção de arte, mídia e comunicação digital para campanhas que movem pessoas.",
        "tag": "Bacharelado"
    },
    {
        "slug": "artes-visuais",
        "area": "artes",
        "cor": "#c026d3",
        "iconeSvg": "<circle cx=\"13.5\" cy=\"6.5\" r=\"1\"></circle> <circle cx=\"17.5\" cy=\"10.5\" r=\"1\"></circle> <circle cx=\"8.5\" cy=\"7.5\" r=\"1\"></circle> <circle cx=\"6.5\" cy=\"12.5\" r=\"1\"></circle> <path d=\"M12 2a10 10 0 1 0 0 20c.9 0 1.6-.7 1.6-1.6 0-.4-.2-.8-.5-1.1-.3-.3-.5-.7-.5-1.1 0-.9.7-1.6 1.6-1.6H16a6 6 0 0 0 6-6c0-5-4.5-8.6-10-8.6Z\"></path>",
        "titulo": "Artes Visuais",
        "descricao": "Desenho, pintura, gravura, escultura, arte digital, curadoria e pesquisa em artes.",
        "tag": "Bacharelado"
    },
    {
        "slug": "artes-cenicas",
        "area": "artes",
        "cor": "#be123c",
        "iconeSvg": "<path d=\"M3 5v6a6 6 0 0 0 6 6 6 6 0 0 0 6-6V5Z\"></path> <path d=\"M9 21h6\"></path> <path d=\"M15 8h6v3a5 5 0 0 1-5 5\"></path>",
        "titulo": "Artes Cênicas",
        "descricao": "Interpretação, corpo e voz, direção, dramaturgia e produção teatral.",
        "tag": "Bacharelado"
    },
    {
        "slug": "moda",
        "area": "artes",
        "cor": "#db2777",
        "iconeSvg": "<circle cx=\"6\" cy=\"6\" r=\"3\"></circle> <circle cx=\"6\" cy=\"18\" r=\"3\"></circle> <path d=\"M20 4 8.12 15.88\"></path> <path d=\"M14.47 14.48 20 20\"></path> <path d=\"M8.12 8.12 12 12\"></path>",
        "titulo": "Design de Moda",
        "descricao": "Do croqui à peça pronta: modelagem, costura, têxteis, tendências e moda sustentável.",
        "tag": "Tecnólogo"
    },
    {
        "slug": "fotografia",
        "area": "artes",
        "cor": "#334155",
        "iconeSvg": "<path d=\"M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z\"></path> <circle cx=\"12\" cy=\"13\" r=\"4\"></circle>",
        "titulo": "Fotografia",
        "descricao": "Luz, composição e narrativa visual — do estúdio ao fotojornalismo e ao tratamento digital.",
        "tag": "Tecnólogo"
    }
];
