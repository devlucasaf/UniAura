"use client";

import { useEffect, useRef, useState } from "react";
import { useRouter } from "next/navigation";
import SiteChrome from "@/components/site/SiteChrome";
import Dropdown from "@/components/ui/Dropdown";
import { useEfeitosDePagina } from "@/hooks/useEfeitosDePagina";
import { mascararCpf, mascararTelefone, mascararCep } from "@/lib/mascaras";
import { notificar } from "@/lib/notificar";
import { api } from "@/lib/api";

const TOTAL_ETAPAS = 7;

// --- ESTADOS BRASILEIROS, USADOS EM TODOS OS SELECTS DE UF DO FORMULÁRIO ---
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

// --- CURSOS AGRUPADOS POR ÁREA, EXATAMENTE COMO NO FORMULÁRIO ORIGINAL ---
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

const OPCOES_SEXO = [
    { value: "FEMININO", label: "Feminino" },
    { value: "MASCULINO", label: "Masculino" },
    { value: "OUTRO", label: "Outro" },
    { value: "PREFIRO_NAO_INFORMAR", label: "Prefiro não informar" }
];

const OPCOES_ESTADO_CIVIL = [
    { value: "SOLTEIRO", label: "Solteiro(a)" },
    { value: "CASADO", label: "Casado(a)" },
    { value: "DIVORCIADO", label: "Divorciado(a)" },
    { value: "VIUVO", label: "Viúvo(a)" },
    { value: "UNIAO_ESTAVEL", label: "União estável" }
];

const OPCOES_TIPO_ENDERECO = [
    { value: "RESIDENCIAL", label: "Residencial" },
    { value: "PROFISSIONAL", label: "Profissional" }
];

const OPCOES_TIPO_SANGUINEO = [
    { value: "A_POSITIVO", label: "A+" },
    { value: "A_NEGATIVO", label: "A-" },
    { value: "B_POSITIVO", label: "B+" },
    { value: "B_NEGATIVO", label: "B-" },
    { value: "AB_POSITIVO", label: "AB+" },
    { value: "AB_NEGATIVO", label: "AB-" },
    { value: "O_POSITIVO", label: "O+" },
    { value: "O_NEGATIVO", label: "O-" }
];

const OPCOES_TIPO_ESCOLA = [
    { value: "PUBLICA", label: "Pública" },
    { value: "PARTICULAR", label: "Particular" }
];

const OPCOES_MES = [
    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
].map((nome, indice) => ({ value: String(indice + 1), label: nome }));

const OPCOES_RACA_ETNIA = [
    { value: "BRANCA", label: "Branca" },
    { value: "PRETA", label: "Preta" },
    { value: "PARDA", label: "Parda" },
    { value: "AMARELA", label: "Amarela" },
    { value: "INDIGENA", label: "Indígena" },
    { value: "NAO_DECLARADA", label: "Prefiro não declarar" }
];

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
        icone: <path d="M22 12h-4l-3 8-4-16-3 8H2"></path>
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
        nome: "Consentimento",
        icone: (
            <>
                <path d="M12 2 4 5v6c0 5 3.5 8.5 8 11 4.5-2.5 8-6 8-11V5Z"></path>
                <path d="m9 12 2 2 4-4"></path>
            </>
        )
    }
];

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
    racaEtnia: ""
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

