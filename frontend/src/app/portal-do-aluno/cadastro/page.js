"use client";

import { Suspense, useCallback, useEffect, useRef, useState }   from "react";
import { useRouter, useSearchParams }                           from "next/navigation";
import { InputText }                                            from "primereact/inputtext";
import { Dropdown }                                             from "primereact/dropdown";
import { Dialog }                                               from "primereact/dialog";
import { Checkbox }                                             from "primereact/checkbox";
import { Button }                                               from "primereact/button";
import { Message }                                              from "primereact/message";
import SiteChrome                                               from "@/components/web/SiteChrome";
import MolduraAluno                                             from "@/screens/portal-do-aluno/MolduraAluno";
import CampoData                                                from "@/components/web/CampoData";
import CampoSenha                                               from "@/components/web/CampoSenha";
import { useEfeitosDePagina }                                   from "@/hooks/useEfeitosDePagina";
import { mascararCpf, mascararTelefone, mascararCep }           from "@/lib/mascaras";
import { notificar }                                            from "@/lib/notificar";
import { api }                                                  from "@/lib/api";


// --- ESTADOS BRASILEIROS ---
const ESTADOS_BRASIL = [
    ["AC", "Acre"], ["AL", "Alagoas"], ["AP", "Amapá"], ["AM", "Amazonas"],
    ["BA", "Bahia"], ["CE", "Ceará"], ["DF", "Distrito Federal"], ["ES", "Espírito Santo"],
    ["GO", "Goiás"], ["MA", "Maranhão"], ["MT", "Mato Grosso"], ["MS", "Mato Grosso do Sul"],
    ["MG", "Minas Gerais"], ["PA", "Pará"], ["PB", "Paraíba"], ["PR", "Paraná"],
    ["PE", "Pernambuco"], ["PI", "Piauí"], ["RJ", "Rio de Janeiro"], ["RN", "Rio Grande do Norte"],
    ["RS", "Rio Grande do Sul"], ["RO", "Rondônia"], ["RR", "Roraima"], ["SC", "Santa Catarina"],
    ["SP", "São Paulo"], ["SE", "Sergipe"], ["TO", "Tocantins"]
];

const OPCOES_UF = ESTADOS_BRASIL.map(([sigla, nome]) => ({ value: sigla, label: `${sigla} — ${nome}` }));

// --- CURSOS AGRUPADOS POR ÁREA ---
const OPCOES_CURSO = [
    {
        group: "Tecnologia da Informação",
        options: [
            "Ciência da Computação",
            "Análise e Desenvolvimento de Sistemas",
            "Engenharia de Software",
            "Ciência de Dados"
        ].map((nome) => ({ value: nome, label: nome }))
    },
    {
        group: "Engenharias",
        options: [
            "Engenharia Mecatrônica",
            "Engenharia Civil",
            "Engenharia Mecânica",
            "Engenharia de Produção",
            "Engenharia Elétrica",
            "Engenharia Química",
            "Engenharia Florestal",
            "Ciências Aeronáuticas",
            "Arquitetura e Urbanismo"
        ].map((nome) => ({ value: nome, label: nome }))
    },
    {
        group: "Saúde",
        options: [
            "Medicina",
            "Odontologia",
            "Farmácia",
            "Fisioterapia",
            "Nutrição",
            "Biomedicina",
            "Ciências Biológicas",
            "Educação Física",
            "Fonoaudiologia",
            "Terapia Ocupacional",
            "Medicina Veterinária"
        ].map((nome) => ({ value: nome, label: nome }))
    },
    {
        group: "Negócios e Humanas",
        options: ["Administração", "Direito"].map((nome) => ({ value: nome, label: nome }))
    },
    {
        group: "Artes e Comunicação",
        options: [
            "Design",
            "Publicidade e Propaganda",
            "Artes Visuais",
            "Artes Cênicas",
            "Design de Moda",
            "Fotografia"
        ].map((nome) => ({ value: nome, label: nome }))
    }
];

// --- TURNOS DO CURSO ---
const OPCOES_TURNO = [
    { 
        value: "MANHA", 
        label: "Manhã" 
    },
    { 
        value: "TARDE", 
        label: "Tarde" 
    },
    { 
        value: "NOITE", 
        label: "Noite" 
    }
];

// --- CAMPOS DE TEXTO NÃO CONTROLADOS, PREENCHIDOS PELO REF QUANDO O FORMULÁRIO ESTÁ EM MODO DE EDIÇÃO ---
const CAMPOS_TEXTO_EDICAO = [
    "nome", "emailPessoal", "nomePai", "nomeMae", "municipioNascimento", "cidade", "nacionalidade",
    "documentoNumero", "documentoOrgaoEmissor", "numeroTituloEleitor", "numeroZonaEleitoral",
    "numeroCertificadoReservista", "orgaoEmissorCertificadoReservista", "enderecoLogradouro", "enderecoNumero",
    "enderecoComplemento", "enderecoBairro", "instituicaoOrigem", "nomeInstituicaoConclusao", "anoConclusaoEnsinoMedio"
];

// --- OPÇÕES DE SEXO ---
const OPCOES_SEXO = [
    { 
        value: "FEMININO", 
        label: "Feminino" 
    },
    { 
        value: "MASCULINO", 
        label: "Masculino" 
    },
    { 
        value: "OUTRO", 
        label: "Outro" 
    },
    { 
        value: "PREFIRO_NAO_INFORMAR", 
        label: "Prefiro não informar" 
    }
];

// --- OPÇÕES DE ESTADO CÍVIL ---
const OPCOES_ESTADO_CIVIL = [
    { 
        value: "SOLTEIRO", 
        label: "Solteiro(a)" 
    },
    { 
        value: "CASADO", 
        label: "Casado(a)" 
    },
    { 
        value: "DIVORCIADO", 
        label: "Divorciado(a)" 
    },
    { 
        value: "VIUVO", 
        label: "Viúvo(a)" 
    },
    { 
        value: "UNIAO_ESTAVEL", 
        label: "União estável" 
    }
];

// -- OPÇÕES DE TIPO DE ENDEREÇO ---
const OPCOES_TIPO_ENDERECO = [
    { 
        value: "RESIDENCIAL", 
        label: "Residencial" 
    },
    { 
        value: "PROFISSIONAL", 
        label: "Profissional" 
    }
];

// -- OPÇÕES DE TIPO SANGUÍNEO ---
const OPCOES_TIPO_SANGUINEO = [
    { 
        value: "A_POSITIVO", 
        label: "A+" 
    },
    { 
        value: "A_NEGATIVO", 
        label: "A-" 
    },
    { 
        value: "B_POSITIVO", 
        label: "B+" 
    },
    { 
        value: "B_NEGATIVO", 
        label: "B-" 
    },
    { 
        value: "AB_POSITIVO", 
        label: "AB+" 
    },
    { 
        value: "AB_NEGATIVO", 
        label: "AB-" 
    },
    { 
        value: "O_POSITIVO", 
        label: "O+" 
    },
    { 
        value: "O_NEGATIVO", 
        label: "O-" 
    }
];

// -- OPÇÕES DE TIPO DE ESCOLA DE ENSINO MÉDIO ---
const OPCOES_TIPO_ESCOLA = [
    { 
        value: "PUBLICA", 
        label: "Pública" 
    },
    { 
        value: "PARTICULAR", 
        label: "Particular" 
    }
];

// -- OPÇÕES DE MESES ---
const OPCOES_MES = [
    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
].map((nome, indice) => ({ value: String(indice + 1), label: nome }));

// -- OPÇÕES DE RAÇA/ETNIA ---
const OPCOES_RACA_ETNIA = [
    { 
        value: "BRANCA", 
        label: "Branca" 
    },
    { 
        value: "PRETA", 
        label: "Preta" 
    },
    { 
        value: "PARDA", 
        label: "Parda" 
    },
    { 
        value: "AMARELA", 
        label: "Amarela" 
    },
    { 
        value: "INDIGENA", 
        label: "Indígena" 
    },
    { 
        value: "NAO_DECLARADA", 
        label: "Prefiro não declarar" 
    }
];

// --- PASSOS DO FORMULÁRIO DE CADASTRO ---
const PASSOS = [
    { 
        nome: "Dados pessoais", 
        icone: (
            <>
                <circle cx="12" cy="8" r="4"></circle>
                <path d="M4 21v-1a8 8 0 0 1 16 0v1"></path>
            </>
        ) 
    },
    { 
        nome: "Origem", 
        icone: (
            <>
                <path d="M12 22s7-7.58 7-12A7 7 0 0 0 5 10c0 4.42 7 12 7 12Z"></path>
                <circle cx="12" cy="10" r="2.5"></circle>
            </>
        ) 
    },
    { 
        nome: "Identificação", 
        icone: (
            <>
                <rect x="2" y="5" width="20" height="14" rx="2"></rect>
                <circle cx="8.5" cy="12" r="2"></circle>
                <path d="M5.5 16.5c.5-1.6 1.6-2.4 3-2.4s2.5.8 3 2.4"></path>
                <path d="M14 10h5M14 14h3"></path>
            </>
        ) 
    },
    { 
        nome: "Endereço", 
        icone: (
            <>
                <path d="M3 10.5 12 3l9 7.5"></path>
                <path d="M5 9.5V21h14V9.5"></path>
            </>
        ) 
    },
    { 
        nome: "Informações gerais", 
        icone: (
            <path d="M22 12h-4l-3 8-4-16-3 8H2"></path>
        ) 
    },
    { 
        nome: "Censo", 
        icone: (
            <>
                <path d="M22 10 12 5 2 10l10 5 10-5Z"></path>
                <path d="M6 12v5c0 1.5 3 3 6 3s6-1.5 6-3v-5"></path>
            </>
        ) 
    },
    { 
        nome: "E-mail institucional", 
        icone: (
            <>
                <rect x="2" y="4" width="20" height="16" rx="2"></rect>
                <path d="m2 7 10 6 10-6"></path>
            </>
        ) 
    },
    { 
        nome: "Consentimento", 
        icone: (
            <>
                <path d="M12 2 4 5v6c0 5 3.5 8.5 8 11 4.5-2.5 8-6 8-11V5Z"></path>
                <path d="m9 12 2 2 sift=4-4"></path>
            </>
        ) 
    }
];

// --- CAMPOS CONTROLADOS PADRÃO, USADOS PARA INICIALIZAR O STATE DE CAMPOS ---
const CAMPOS_CONTROLADOS_PADRAO = {
    cpf: "",
    telefone: "",
    telefoneEmergencia: "",
    enderecoCep: "",
    curso: "",
    sexo: "",
    estadoCivil: "",
    estado: "",
    ufExpedicaoIdentidade: "",
    ufZonaEleitoral: "",
    ufReservista: "",
    tipoEndereco: "",
    enderecoUf: "",
    tipoSanguineo: "",
    tipoEscolaEnsinoMedio: "",
    mesConclusaoEnsinoMedio: "",
    racaEtnia: "",
    turno: "",
    publicoAlvoEducacaoEspecial: false,
    canhoto: false,
    necessitaAcompanhamentoInstitucional: false,
    termoConsentimento: false
};

// --- RECUSA DATAS NO FUTURO E IDADES IMPLAUSÍVEIS ---
function idadeMinimaValida(valor) {
    if (!valor) {
        return false;
    }

    const nascimento = new Date(valor);
    if (Number.isNaN(nascimento.getTime())) {
        return false;
    }

    const hoje = new Date();
    const anos = (hoje - nascimento) / (1000 * 60 * 60 * 24 * 365.25);
    return anos >= 14 && anos < 120;
}

// --- CONVERTE O PERFIL DEVOLVIDO POR GET /alunos/me NOS CAMPOS CONTROLADOS DO FORMULÁRIO ---
function camposDoPerfil(dados) {
    const texto = (valor) => valor ?? "";
    return {
        cpf: mascararCpf(texto(dados.cpf)),
        telefone: mascararTelefone(texto(dados.telefone)),
        telefoneEmergencia: mascararTelefone(texto(dados.telefoneEmergencia)),
        enderecoCep: mascararCep(texto(dados.enderecoCep)),
        curso: texto(dados.curso),
        turno: texto(dados.turno),
        sexo: texto(dados.sexo),
        estadoCivil: texto(dados.estadoCivil),
        estado: texto(dados.estado),
        ufExpedicaoIdentidade: texto(dados.ufExpedicaoIdentidade),
        ufZonaEleitoral: texto(dados.ufZonaEleitoral),
        ufReservista: texto(dados.ufReservista),
        tipoEndereco: texto(dados.tipoEndereco),
        enderecoUf: texto(dados.enderecoUf),
        tipoSanguineo: texto(dados.tipoSanguineo),
        tipoEscolaEnsinoMedio: texto(dados.tipoEscolaEnsinoMedio),
        mesConclusaoEnsinoMedio: dados.mesConclusaoEnsinoMedio ? String(dados.mesConclusaoEnsinoMedio) : "",
        racaEtnia: texto(dados.racaEtnia),
        publicoAlvoEducacaoEspecial: !!dados.publicoAlvoEducacaoEspecial,
        canhoto: !!dados.canhoto,
        necessitaAcompanhamentoInstitucional: !!dados.necessitaAcompanhamentoInstitucional
    };
}