export default function CadastroPortalDoAlunoPage() {
    const raizRef = useEfeitosDePagina();
    const router = useRouter();
    const formularioRef = useRef(null);

    const [etapaAtual, setEtapaAtual] = useState(1);
    const [campos, setCampos] = useState(CAMPOS_CONTROLADOS_PADRAO);
    const [cursoInvalido, setCursoInvalido] = useState(false);
    const [mostrarInfoAcompanhamento, setMostrarInfoAcompanhamento] = useState(false);
    const [mensagem, setMensagem] = useState("");
    const [erro, setErro] = useState("");
    const [enviando, setEnviando] = useState(false);

    useEffect(() => {
        formularioRef.current
            ?.querySelector(`.site-wizard-etapa[data-etapa="${etapaAtual}"]`)
            ?.scrollIntoView({ behavior: "smooth", block: "start" });
    }, [etapaAtual]);

    const atualizarCampo = (nome, valor) => {
        setCampos((atual) => ({ ...atual, [nome]: valor }));
    };

    // --- VALIDA SÓ OS CAMPOS VISÍVEIS NA ETAPA ATUAL, SEM TRAVAR NOS DEMAIS ---
    const etapaValida = (numero) => {
        const formulario = formularioRef.current;
        const etapa = formulario?.querySelector(`.site-wizard-etapa[data-etapa="${numero}"]`);
        if (!etapa) {
            return true;
        }

        const camposNativos = Array.from(etapa.querySelectorAll("input, textarea"));
        for (const campo of camposNativos) {
            if (!campo.checkValidity()) {
                campo.reportValidity();
                return false;
            }
        }

        if (numero === 1 && !campos.curso) {
            setCursoInvalido(true);
            return false;
        }
        setCursoInvalido(false);

        if (numero === 4) {
            const senha = formulario.senha.value;
            const confirmar = formulario.confirmarSenha.value;
            if (senha !== confirmar) {
                setErro("As senhas informadas não conferem.");
                formulario.confirmarSenha.focus();
                return false;
            }
        }

        return true;
    };

    const exibirEtapa = (numero) => setEtapaAtual(numero);

    const aoAvancar = () => {
        setErro("");
        if (etapaValida(etapaAtual) && etapaAtual < TOTAL_ETAPAS) {
            exibirEtapa(etapaAtual + 1);
        }
    };

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
            email: formulario.email.value.trim(),
            senha: formulario.senha.value,

            // --- ETAPA 1 ---
            curso: campos.curso,
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
            publicoAlvoEducacaoEspecial: formulario.publicoAlvoEducacaoEspecial.checked,
            canhoto: formulario.canhoto.checked,
            necessitaAcompanhamentoInstitucional: formulario.necessitaAcompanhamentoInstitucional.checked,

            // --- ETAPA 6 ---
            instituicaoOrigem: texto("instituicaoOrigem"),
            tipoEscolaEnsinoMedio: campos.tipoEscolaEnsinoMedio || null,
            nomeInstituicaoConclusao: texto("nomeInstituicaoConclusao"),
            mesConclusaoEnsinoMedio: numero("mesConclusaoEnsinoMedio"),
            anoConclusaoEnsinoMedio: numero("anoConclusaoEnsinoMedio"),
            racaEtnia: campos.racaEtnia || null,

            // --- ETAPA 7 ---
            termoConsentimento: formulario.termoConsentimento.checked
        };
    };

    const aoSubmeter = async (evento) => {
        evento.preventDefault();

        setMensagem("");
        setErro("");

        const formulario = formularioRef.current;
        if (!etapaValida(7)) {
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
                "Guarde essa informação e faça login no Portal do Aluno com o e-mail e a senha cadastrados.";
            setMensagem(texto);
            notificar("Matrícula realizada com sucesso!", "success");

            formulario.reset();
            setCampos(CAMPOS_CONTROLADOS_PADRAO);
            setCursoInvalido(false);
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
        <SiteChrome>
            <div className="grad-page" ref={raizRef}>
                <main>
                    <section className="grad-hero">
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
                                    Preencha o formulário em 7 etapas para se matricular na UniAura. Ao concluir, você
                                    recebe seu número de matrícula (RA) e já pode acessar o Portal do Aluno com o
                                    e-mail e a senha cadastrados aqui.
                                </p>
                            </div>
                        </div>
                    </section>

                    <section className="grad-section" id="grad-cadastro">
                        <div className="grad-container">
                            <div className="grad-section-title" data-revelar>
                                <span className="grad-eyebrow">Matrícula</span>
                                <h2>Preencha seus dados</h2>
                                <p>Os campos marcados com asterisco são obrigatórios.</p>
                            </div>

                            <div className="site-wizard" data-revelar>
                                <ol className="site-wizard-passos" aria-label="Etapas do cadastro">
                                    {PASSOS.map((passo, indice) => {
                                        const numero = indice + 1;
                                        return (
                                            <li
                                                key={passo.nome}
                                                className={`site-wizard-passo${numero === etapaAtual ? " ativo" : ""}${numero < etapaAtual ? " concluida" : ""}`}
                                                onClick={() => aoClicarPasso(numero)}
                                            >
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

                                <form id="formCadastroAluno" className="site-form" noValidate ref={formularioRef} onSubmit={aoSubmeter}>

                                    {/* --- ETAPA 1: DADOS PESSOAIS --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="1" hidden={etapaAtual !== 1}>
                                        <legend>Dados pessoais</legend>
                                        <div className="site-form-grid">
                                            <div className="field field-full">
                                                <label htmlFor="cadNome">Nome completo *</label>
                                                <input id="cadNome" name="nome" type="text" required minLength={5} autoComplete="name" />
                                            </div>

                                            <div className="field field-full">
                                                <label htmlFor="cadCurso">Curso pretendido *</label>
                                                <Dropdown
                                                    id="cadCurso" name="curso" options={OPCOES_CURSO}
                                                    value={campos.curso} onChange={(v) => atualizarCampo("curso", v)}
                                                    placeholder="Selecione o curso" invalid={cursoInvalido} required
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNomePai">Nome do pai</label>
                                                <input id="cadNomePai" name="nomePai" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNomeMae">Nome da mãe</label>
                                                <input id="cadNomeMae" name="nomeMae" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadSexo">Sexo</label>
                                                <Dropdown
                                                    id="cadSexo" name="sexo" options={OPCOES_SEXO}
                                                    value={campos.sexo} onChange={(v) => atualizarCampo("sexo", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadEstadoCivil">Estado civil</label>
                                                <Dropdown
                                                    id="cadEstadoCivil" name="estadoCivil" options={OPCOES_ESTADO_CIVIL}
                                                    value={campos.estadoCivil} onChange={(v) => atualizarCampo("estadoCivil", v)}
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
                                                <label htmlFor="cadNascimento">Data de nascimento *</label>
                                                <input id="cadNascimento" name="dataNascimento" type="date" required />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadMunicipioNascimento">Município de nascimento</label>
                                                <input id="cadMunicipioNascimento" name="municipioNascimento" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadCidade">Cidade</label>
                                                <input id="cadCidade" name="cidade" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadEstado">Estado</label>
                                                <Dropdown
                                                    id="cadEstado" name="estado" options={OPCOES_UF}
                                                    value={campos.estado} onChange={(v) => atualizarCampo("estado", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNacionalidade">Nacionalidade</label>
                                                <input id="cadNacionalidade" name="nacionalidade" type="text" defaultValue="Brasileira" />
                                            </div>
                                        </div>
                                    </fieldset>

                                    {/* --- ETAPA 3: IDENTIFICAÇÃO --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="3" hidden={etapaAtual !== 3}>
                                        <legend>Identificação</legend>
                                        <div className="site-form-grid">
                                            <div className="field">
                                                <label htmlFor="cadCpf">CPF *</label>
                                                <input
                                                    id="cadCpf" name="cpf" type="text" required inputMode="numeric"
                                                    maxLength={14} placeholder="000.000.000-00"
                                                    value={campos.cpf} onChange={(e) => atualizarCampo("cpf", mascararCpf(e.target.value))}
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNumeroIdentidade">Número da identidade (RG)</label>
                                                <input id="cadNumeroIdentidade" name="documentoNumero" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadOrgaoEmissor">Órgão emissor da identidade</label>
                                                <input id="cadOrgaoEmissor" name="documentoOrgaoEmissor" type="text" placeholder="SSP" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadUfExpedicao">UF de expedição da identidade</label>
                                                <Dropdown
                                                    id="cadUfExpedicao" name="ufExpedicaoIdentidade" options={OPCOES_UF}
                                                    value={campos.ufExpedicaoIdentidade}
                                                    onChange={(v) => atualizarCampo("ufExpedicaoIdentidade", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadDataExpedicao">Data de expedição da identidade</label>
                                                <input id="cadDataExpedicao" name="dataExpedicaoIdentidade" type="date" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadTituloEleitor">Número do título de eleitor</label>
                                                <input id="cadTituloEleitor" name="numeroTituloEleitor" type="text" inputMode="numeric" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadZonaEleitoral">Número da zona eleitoral</label>
                                                <input id="cadZonaEleitoral" name="numeroZonaEleitoral" type="text" inputMode="numeric" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadUfZonaEleitoral">UF da zona eleitoral</label>
                                                <Dropdown
                                                    id="cadUfZonaEleitoral" name="ufZonaEleitoral" options={OPCOES_UF}
                                                    value={campos.ufZonaEleitoral}
                                                    onChange={(v) => atualizarCampo("ufZonaEleitoral", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadCertificadoReservista">Número do certificado de reservista (opcional)</label>
                                                <input id="cadCertificadoReservista" name="numeroCertificadoReservista" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadOrgaoReservista">Órgão emissor do certificado de reservista (opcional)</label>
                                                <input id="cadOrgaoReservista" name="orgaoEmissorCertificadoReservista" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadUfReservista">UF do certificado de reservista (opcional)</label>
                                                <Dropdown
                                                    id="cadUfReservista" name="ufReservista" options={OPCOES_UF}
                                                    value={campos.ufReservista}
                                                    onChange={(v) => atualizarCampo("ufReservista", v)}
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
                                                    id="cadTipoEndereco" name="tipoEndereco" options={OPCOES_TIPO_ENDERECO}
                                                    value={campos.tipoEndereco} onChange={(v) => atualizarCampo("tipoEndereco", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadCep">CEP</label>
                                                <input
                                                    id="cadCep" name="enderecoCep" type="text" inputMode="numeric"
                                                    maxLength={9} placeholder="00000-000"
                                                    value={campos.enderecoCep}
                                                    onChange={(e) => atualizarCampo("enderecoCep", mascararCep(e.target.value))}
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadRua">Rua</label>
                                                <input id="cadRua" name="enderecoLogradouro" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNumeroEndereco">Número</label>
                                                <input id="cadNumeroEndereco" name="enderecoNumero" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadComplemento">Complemento</label>
                                                <input id="cadComplemento" name="enderecoComplemento" type="text" placeholder="Apto, bloco..." />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadBairro">Bairro</label>
                                                <input id="cadBairro" name="enderecoBairro" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadUfEndereco">UF de endereço</label>
                                                <Dropdown
                                                    id="cadUfEndereco" name="enderecoUf" options={OPCOES_UF}
                                                    value={campos.enderecoUf} onChange={(v) => atualizarCampo("enderecoUf", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadEmail">E-mail *</label>
                                                <input id="cadEmail" name="email" type="email" required autoComplete="email" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadTelefone">Telefone *</label>
                                                <input
                                                    id="cadTelefone" name="telefone" type="tel" required
                                                    maxLength={15} placeholder="(00) 00000-0000" autoComplete="tel"
                                                    value={campos.telefone}
                                                    onChange={(e) => atualizarCampo("telefone", mascararTelefone(e.target.value))}
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadTelefoneEmergencia">Telefone de emergência</label>
                                                <input
                                                    id="cadTelefoneEmergencia" name="telefoneEmergencia" type="tel"
                                                    maxLength={15} placeholder="(00) 00000-0000"
                                                    value={campos.telefoneEmergencia}
                                                    onChange={(e) => atualizarCampo("telefoneEmergencia", mascararTelefone(e.target.value))}
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadSenha">Senha de acesso *</label>
                                                <input id="cadSenha" name="senha" type="password" required minLength={6} autoComplete="new-password" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadConfirmarSenha">Confirmar senha *</label>
                                                <input id="cadConfirmarSenha" name="confirmarSenha" type="password" required minLength={6} autoComplete="new-password" />
                                            </div>
                                        </div>
                                    </fieldset>

                                    {/* --- ETAPA 5: INFORMAÇÕES GERAIS --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="5" hidden={etapaAtual !== 5}>
                                        <legend>Informações gerais</legend>
                                        <div className="site-form-grid">
                                            <div className="field">
                                                <label htmlFor="cadTipoSanguineo">Tipo sanguíneo</label>
                                                <Dropdown
                                                    id="cadTipoSanguineo" name="tipoSanguineo" options={OPCOES_TIPO_SANGUINEO}
                                                    value={campos.tipoSanguineo} onChange={(v) => atualizarCampo("tipoSanguineo", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>
                                        </div>

                                        <label className="site-form-termos">
                                            <input id="cadEducacaoEspecial" name="publicoAlvoEducacaoEspecial" type="checkbox" />
                                            <span>Sou aluno público-alvo da Educação Especial.</span>
                                        </label>

                                        <label className="site-form-termos">
                                            <input id="cadCanhoto" name="canhoto" type="checkbox" />
                                            <span>Sou canhoto(a).</span>
                                        </label>

                                        <label className="site-form-termos">
                                            <input id="cadAcompanhamento" name="necessitaAcompanhamentoInstitucional" type="checkbox" />
                                            <span>
                                                Necessito de acompanhamento institucional.
                                                <button
                                                    type="button" className="site-info-btn" id="btnInfoAcompanhamento"
                                                    aria-label="O que é acompanhamento institucional?"
                                                    onClick={() => setMostrarInfoAcompanhamento((atual) => !atual)}
                                                >
                                                    <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor"
                                                         strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                                                        <circle cx="12" cy="12" r="10"></circle>
                                                        <line x1="12" y1="16" x2="12" y2="12"></line>
                                                        <line x1="12" y1="8" x2="12.01" y2="8"></line>
                                                    </svg>
                                                </button>
                                            </span>
                                        </label>
                                        <p className="site-info-texto" id="infoAcompanhamento" hidden={!mostrarInfoAcompanhamento}>
                                            O acompanhamento institucional é o suporte pedagógico e psicossocial oferecido pela
                                            UniAura a alunos que precisem de apoio extra durante o curso (dificuldades de
                                            aprendizagem, questões de saúde, adaptação ou outras necessidades). Marcar esta
                                            opção não afeta sua matrícula: a coordenação apenas entrará em contato para
                                            entender como pode ajudar.
                                        </p>
                                    </fieldset>

                                    {/* --- ETAPA 6: CENSO --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="6" hidden={etapaAtual !== 6}>
                                        <legend>Censo</legend>
                                        <div className="site-form-grid">
                                            <div className="field">
                                                <label htmlFor="cadInstituicaoOrigem">Instituição de origem</label>
                                                <input id="cadInstituicaoOrigem" name="instituicaoOrigem" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadTipoEscola">Tipo de escola do ensino médio</label>
                                                <Dropdown
                                                    id="cadTipoEscola" name="tipoEscolaEnsinoMedio" options={OPCOES_TIPO_ESCOLA}
                                                    value={campos.tipoEscolaEnsinoMedio}
                                                    onChange={(v) => atualizarCampo("tipoEscolaEnsinoMedio", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadNomeInstituicao">Nome da instituição (conclusão do ensino médio)</label>
                                                <input id="cadNomeInstituicao" name="nomeInstituicaoConclusao" type="text" />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadMesConclusao">Mês de conclusão do ensino médio</label>
                                                <Dropdown
                                                    id="cadMesConclusao" name="mesConclusaoEnsinoMedio" options={OPCOES_MES}
                                                    value={campos.mesConclusaoEnsinoMedio}
                                                    onChange={(v) => atualizarCampo("mesConclusaoEnsinoMedio", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadAnoConclusao">Ano de conclusão</label>
                                                <input
                                                    id="cadAnoConclusao" name="anoConclusaoEnsinoMedio" type="number"
                                                    inputMode="numeric" min={1950} max={2100} placeholder="2024"
                                                />
                                            </div>

                                            <div className="field">
                                                <label htmlFor="cadRacaEtnia">Raça/Etnia</label>
                                                <Dropdown
                                                    id="cadRacaEtnia" name="racaEtnia" options={OPCOES_RACA_ETNIA}
                                                    value={campos.racaEtnia} onChange={(v) => atualizarCampo("racaEtnia", v)}
                                                    placeholder="Selecione"
                                                />
                                            </div>
                                        </div>
                                    </fieldset>

                                    {/* --- ETAPA 7: TERMO DE CONSENTIMENTO --- */}
                                    <fieldset className="site-wizard-etapa" data-etapa="7" hidden={etapaAtual !== 7}>
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
                                            <input id="cadTermos" name="termoConsentimento" type="checkbox" required />
                                            <span>Li e aceito o termo de consentimento para uso dos meus dados pessoais. *</span>
                                        </label>
                                    </fieldset>

                                    <div className="site-wizard-acoes">
                                        <button
                                            type="button" id="btnVoltarEtapa" className="grad-btn grad-btn-outline"
                                            hidden={etapaAtual === 1} onClick={aoVoltar}
                                        >
                                            ← Voltar
                                        </button>
                                        <span className="site-wizard-contador">
                                            Etapa <span id="wizardEtapaAtual">{etapaAtual}</span> de {TOTAL_ETAPAS}
                                        </span>
                                        <button
                                            type="button" id="btnAvancarEtapa" className="grad-btn grad-btn-primary"
                                            hidden={etapaAtual === TOTAL_ETAPAS} onClick={aoAvancar}
                                        >
                                            Próxima etapa →
                                        </button>
                                        <button
                                            type="submit" id="btnConcluirCadastro" className="grad-btn grad-btn-primary"
                                            hidden={etapaAtual !== TOTAL_ETAPAS} disabled={enviando}
                                        >
                                            {enviando ? "Enviando..." : "Concluir matrícula"}
                                        </button>
                                    </div>

                                    {mensagem && <p id="cadMensagem" className="site-form-msg" aria-live="polite">{mensagem}</p>}
                                    {erro && <p id="cadErro" className="site-form-erro" aria-live="polite">{erro}</p>}
                                </form>
                            </div>
                        </div>
                    </section>
                </main>
            </div>
        </SiteChrome>
    );
}