// --- GERA ATÉ 3 SUGESTÕES DE E-MAIL INSTITUCIONAL A PARTIR DO NOME COMPLETO ---
function gerarSugestoesEmail(nomeCompleto) {
    const partes = (nomeCompleto || "")
        .normalize("NFD")
        .replace(/[̀-ͯ]/g, "")
        .toLowerCase()
        .replace(/[^a-z\s]/g, " ")
        .split(/\s+/)
        .filter((parte) => parte && !["de", "da", "do", "das", "dos", "e"].includes(parte));

    if (partes.length === 0) {
        return [];
    }

    if (partes.length === 1) {
        return [`${partes[0]}@uniaura.com`];
    }

    const primeiro = partes[0];
    const ultimo = partes[partes.length - 1];
    const meio = partes.length > 2 ? partes[1] : null;

    const candidatas = [
        `${primeiro}.${ultimo}`,
        `${primeiro[0]}${ultimo}`,
        meio ? `${primeiro}.${meio[0]}.${ultimo}` : `${primeiro}${ultimo[0]}`
    ];

    return [...new Set(candidatas)].map((usuario) => `${usuario}@uniaura.com`);
}

// --- TRADUZ O ESTADO DE VALIDAÇÃO DE UM CAMPO NATIVO EM UMA MENSAGEM ---
function mensagemDeErro(campo) {
    if (campo.validity.valueMissing) {
        return campo.type === "checkbox" ? "Você precisa aceitar para continuar." : "Campo obrigatório.";
    }

    if (campo.validity.typeMismatch) {
        return "Informe um valor válido.";
    }

    if (campo.validity.tooShort) {
        return `Use pelo menos ${campo.minLength} caracteres.`;
    }
    return "Valor inválido.";
}

// --- PÁGINA DE CADASTRO DO PORTAL DO ALUNO ---
function CadastroConteudo() {
    const raizRef = useEfeitosDePagina();
    const router = useRouter();

    const editando = useSearchParams().get("editar") === "1";
    const totalEtapas = editando ? 6 : 8;
    const passos = editando ? PASSOS.slice(0, 6) : PASSOS;
    const Moldura = editando ? MolduraAluno : SiteChrome;
    const formularioRef = useRef(null);
    const [perfil, setPerfil] = useState(null);
    const [formularioMontado, setFormularioMontado] = useState(false);

    // --- GUARDA O <form> NO REF E AVISA QUANDO ELE FOI MONTADO ---
    const definirFormulario = useCallback((no) => {
        formularioRef.current = no;
        setFormularioMontado(!!no);
    }, []);

    const [etapaAtual,                  setEtapaAtual]                  = useState(1);
    const [campos,                      setCampos]                      = useState(CAMPOS_CONTROLADOS_PADRAO);
    const [erros,                       setErros]                       = useState({});
    const [chaveFormulario,             setChaveFormulario]             = useState(0);
    const [sugestoesEmail,              setSugestoesEmail]              = useState([]);
    const [emailEscolhido,              setEmailEscolhido]              = useState("");
    const [mostrarInfoAcompanhamento,   setMostrarInfoAcompanhamento]   = useState(false);
    const [mensagem,                    setMensagem]                    = useState("");
    const [erro,                        setErro]                        = useState("");
    const [enviando,                    setEnviando]                    = useState(false);

    // -- SCROLLA PARA A ETAPA ATUAL, QUANDO O USUÁRIO AVANÇA OU VOLTA ---
    useEffect(() => {
        formularioRef.current
            ?.querySelector(`.site-wizard-etapa[data-etapa="${etapaAtual}"]`)
            ?.scrollIntoView({ behavior: "smooth", block: "start" });
    }, [etapaAtual]);

    // --- NA EDIÇÃO, BUSCA OS DADOS DO ALUNO E REMONTA O FORMULÁRIO COM OS VALORES CONTROLADOS ---
    useEffect(() => {
        if (!editando) {
            return;
        }
        api("/alunos/me")
            .then((dados) => {
                setPerfil(dados);
                setCampos((atual) => ({ ...atual, ...camposDoPerfil(dados) }));
                setChaveFormulario((atual) => atual + 1);
            })
            .catch((excecao) => setErro(excecao.message));
    }, [editando]);

    // --- NA EDIÇÃO, PREENCHE OS CAMPOS DE TEXTO NÃO CONTROLADOS QUANDO O FORMULÁRIO JÁ ESTÁ NA TELA ---
    useEffect(() => {
        const formulario = formularioRef.current;
        if (!editando || !perfil || !formularioMontado || !formulario) {
            return;
        }

        CAMPOS_TEXTO_EDICAO.forEach((nome) => {
            if (formulario[nome]) {
                formulario[nome].value = perfil[nome] ?? "";
            }
        });
    }, [editando, perfil, formularioMontado, chaveFormulario]);

    // -- ATUALIZA O STATE DE CAMPOS CONTROLADOS, USADO PARA VALIDAR E MONTAR O PAYLOAD ---
    const atualizarCampo = (nome, valor) => {
        setCampos((atual) => ({ ...atual, [nome]: valor }));
        limparErroCampo(nome);
    };

    // --- REMOVE A MENSAGEM DE ERRO DE UM CAMPO ASSIM QUE O USUÁRIO MEXE NELE ---
    const limparErroCampo = (nome) => {
        setErros((atual) => {
            if (!atual[nome]) {
                return atual;
            }
            const { [nome]: _removido, ...resto } = atual;
            return resto;
        });
    };

    // --- MENSAGEM DE ERRO EM VERMELHO ABAIXO DO CAMPO ---
    const erroDe = (nome) => erros[nome] && <small className="ua-erro-campo" role="alert">{erros[nome]}</small>;
    const classeErro = (nome) => (erros[nome] ? "p-invalid" : "");

    // --- AO COMPLETAR O CEP, BUSCA O ENDEREÇO NO ViaCEP E PREENCHE RUA, BAIRRO, CIDADE E UF ---
    const aoDigitarCep = async (valor) => {
        const cepMascarado = mascararCep(valor);
        atualizarCampo("enderecoCep", cepMascarado);

        const digitos = cepMascarado.replace(/\D/g, "");
        if (digitos.length !== 8) {
            return;
        }

        try {
            const resposta = await fetch(`https://viacep.com.br/ws/${digitos}/json/`);
            const dados = await resposta.json();
            if (dados.erro) {
                notificar("CEP não encontrado. Preencha o endereço manualmente.", "warning");
                return;
            }

            const formulario = formularioRef.current;
            if (formulario) {
                formulario.enderecoLogradouro.value = dados.logradouro || "";
                formulario.enderecoBairro.value = dados.bairro || "";
                formulario.cidade.value = dados.localidade || formulario.cidade.value;
            }
            atualizarCampo("enderecoUf", dados.uf || "");
        } catch {
            notificar("Não foi possível consultar o CEP agora. Preencha o endereço manualmente.", "warning");
        }
    };

    // --- VALIDA SÓ OS CAMPOS VISÍVEIS NA ETAPA ATUAL, SEM TRAVAR NOS DEMAIS ---
    const etapaValida = (numero) => {
        const formulario = formularioRef.current;
        const etapa = formulario?.querySelector(`.site-wizard-etapa[data-etapa="${numero}"]`);
        if (!etapa) {
            return true;
        }

        const novosErros = {};
        const camposNativos = Array.from(etapa.querySelectorAll("input[name], textarea[name]"));
        for (const campo of camposNativos) {
            if (campo.type !== "hidden" && !campo.checkValidity()) {
                novosErros[campo.name] = mensagemDeErro(campo);
            }
        }

        if (numero === 1 && !editando) {
            if (!campos.curso) {
                novosErros.curso = "Escolha o curso pretendido.";
            }

            if (!campos.turno) {
                novosErros.turno = "Escolha o turno.";
            }
        }

        if (etapa.querySelector('[name="dataNascimento"]') && !formulario.dataNascimento.value) {
            novosErros.dataNascimento = "Informe sua data de nascimento.";
        }

        if (numero === 7 && !emailEscolhido) {
            novosErros.email = "Escolha um e-mail institucional para continuar.";
        }

        if (numero === 4 && !editando && !novosErros.senha && !novosErros.confirmarSenha
                && formulario.senha.value !== formulario.confirmarSenha.value) {
            novosErros.confirmarSenha = "As senhas informadas não conferem.";
        }

        setErros(novosErros);
        const nomes = Object.keys(novosErros);
        if (nomes.length > 0) {
            setErro("Revise os campos destacados antes de continuar.");
            formulario.querySelector(`[name="${nomes[0]}"]`)?.focus?.();
            return false;
        }

        return true;
    };

    const exibirEtapa = (numero) => setEtapaAtual(numero);

    // --- AVANÇA PARA A PRÓXIMA ETAPA, SE A ATUAL ESTIVER VÁLIDA ---
    const aoAvancar = () => {
        setErro("");
        if (etapaValida(etapaAtual) && etapaAtual < totalEtapas) {
            if (etapaAtual === 1) {
                const novas = gerarSugestoesEmail(formularioRef.current.nome.value);
                setSugestoesEmail(novas);
                setEmailEscolhido((atual) => (novas.includes(atual) ? atual : ""));
            }
            exibirEtapa(etapaAtual + 1);
        }
    };

    // --- GUARDA O E-MAIL INSTITUCIONAL ESCOLHIDO ---
    const escolherEmail = (sugestao) => {
        setEmailEscolhido(sugestao);
        limparErroCampo("email");
    };

    // --- VOLTA PARA A ETAPA ANTERIOR, SE NÃO ESTIVER NA PRIMEIRA ---
    const aoVoltar = () => {
        if (etapaAtual > 1) {
            exibirEtapa(etapaAtual - 1);
        }
    };

    // --- PERMITE PULAR DIRETO PARA UMA ETAPA JÁ CONCLUÍDA, CLICANDO NA TRILHA ---
    const aoClicarPasso = (destino) => {
        if (destino < etapaAtual) {
            exibirEtapa(destino);
        }
    };

    // --- MONTA O PAYLOAD ENVIADO AO ENDPOINT PÚBLICO DE PRÉ-MATRÍCULA ---
    const montarPayload = (formulario, cpf) => {
        const numero = (campo) => (formulario[campo].value === "" ? null : Number(formulario[campo].value));
        const texto = (campo) => formulario[campo].value.trim() || null;
        const digitos = (campo) => formulario[campo].value.replace(/\D/g, "") || null;

        return {
            // --- ACESSO ---
            nome: formulario.nome.value.trim(),
            email: emailEscolhido,
            emailPessoal: formulario.emailPessoal.value.trim(),
            senha: formulario.senha?.value,

            // --- ETAPA 1 ---
            curso: campos.curso,
            turno: campos.turno,
            nomePai: texto("nomePai"),
            nomeMae: texto("nomeMae"),
            sexo: campos.sexo || null,
            estadoCivil: campos.estadoCivil || null,

            // --- ETAPA 2 ---
            dataNascimento: formulario.dataNascimento.value,
            municipioNascimento: texto("municipioNascimento"),
            cidade: texto("cidade"),
            estado: campos.estado || null,
            nacionalidade: texto("nacionalidade"),

            // --- ETAPA 3 ---
            cpf,
            documentoNumero: texto("documentoNumero"),
            documentoOrgaoEmissor: texto("documentoOrgaoEmissor"),
            ufExpedicaoIdentidade: campos.ufExpedicaoIdentidade || null,
            dataExpedicaoIdentidade: formulario.dataExpedicaoIdentidade.value || null,
            numeroTituloEleitor: texto("numeroTituloEleitor"),
            numeroZonaEleitoral: texto("numeroZonaEleitoral"),
            ufZonaEleitoral: campos.ufZonaEleitoral || null,
            numeroCertificadoReservista: texto("numeroCertificadoReservista"),
            orgaoEmissorCertificadoReservista: texto("orgaoEmissorCertificadoReservista"),
            ufReservista: campos.ufReservista || null,

            // --- ETAPA 4 ---
            tipoEndereco: campos.tipoEndereco || null,
            enderecoCep: digitos("enderecoCep"),
            enderecoLogradouro: texto("enderecoLogradouro"),
            enderecoNumero: texto("enderecoNumero"),
            enderecoComplemento: texto("enderecoComplemento"),
            enderecoBairro: texto("enderecoBairro"),
            enderecoUf: campos.enderecoUf || null,
            telefone: digitos("telefone"),
            telefoneEmergencia: digitos("telefoneEmergencia"),

            // --- ETAPA 5 ---
            tipoSanguineo: campos.tipoSanguineo || null,
            publicoAlvoEducacaoEspecial: campos.publicoAlvoEducacaoEspecial,
            canhoto: campos.canhoto,
            necessitaAcompanhamentoInstitucional: campos.necessitaAcompanhamentoInstitucional,

            // --- ETAPA 6 ---
            instituicaoOrigem: texto("instituicaoOrigem"),
            tipoEscolaEnsinoMedio: campos.tipoEscolaEnsinoMedio || null,
            nomeInstituicaoConclusao: texto("nomeInstituicaoConclusao"),
            mesConclusaoEnsinoMedio: numero("mesConclusaoEnsinoMedio"),
            anoConclusaoEnsinoMedio: numero("anoConclusaoEnsinoMedio"),
            racaEtnia: campos.racaEtnia || null,

            // --- ETAPA 7 ---
            termoConsentimento: campos.termoConsentimento
        };
    };

    // -- ENVIA O FORMULÁRIO PARA O ENDPOINT PÚBLICO DE PRÉ-MATRÍCULA, SE TUDO ESTIVER VÁLIDO ---
    const aoSubmeter = async (evento) => {
        evento.preventDefault();

        setMensagem("");
        setErro("");

        const formulario = formularioRef.current;
        if (!etapaValida(totalEtapas)) {
            return;
        }

        // --- ENVIA SÓ OS DADOS PESSOAIS ---
        if (editando) {
            const { senha, email, cpf, curso, turno, termoConsentimento, ...dadosPessoais } =
                montarPayload(formulario, formulario.cpf.value.replace(/\D/g, ""));

            setEnviando(true);
            try {
                await api("/alunos/me", { metodo: "PUT", corpo: { ...dadosPessoais, emailPessoal: formulario.emailPessoal.value.trim() } });
                notificar("Dados pessoais atualizados com sucesso!", "success");
                router.push("/portal-do-aluno/dashboard");
            } catch (erroRequisicao) {
                setErro(erroRequisicao.message);
                notificar(erroRequisicao.message, "error");
            } finally {
                setEnviando(false);
            }
            return;
        }

        const cpf = formulario.cpf.value.replace(/\D/g, "");
        if (cpf.length !== 11) {
            setErro("Informe um CPF com 11 dígitos.");
            formulario.cpf.focus();
            return;
        }

        if (!idadeMinimaValida(formulario.dataNascimento.value)) {
            setErro("Informe uma data de nascimento válida.");
            formulario.dataNascimento.focus();
            return;
        }

        const payload = montarPayload(formulario, cpf);

        setEnviando(true);
        try {
            const resposta = await api("/pre-matricula", { metodo: "POST", corpo: payload });

            const texto = `Matrícula concluída! Seu número de matrícula (RA) é ${resposta.matriculaRA}. ` +
                `Entre no Portal do Aluno com o e-mail institucional ${resposta.email} e a senha cadastrada.`;
            setMensagem(texto);
            notificar("Matrícula realizada com sucesso!", "success");

            // --- LIMPA TODO O FORMULÁRIO ---
            setCampos(CAMPOS_CONTROLADOS_PADRAO);
            setErros({});
            setSugestoesEmail([]);
            setEmailEscolhido("");
            setMostrarInfoAcompanhamento(false);
            setChaveFormulario((atual) => atual + 1);
            setEtapaAtual(1);

            setTimeout(() => router.push("/portal-do-aluno/login"), 3500);
        } catch (erroRequisicao) {
            setErro(erroRequisicao.message);
            notificar(erroRequisicao.message, "error");
        } finally {
            setEnviando(false);
        }
    };

    return (
        <Moldura>
            <div className="grad-page" ref={raizRef}>
                <main>
                    {!editando && <section className="grad-hero">
                        <span className="grad-hero-brilho grad-hero-brilho-a" aria-hidden="true"></span>
                        <span className="grad-hero-brilho grad-hero-brilho-b" aria-hidden="true"></span>

                        <div className="grad-container grad-hero-grid">
                            <div className="grad-hero-texto">
                                <span className="grad-eyebrow" data-entrada style={{ "--atraso": "60ms" }}>
                                    <svg viewBox="0 0 24 24" width="15" height="15" fill="none" stroke="currentColor"
                                            strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                        <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
                                        <circle cx="9" cy="7" r="4"></circle>
                                        <path d="M22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75"></path>
                                    </svg>
                                    Portal do Aluno
                                </span>

                                <h1 data-entrada style={{ "--atraso": "160ms" }}>Crie sua <span>matrícula</span></h1>

                                <p data-entrada style={{ "--atraso": "260ms" }}>
                                    Preencha o formulário em 8 etapas para se matricular na UniAura. Ao concluir, você
                                    recebe seu número de matrícula (RA) e já pode acessar o Portal do Aluno com o
                                    e-mail e a senha cadastrados aqui.
                                </p>
                            </div>
                        </div>
                    </section>}

                    <section className="grad-section" id="grad-cadastro">
                        <div className="grad-container">
                            <div className="grad-section-title" data-revelar>
                                <span className="grad-eyebrow">{editando ? "Meus dados" : "Matrícula"}</span>
                                <h2>{editando ? "Alterar dados pessoais" : "Preencha seus dados"}</h2>
                                <p>
                                    {editando
                                        ? "Atualize suas informações. Curso, turno, RA, CPF e e-mail institucional não podem ser alterados aqui."
                                        : "Os campos marcados com asterisco são obrigatórios."}
                                </p>
                            </div>

                            <div className="site-wizard" data-revelar>
                                <ol className="site-wizard-passos" aria-label="Etapas do cadastro">
                                    {passos.map((passo, indice) => {
                                        const numero = indice + 1;
                                        return (
                                            <li key={passo.nome} className={`site-wizard-passo${numero === etapaAtual ? " ativo" : ""}${numero < etapaAtual ? " concluida" : ""}`}
                                                    onClick={() => aoClicarPasso(numero)}>
                                                <span className="site-wizard-numero">{numero}</span>
                                                <span className="site-wizard-icone" aria-hidden="true">
                                                    <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor"
                                                            strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                                                        {passo.icone}
                                                    </svg>
                                                </span>
                                                <span className="site-wizard-nome">{passo.nome}</span>
                                            </li>
                                        );
                                    })}
                                </ol>

                                <form key={chaveFormulario} id="formCadastroAluno" className="site-form" noValidate ref={definirFormulario} onSubmit={aoSubmeter}>

                                    {/* --- ETAPA 1: DADOS PESSOAIS --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="1" hidden={etapaAtual !== 1}>
                                        <legend>Dados pessoais</legend>
                                        <div className="site-form-grid">
                                            <div className="field field-full">
                                                <label htmlFor="cadNome">
                                                    Nome completo <span className="ua-obrigatorio" aria-hidden="true">*</span>
                                                </label>
                                                <InputText 
                                                    id="cadNome" 
                                                    name="nome" 
                                                    required 
                                                    minLength={5} 
                                                    autoComplete="name" 
                                                    className={classeErro("nome")} 
                                                    onInput={() => limparErroCampo("nome")} 
                                                />
                                                {erroDe("nome")}
                                            </div>

                                            <div className="field field-full">
                                                <label htmlFor="cadCurso">Curso pretendido <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                                <Dropdown
                                                    id="cadCurso"
                                                    name="curso"
                                                    options={OPCOES_CURSO}
                                                    optionGroupLabel="group"
                                                    optionGroupChildren="options"
                                                    value={campos.curso}
                                                    onChange={(e) => atualizarCampo("curso", e.value)}
                                                    placeholder="Selecione o curso"
                                                    disabled={editando}
                                                    className={classeErro("curso")}
                                                />
                                                {erroDe("curso")}
                                            </div>

                                            <div className="field field-full">
                                                <label htmlFor="cadTurno">Turno <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                                <Dropdown
                                                    inputId="cadTurno"
                                                    name="turno"
                                                    options={OPCOES_TURNO}
                                                    value={campos.turno}
                                                    onChange={(e) => atualizarCampo("turno", e.value)}
                                                    placeholder="Selecione o turno"
                                                    disabled={editando}
                                                    className={classeErro("turno")}
                                                />
                                                {erroDe("turno")}
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNomePai">Nome do pai</label>
                                                <InputText 
                                                    id="cadNomePai" 
                                                    name="nomePai" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNomeMae">Nome da mãe</label>
                                                <InputText 
                                                    id="cadNomeMae" 
                                                    name="nomeMae" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadSexo">Sexo</label>
                                                <Dropdown
                                                    id="cadSexo"
                                                    name="sexo"
                                                    options={OPCOES_SEXO}
                                                    value={campos.sexo}
                                                    onChange={(e) => atualizarCampo("sexo", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadEstadoCivil">Estado civil</label>
                                                <Dropdown
                                                    id="cadEstadoCivil"
                                                    name="estadoCivil"
                                                    options={OPCOES_ESTADO_CIVIL}
                                                    value={campos.estadoCivil}
                                                    onChange={(e) => atualizarCampo("estadoCivil", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>
                                        </div>
                                    </fieldset>

                                    {/* --- ETAPA 2: ORIGEM --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="2" hidden={etapaAtual !== 2}>
                                        <legend>Origem</legend>
                                        <div className="site-form-grid">
                                            <div className="field">
                                                <label htmlFor="cadNascimento">Data de nascimento <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                                <CampoData 
                                                    id="cadNascimento" 
                                                    name="dataNascimento" 
                                                    yearRange="1920:2010" 
                                                    valorInicial={perfil?.dataNascimento} 
                                                    required 
                                                    invalido={!!erros.dataNascimento} 
                                                    onChange={() => limparErroCampo("dataNascimento")} 
                                                />
                                                {erroDe("dataNascimento")}
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadMunicipioNascimento">Município de nascimento</label>
                                                <InputText 
                                                    id="cadMunicipioNascimento" 
                                                    name="municipioNascimento" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadCidade">Cidade</label>
                                                <InputText 
                                                    id="cadCidade" 
                                                    name="cidade" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadEstado">Estado</label>
                                                <Dropdown
                                                    id="cadEstado"
                                                    name="estado"
                                                    options={OPCOES_UF}
                                                    value={campos.estado}
                                                    onChange={(e) => atualizarCampo("estado", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNacionalidade">Nacionalidade</label>
                                                <InputText 
                                                    id="cadNacionalidade" 
                                                    name="nacionalidade" 
                                                    defaultValue="Brasileira" 
                                                />
                                            </div>
                                        </div>
                                    </fieldset>

                                    {/* --- ETAPA 3: IDENTIFICAÇÃO --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="3" hidden={etapaAtual !== 3}>
                                        <legend>Identificação</legend>
                                        <div className="site-form-grid">
                                            <div className="field">
                                                <label htmlFor="cadCpf">CPF <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                                <InputText
                                                    id="cadCpf"
                                                    name="cpf"
                                                    required
                                                    readOnly={editando}
                                                    inputMode="numeric"
                                                    maxLength={14}
                                                    placeholder="000.000.000-00"
                                                    value={campos.cpf}
                                                    onChange={(e) => atualizarCampo("cpf", mascararCpf(e.target.value))}
                                                    className={classeErro("cpf")}
                                                />
                                                {erroDe("cpf")}
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNumeroIdentidade">Número da identidade (RG)</label>
                                                <InputText 
                                                    id="cadNumeroIdentidade" 
                                                    name="documentoNumero" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadOrgaoEmissor">Órgão emissor da identidade</label>
                                                <InputText 
                                                    id="cadOrgaoEmissor" 
                                                    name="documentoOrgaoEmissor" 
                                                    placeholder="SSP" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadUfExpedicao">UF de expedição da identidade</label>
                                                <Dropdown
                                                    id="cadUfExpedicao"
                                                    name="ufExpedicaoIdentidade"
                                                    options={OPCOES_UF}
                                                    value={campos.ufExpedicaoIdentidade}
                                                    onChange={(e) => atualizarCampo("ufExpedicaoIdentidade", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadDataExpedicao">Data de expedição da identidade</label>
                                                <CampoData 
                                                    id="cadDataExpedicao" 
                                                    name="dataExpedicaoIdentidade" 
                                                    valorInicial={perfil?.dataExpedicaoIdentidade} 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadTituloEleitor">Número do título de eleitor</label>
                                                <InputText 
                                                    id="cadTituloEleitor" 
                                                    name="numeroTituloEleitor" 
                                                    inputMode="numeric" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadZonaEleitoral">Número da zona eleitoral</label>
                                                <InputText 
                                                    id="cadZonaEleitoral" 
                                                    name="numeroZonaEleitoral" 
                                                    inputMode="numeric" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadUfZonaEleitoral">UF da zona eleitoral</label>
                                                <Dropdown
                                                    id="cadUfZonaEleitoral"
                                                    name="ufZonaEleitoral"
                                                    options={OPCOES_UF}
                                                    value={campos.ufZonaEleitoral}
                                                    onChange={(e) => atualizarCampo("ufZonaEleitoral", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadCertificadoReservista">Número do certificado de reservista (opcional)</label>
                                                <InputText 
                                                    id="cadCertificadoReservista" 
                                                    name="numeroCertificadoReservista" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadOrgaoReservista">Órgão emissor do certificado de reservista (opcional)</label>
                                                <InputText 
                                                    id="cadOrgaoReservista" 
                                                    name="orgaoEmissorCertificadoReservista" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadUfReservista">UF do certificado de reservista (opcional)</label>
                                                <Dropdown
                                                    id="cadUfReservista"
                                                    name="ufReservista"
                                                    options={OPCOES_UF}
                                                    value={campos.ufReservista}
                                                    onChange={(e) => atualizarCampo("ufReservista", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>
                                        </div>
                                    </fieldset>

                                    {/* --- ETAPA 4: ENDEREÇO --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="4" hidden={etapaAtual !== 4}>
                                        <legend>Endereço</legend>
                                        <div className="site-form-grid">
                                            <div className="field">
                                                <label htmlFor="cadTipoEndereco">Tipo de endereço</label>
                                                <Dropdown
                                                    id="cadTipoEndereco"
                                                    name="tipoEndereco"
                                                    options={OPCOES_TIPO_ENDERECO}
                                                    value={campos.tipoEndereco}
                                                    onChange={(e) => atualizarCampo("tipoEndereco", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadCep">CEP</label>
                                                <InputText
                                                    id="cadCep"
                                                    name="enderecoCep"
                                                    inputMode="numeric"
                                                    maxLength={9}
                                                    placeholder="00000-000"
                                                    value={campos.enderecoCep}
                                                    onChange={(e) => aoDigitarCep(e.target.value)}
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadRua">Rua</label>
                                                <InputText 
                                                    id="cadRua" 
                                                    name="enderecoLogradouro" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNumeroEndereco">Número</label>
                                                <InputText 
                                                    id="cadNumeroEndereco" 
                                                    name="enderecoNumero" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadComplemento">Complemento</label>
                                                <InputText 
                                                    id="cadComplemento" 
                                                    name="enderecoComplemento" 
                                                    placeholder="Apto, bloco..." 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadBairro">Bairro</label>
                                                <InputText 
                                                    id="cadBairro" 
                                                    name="enderecoBairro" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadUfEndereco">UF de endereço</label>
                                                <Dropdown
                                                    id="cadUfEndereco"
                                                    name="enderecoUf"
                                                    options={OPCOES_UF}
                                                    value={campos.enderecoUf}
                                                    onChange={(e) => atualizarCampo("enderecoUf", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadEmailPessoal">
                                                    E-mail pessoal <span className="ua-obrigatorio" aria-hidden="true">*</span>
                                                </label>
                                                <InputText 
                                                    id="cadEmailPessoal" 
                                                    name="emailPessoal" 
                                                    type="email" 
                                                    required 
                                                    autoComplete="email" 
                                                    className={classeErro("emailPessoal")} 
                                                    onInput={() => limparErroCampo("emailPessoal")} 
                                                />
                                                {erroDe("emailPessoal")}
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadTelefone">Telefone <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                                <InputText
                                                    id="cadTelefone"
                                                    name="telefone"
                                                    type="tel"
                                                    required
                                                    maxLength={15}
                                                    placeholder="(00) 00000-0000"
                                                    autoComplete="tel"
                                                    value={campos.telefone}
                                                    onChange={(e) => atualizarCampo("telefone", mascararTelefone(e.target.value))}
                                                    className={classeErro("telefone")}
                                                />
                                                {erroDe("telefone")}
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadTelefoneEmergencia">Telefone de emergência</label>
                                                <InputText
                                                    id="cadTelefoneEmergencia"
                                                    name="telefoneEmergencia"
                                                    type="tel"
                                                    maxLength={15}
                                                    placeholder="(00) 00000-0000"
                                                    value={campos.telefoneEmergencia}
                                                    onChange={(e) => atualizarCampo("telefoneEmergencia", mascararTelefone(e.target.value))}
                                                />
                                            </div>

                                            {!editando && (
                                                <>
                                            <div className="field">
                                                <label htmlFor="cadSenha">Senha de acesso <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                                <CampoSenha 
                                                    id="cadSenha" 
                                                    name="senha" 
                                                    required 
                                                    minLength={6} 
                                                    autoComplete="new-password" 
                                                    className={classeErro("senha")} 
                                                    onInput={() => limparErroCampo("senha")} 
                                                />
                                                {erroDe("senha")}
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadConfirmarSenha">Confirmar senha <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                                <CampoSenha 
                                                    id="cadConfirmarSenha" 
                                                    name="confirmarSenha" 
                                                    required 
                                                    minLength={6} 
                                                    autoComplete="new-password" 
                                                    className={classeErro("confirmarSenha")} 
                                                    onInput={() => limparErroCampo("confirmarSenha")} 
                                                />
                                                {erroDe("confirmarSenha")}
                                            </div>
                                                </>
                                            )}
                                        </div>
                                    </fieldset>

                                    {/* --- ETAPA 5: INFORMAÇÕES GERAIS --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="5" hidden={etapaAtual !== 5}>
                                        <legend>Informações gerais</legend>
                                        <div className="site-form-grid">
                                            <div className="field">
                                                <label htmlFor="cadTipoSanguineo">Tipo sanguíneo</label>
                                                <Dropdown
                                                    id="cadTipoSanguineo"
                                                    name="tipoSanguineo"
                                                    options={OPCOES_TIPO_SANGUINEO}
                                                    value={campos.tipoSanguineo}
                                                    onChange={(e) => atualizarCampo("tipoSanguineo", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>
                                        </div>

                                        <label className="site-form-termos">
                                            <Checkbox 
                                                inputId="cadEducacaoEspecial" 
                                                name="publicoAlvoEducacaoEspecial" 
                                                checked={campos.publicoAlvoEducacaoEspecial} 
                                                onChange={(e) => atualizarCampo("publicoAlvoEducacaoEspecial", e.checked)} 
                                            />
                                            <span>Sou aluno público-alvo da Educação Especial.</span>
                                        </label>

                                        <label className="site-form-termos">
                                            <Checkbox 
                                                inputId="cadCanhoto" 
                                                name="canhoto" 
                                                checked={campos.canhoto} 
                                                onChange={(e) => atualizarCampo("canhoto", e.checked)} 
                                            />
                                            <span>Sou canhoto(a).</span>
                                        </label>

                                        <label className="site-form-termos">
                                            <Checkbox 
                                                inputId="cadAcompanhamento" 
                                                name="necessitaAcompanhamentoInstitucional" 
                                                checked={campos.necessitaAcompanhamentoInstitucional} 
                                                onChange={(e) => atualizarCampo("necessitaAcompanhamentoInstitucional", e.checked)} 
                                            />
                                            <span>
                                                Necessito de acompanhamento institucional.
                                                <button type="button" className="site-info-btn" id="btnInfoAcompanhamento" aria-label="O que é acompanhamento institucional?"
                                                        onClick={(e) => { e.preventDefault(); e.stopPropagation(); setMostrarInfoAcompanhamento(true); }}>
                                                    <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor"
                                                            strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                                        <circle cx="12" cy="12" r="10"></circle>
                                                        <line x1="12" y1="16" x2="12" y2="12"></line>
                                                        <line x1="12" y1="8" x2="12.01" y2="8"></line>
                                                    </svg>
                                                </button>
                                            </span>
                                        </label>
                                        <Dialog header="Acompanhamento institucional" visible={mostrarInfoAcompanhamento} onHide={() => setMostrarInfoAcompanhamento(false)} style={{ width: "min(28rem, 92vw)" }} dismissableMask draggable={false}>
                                            <p className="site-info-texto" id="infoAcompanhamento">
                                            O acompanhamento institucional é o suporte pedagógico e psicossocial oferecido pela
                                            UniAura a alunos que precisem de apoio extra durante o curso (dificuldades de
                                            aprendizagem, questões de saúde, adaptação ou outras necessidades). Marcar esta
                                            opção não afeta sua matrícula: a coordenação apenas entrará em contato para
                                            entender como pode ajudar.
                                        </p>
                                        </Dialog>
                                    </fieldset>

                                    {/* --- ETAPA 6: CENSO --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="6" hidden={etapaAtual !== 6}>
                                        <legend>Censo</legend>
                                        <div className="site-form-grid">
                                            <div className="field">
                                                <label htmlFor="cadInstituicaoOrigem">Instituição de origem</label>
                                                <InputText 
                                                    id="cadInstituicaoOrigem" 
                                                    name="instituicaoOrigem" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadTipoEscola">Tipo de escola do ensino médio</label>
                                                <Dropdown
                                                    id="cadTipoEscola"
                                                    name="tipoEscolaEnsinoMedio"
                                                    options={OPCOES_TIPO_ESCOLA}
                                                    value={campos.tipoEscolaEnsinoMedio}
                                                    onChange={(e) => atualizarCampo("tipoEscolaEnsinoMedio", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNomeInstituicao">Nome da instituição (conclusão do ensino médio)</label>
                                                <InputText 
                                                    id="cadNomeInstituicao" 
                                                    name="nomeInstituicaoConclusao" 
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadMesConclusao">Mês de conclusão do ensino médio</label>
                                                <Dropdown
                                                    id="cadMesConclusao"
                                                    name="mesConclusaoEnsinoMedio"
                                                    options={OPCOES_MES}
                                                    value={campos.mesConclusaoEnsinoMedio}
                                                    onChange={(e) => atualizarCampo("mesConclusaoEnsinoMedio", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadAnoConclusao">Ano de conclusão</label>
                                                <InputText
                                                    id="cadAnoConclusao"
                                                    name="anoConclusaoEnsinoMedio"
                                                    type="number"
                                                    inputMode="numeric"
                                                    min={1950}
                                                    max={2100}
                                                    placeholder="2024"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadRacaEtnia">Raça/Etnia</label>
                                                <Dropdown
                                                    id="cadRacaEtnia"
                                                    name="racaEtnia"
                                                    options={OPCOES_RACA_ETNIA}
                                                    value={campos.racaEtnia}
                                                    onChange={(e) => atualizarCampo("racaEtnia", e.value)}
                                                    placeholder="Selecione"
                                                />
                                            </div>
                                        </div>
                                    </fieldset>

                                    {!editando && (
                                <>
                                    {/* --- ETAPA 7: ESCOLHA DO E-MAIL INSTITUCIONAL --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="7" hidden={etapaAtual !== 7}>
                                        <legend>E-mail institucional</legend>

                                        <p className="muted">
                                            Escolha o e-mail <strong>@uniaura.com</strong> que será o seu acesso ao Portal do Aluno.
                                            As opções foram geradas a partir do seu nome.
                                        </p>

                                        {sugestoesEmail.length > 0 ? (
                                            <div className="ua-sugestoes-email" role="group" aria-label="Opções de e-mail institucional">
                                                <div className="ua-sugestoes-email__lista">
                                                    {sugestoesEmail.map((sugestao) => (
                                                        <button
                                                            key={sugestao}
                                                            type="button"
                                                            className="ua-sugestao-email"
                                                            aria-pressed={emailEscolhido === sugestao}
                                                            onClick={() => escolherEmail(sugestao)}
                                                        >
                                                            {sugestao}
                                                        </button>
                                                    ))}
                                                </div>
                                            </div>
                                        ) : (
                                            <p className="muted">Volte à primeira etapa e informe seu nome completo para ver as opções.</p>
                                        )}

                                        {erroDe("email")}
                                        {emailEscolhido && <small className="muted">Você entrará no portal com <strong>{emailEscolhido}</strong>.</small>}
                                    </fieldset>

                                    {/* --- ETAPA 8: TERMO DE CONSENTIMENTO --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="8" hidden={etapaAtual !== 8}>
                                        <legend>Termo de consentimento</legend>

                                        <div className="site-consentimento">
                                            <p>
                                                Ao concluir esta matrícula, a UniAura passa a tratar os dados pessoais informados
                                                neste formulário para as finalidades de gestão acadêmica, comunicação institucional
                                                e cumprimento de obrigações legais e regulatórias do ensino superior, nos termos da
                                                Lei Geral de Proteção de Dados (LGPD).
                                            </p>
                                            <p>
                                                Você pode solicitar a qualquer momento a atualização, correção ou exclusão dos
                                                seus dados junto à secretaria acadêmica, respeitadas as obrigações legais de
                                                guarda de registros escolares.
                                            </p>
                                        </div>

                                        <label className="site-form-termos">
                                            <Checkbox inputId="cadTermos" name="termoConsentimento" required className={classeErro("termoConsentimento")} checked={campos.termoConsentimento} onChange={(e) => atualizarCampo("termoConsentimento", e.checked)} />
                                            <span>Li e aceito o termo de consentimento para uso dos meus dados pessoais. <span className="ua-obrigatorio" aria-hidden="true">*</span></span>
                                        </label>
                                        {erroDe("termoConsentimento")}
                                    </fieldset>
                                </>
                            )}

                                    <div className="site-wizard-acoes">
                                        {etapaAtual !== 1
                                            ? <Button type="button" id="btnVoltarEtapa" label="Voltar" icon="pi pi-arrow-left" className="ua-botao-seta ua-botao-seta--voltar" outlined onClick={aoVoltar} />
                                            : <span />}

                                        <span className="site-wizard-contador">
                                            Etapa <span id="wizardEtapaAtual">{etapaAtual}</span> de {totalEtapas}
                                        </span>

                                        {etapaAtual !== totalEtapas && (
                                            <Button 
                                                type="button" 
                                                id="btnAvancarEtapa" 
                                                label="Próxima etapa" 
                                                icon="pi pi-arrow-right" 
                                                iconPos="right" 
                                                className="ua-botao-seta" 
                                                onClick={aoAvancar} 
                                            />
                                        )}

                                        {etapaAtual === totalEtapas && (
                                            <Button 
                                                type="submit" 
                                                id="btnConcluirCadastro" 
                                                label={enviando ? "Enviando..." : (editando ? "Salvar alterações" : "Concluir matrícula")} 
                                                loading={enviando} 
                                            />
                                        )}
                                    </div>

                                    {mensagem && <Message severity="success" text={mensagem} />}
                                    {erro && <Message severity="error" text={erro} />}
                                </form>
                            </div>
                        </div>
                    </section>
                </main>
            </div>
        </Moldura>
    );
}

// --- O useSearchParams EXIGE UM LIMITE DE SUSPENSE NO APP ROUTER ---
export default function CadastroPortalDoAlunoPage() {
    return (
        <Suspense fallback={null}>
            <CadastroConteudo />
        </Suspense>
    );
}
