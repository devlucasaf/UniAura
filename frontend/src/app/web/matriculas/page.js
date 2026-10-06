"use client";

import { useState }                         from "react";
import { InputText }                        from "primereact/inputtext";
import { InputTextarea }                    from "primereact/inputtextarea";
import { Dropdown }                         from "primereact/dropdown";
import { Checkbox }                         from "primereact/checkbox";
import { Button }                           from "primereact/button";
import { Message }                          from "primereact/message";
import Link                                 from "next/link";
import CampoData                            from "@/components/web/CampoData";
import { useEfeitosDePagina }               from "@/hooks/useEfeitosDePagina";
import { mascararCpf, mascararTelefone }    from "@/lib/mascaras";

const ALFABETO_PROTOCOLO = "23456789BCDFGHJKLMNPQRSTVWXZ";
const TAMANHO_SUFIXO = 8;

const CURSOS = [
    {
        value: "ciencia-da-computacao",
        label: "Ciência da Computação"
    },
    {
        value: "analise-e-desenvolvimento-de-sistemas",
        label: "Análise e Desenvolvimento de Sistemas"
    },
    {
        value: "engenharia-de-software",
        label: "Engenharia de Software"
    },
    {
        value: "ciencia-de-dados",
        label: "Ciência de Dados"
    },
    {
        value: "engenharia-mecatronica",
        label: "Engenharia Mecatrônica"
    }
];

const TURNOS = [
    {
        value: "MATUTINO",
        label: "Matutino"
    },
    {
        value: "VESPERTINO",
        label: "Vespertino"
    },
    {
        value: "NOTURNO",
        label: "Noturno"
    }
];

const FORMAS_INGRESSO = [
    {
        value: "VESTIBULAR",
        label: "Vestibular"
    },
    {
        value: "ENEM",
        label: "Nota do ENEM"
    },
    {
        value: "TRANSFERENCIA",
        label: "Transferência de outra instituição"
    },
    {
        value: "SEGUNDA_GRADUACAO",
        label: "Segunda graduação"
    }
];

const ETAPAS = [
    {
        titulo: "1 · Escolha o curso",
        texto: "Compare a matriz curricular e a carreira de cada graduação antes de decidir.",
        icone: <path d="M4 6h16M4 12h16M4 18h10"></path>
    },
    {
        titulo: "2 · Preencha a inscrição",
        texto: "Formulário on-line com seus dados e a forma de ingresso escolhida.",
        icone: (
            <>
                <path d="M12 20h9"></path>
                <path d="M16.5 3.5a2.1 2.1 0 0 1 3 3L7 19l-4 1 1-4Z"></path>
            </>
        )
    },
    {
        titulo: "3 · Envie os documentos",
        texto: "A secretaria confere a documentação e valida sua inscrição.",
        icone: (
            <>
                <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8Z"></path>
                <path d="M14 2v6h6"></path>
            </>
        )
    },
    {
        titulo: "4 · Confirme a matrícula",
        texto: "Assine o contrato, receba seu RA e o acesso ao Portal do Aluno.",
        icone: <path d="M20 6L9 17l-5-5"></path>
    }
];

const DOCUMENTOS = [
    "RG e CPF",
    "Certidão de nascimento ou casamento",
    "Histórico escolar do ensino médio",
    "Certificado de conclusão do ensino médio",
    "Comprovante de residência atualizado",
    "Título de eleitor (maiores de 18 anos)",
    "Certificado de reservista (homens)",
    "Foto 3x4 recente"
];

// --- GERA O NÚMERO DE PROTOCOLO DA INSCRIÇÃO ---
function gerarProtocolo() {
    let sufixo = "";
    for (let i = 0; i < TAMANHO_SUFIXO; i++) {
        sufixo += ALFABETO_PROTOCOLO.charAt(Math.floor(Math.random() * ALFABETO_PROTOCOLO.length));
    }
    return `MAT-${new Date().getFullYear()}-${sufixo}`;
}

// --- VERIFICA SE A DATA DE NASCIMENTO INFORMADA RESULTA EM UMA IDADE VÁLIDA ---
function idadeValida(valor) {
    if (!valor) {
        return false;
    }

    const nascimento = new Date(valor);
    if (Number.isNaN(nascimento.getTime())) {
        return false;
    }
    const anos = (new Date() - nascimento) / (1000 * 60 * 60 * 24 * 365.25);
    return anos > 14 && anos < 120;
}

// --- COMPONENTE DA PÁGINA DE MATRÍCULAS ---
export default function MatriculasPage() {
    const raizRef = useEfeitosDePagina();
    const [cpf,             setCpf]             = useState("");
    const [telefone,        setTelefone]        = useState("");
    const [curso,           setCurso]           = useState("");
    const [turno,           setTurno]           = useState("");
    const [formaIngresso,   setFormaIngresso]   = useState("");
    const [nascimento,      setNascimento]      = useState("");
    const [termos,          setTermos]          = useState(false);
    const [erros,           setErros]           = useState({});
    const [mensagem,        setMensagem]        = useState("");
    const [erro,            setErro]            = useState("");
    const [enviando,        setEnviando]        = useState(false);

    // --- REMOVE O ERRO DE UM CAMPO ASSIM QUE O USUÁRIO MEXE NELE ---
    const limparErro = (nome) => {
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

    // --- VERIFICA CADA CAMPO OBRIGATÓRIO E RETORNA UMA MENSAGEM POR CAMPO COM PROBLEMA ---
    const validarCampos = (formulario) => {
        const novos = {};
        const nome = formulario.nome.value.trim();
        const email = formulario.email.value.trim();

        if (!nome) {
            novos.nome = "Informe seu nome completo.";
        } else if (nome.length < 5) {
            novos.nome = "Use pelo menos 5 caracteres.";
        }

        if (!email) {
            novos.email = "Informe seu e-mail.";
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            novos.email = "Informe um e-mail válido.";
        }

        if (!cpf) {
            novos.cpf = "Informe seu CPF.";
        } else if (cpf.replace(/\D/g, "").length !== 11) {
            novos.cpf = "Informe um CPF com 11 dígitos.";
        }

        if (!telefone) {
            novos.telefone = "Informe seu telefone.";
        } else if (telefone.replace(/\D/g, "").length < 10) {
            novos.telefone = "Informe um telefone com DDD.";
        }

        if (!nascimento) {
            novos.nascimento = "Informe sua data de nascimento.";
        } else if (!idadeValida(nascimento)) {
            novos.nascimento = "Informe uma data de nascimento válida.";
        }

        if (!curso) {
            novos.curso = "Escolha o curso pretendido.";
        }

        if (!turno) {
            novos.turno = "Escolha o turno.";
        }

        if (!formaIngresso) {
            novos.formaIngresso = "Escolha a forma de ingresso.";
        }

        if (!termos) {
            novos.termos = "Você precisa aceitar os termos para enviar.";
        }

        return novos;
    };

    // --- VALIDA E PROCESSA O ENVIO DA INSCRIÇÃO DE MATRÍCULA ---
    const aoEnviar = (evento) => {
        evento.preventDefault();
        const formulario = evento.target;
        setMensagem("");

        const novosErros = validarCampos(formulario);
        setErros(novosErros);
        if (Object.keys(novosErros).length > 0) {
            setErro("Revise os campos destacados antes de enviar.");
            return;
        }
        setErro("");

        setEnviando(true);
        const protocolo = gerarProtocolo();

        setTimeout(() => {
            setMensagem(
                `Inscrição registrada! Guarde o seu protocolo: ${protocolo}. Você receberá por e-mail as ` +
                "instruções para o envio da documentação."
            );
            formulario.reset();
            setCpf("");
            setTelefone("");
            setCurso("");
            setTurno("");
            setFormaIngresso("");
            setNascimento("");
            setTermos(false);
            setErros({});
            setEnviando(false);
        }, 300);
    };

    return (
        <>
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
                                        <rect x="3" y="4" width="18" height="17" rx="2"></rect>
                                        <path d="M16 2v4M8 2v4M3 10h18"></path>
                                    </svg>
                                    Ingresso · 2027.1
                                </span>

                                <h1 data-entrada style={{ "--atraso": "160ms" }}>Faça sua <span>matrícula</span></h1>

                                <p data-entrada style={{ "--atraso": "260ms" }}>
                                    As inscrições para o próximo semestre estão abertas. Preencha o formulário,
                                    envie a documentação e garanta sua vaga na UniAura. Todo o processo é on-line
                                    e leva menos de dez minutos.
                                </p>

                                <div className="grad-actions" data-entrada style={{ "--atraso": "360ms" }}>
                                    <Button label="Iniciar inscrição" onClick={() => document.getElementById("grad-inscricao")?.scrollIntoView({ behavior: "smooth" })} />
                                    <Button
                                        label="Ver documentos exigidos"
                                        outlined
                                        onClick={() => document.getElementById("grad-documentos")?.scrollIntoView({ behavior: "smooth" })}
                                    />
                                </div>
                            </div>

                            <div className="grad-hero-card" data-entrada style={{ "--atraso": "460ms" }} aria-label="Prazos do processo seletivo">
                                <div className="grad-janela">
                                    <div className="grad-janela-topo">
                                        <span className="grad-janela-ponto"></span>
                                        <span className="grad-janela-ponto"></span>
                                        <span className="grad-janela-ponto"></span>
                                        <span className="grad-janela-titulo">calendario-2027-1</span>
                                    </div>
                                    <div className="grad-janela-corpo">
                                        <span className="grad-janela-linha">
                                            <span className="muted">{"// Calendário do processo seletivo"}</span>
                                        </span>
                                        <span className="grad-janela-linha"> </span>
                                        <span className="grad-janela-linha">
                                            <span className="blue">Inscrições</span>
                                            <span className="green">05/01 a 28/02</span>
                                        </span>
                                        <span className="grad-janela-linha">
                                            <span className="blue">Prova on-line</span>
                                            <span className="green">07/03</span>
                                        </span>
                                        <span className="grad-janela-linha">
                                            <span className="blue">Resultado</span>
                                            <span className="green">14/03</span>
                                        </span>
                                        <span className="grad-janela-linha">
                                            <span className="blue">Documentação</span>
                                            <span className="green">15/03 a 22/03</span>
                                        </span>
                                        <span className="grad-janela-linha">
                                            <span className="blue">Início das aulas</span>
                                            <span className="green">01/04</span>
                                        </span>
                                        <span className="grad-janela-linha"> </span>
                                        <span className="grad-janela-linha">
                                            <span className="muted">{"// Vagas limitadas por turma"}</span>
                                            <span className="grad-janela-cursor" aria-hidden="true"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    <section className="grad-section" id="grad-etapas">
                        <div className="grad-container">
                            <div className="grad-section-title" data-revelar>
                                <span className="grad-eyebrow">Como funciona</span>
                                <h2>Quatro etapas até a sala de aula</h2>
                                <p>Você acompanha cada etapa pelo número de protocolo gerado na inscrição.</p>
                            </div>

                            <div className="grad-grid-3">
                                {ETAPAS.map((etapa, indice) => (
                                    <article key={etapa.titulo} className="grad-card" data-revelar style={{ "--atraso": `${indice * 90}ms` }}>
                                        <div className="grad-card-icon">
                                            <svg viewBox="0 0 24 24" width="23" height="23" fill="none" stroke="currentColor"
                                                    strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
                                                {etapa.icone}
                                            </svg>
                                        </div>
                                        <h3>{etapa.titulo}</h3>
                                        <p>{etapa.texto}</p>
                                    </article>
                                ))}
                            </div>
                        </div>
                    </section>

                    <section className="grad-section grad-section-alt" id="grad-documentos">
                        <div className="grad-container">
                            <div className="grad-career">
                                <div data-revelar>
                                    <span className="grad-eyebrow">Documentação</span>
                                    <h2 className="grad-career-titulo">O que você precisa ter em mãos</h2>
                                    <p className="muted">
                                        Aceitamos cópia digital legível de cada documento. Se faltar algum, você
                                        ainda consegue se inscrever e complementar depois, dentro do prazo.
                                    </p>
                                </div>

                                <div className="grad-card" data-revelar style={{ "--atraso": "120ms" }}>
                                    <strong>Documentos exigidos</strong>
                                    <div className="grad-semester-list">
                                        {DOCUMENTOS.map((doc) => (
                                            <span key={doc} className="grad-subject">{doc}</span>
                                        ))}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    <section className="grad-section" id="grad-inscricao">
                        <div className="grad-container">
                            <div className="grad-section-title" data-revelar>
                                <span className="grad-eyebrow">Inscrição</span>
                                <h2>Preencha seus dados</h2>
                                <p>Os campos marcados com asterisco são obrigatórios.</p>
                            </div>

                            <form id="formMatricula" className="site-form site-form-matricula" noValidate data-revelar onSubmit={aoEnviar}>
                                <div className="site-form-grid">
                                    <div className="field">
                                        <label htmlFor="matNome">Nome completo <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                        <InputText
                                            id="matNome"
                                            name="nome"
                                            autoComplete="name"
                                            className={classeErro("nome")}
                                            onInput={() => limparErro("nome")}
                                        />
                                        {erroDe("nome")}
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matEmail">E-mail <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                        <InputText
                                            id="matEmail"
                                            name="email"
                                            type="email"
                                            autoComplete="email"
                                            className={classeErro("email")}
                                            onInput={() => limparErro("email")}
                                        />
                                        {erroDe("email")}
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matCpf">CPF <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                        <InputText
                                            id="matCpf"
                                            name="cpf"
                                            inputMode="numeric"
                                            maxLength={14}
                                            placeholder="000.000.000-00"
                                            value={cpf}
                                            onChange={(e) => { setCpf(mascararCpf(e.target.value)); limparErro("cpf"); }}
                                            className={classeErro("cpf")}
                                        />
                                        {erroDe("cpf")}
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matTelefone">Telefone <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                        <InputText
                                            id="matTelefone"
                                            name="telefone"
                                            type="tel"
                                            maxLength={15}
                                            placeholder="(00) 00000-0000"
                                            autoComplete="tel"
                                            value={telefone}
                                            onChange={(e) => { setTelefone(mascararTelefone(e.target.value)); limparErro("telefone"); }}
                                            className={classeErro("telefone")}
                                        />
                                        {erroDe("telefone")}
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matNascimento">Data de nascimento <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                        <CampoData
                                            id="matNascimento"
                                            name="dataNascimento"
                                            yearRange="1920:2010"
                                            invalido={!!erros.nascimento}
                                            onChange={(iso) => { setNascimento(iso); limparErro("nascimento"); }}
                                        />
                                        {erroDe("nascimento")}
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matCurso">Curso pretendido <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                        <Dropdown
                                            inputId="matCurso"
                                            name="curso"
                                            options={CURSOS}
                                            value={curso}
                                            onChange={(e) => { setCurso(e.value); limparErro("curso"); }}
                                            placeholder="Selecione o curso"
                                            className={classeErro("curso")}
                                        />
                                        {erroDe("curso")}
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matTurno">Turno <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                        <Dropdown
                                            inputId="matTurno"
                                            name="turno"
                                            options={TURNOS}
                                            value={turno}
                                            onChange={(e) => { setTurno(e.value); limparErro("turno"); }}
                                            placeholder="Selecione o turno"
                                            className={classeErro("turno")}
                                        />
                                        {erroDe("turno")}
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matIngresso">Forma de ingresso <span className="ua-obrigatorio" aria-hidden="true">*</span></label>
                                        <Dropdown
                                            inputId="matIngresso"
                                            name="formaIngresso"
                                            options={FORMAS_INGRESSO}
                                            value={formaIngresso}
                                            onChange={(e) => { setFormaIngresso(e.value); limparErro("formaIngresso"); }}
                                            placeholder="Selecione a forma de ingresso"
                                            className={classeErro("formaIngresso")}
                                        />
                                        {erroDe("formaIngresso")}
                                    </div>
                                </div>

                                <div className="field">
                                    <label htmlFor="matObservacoes">Observações</label>
                                    <InputTextarea
                                        id="matObservacoes"
                                        name="observacoes"
                                        rows={4}
                                        placeholder="Conte se você precisa de algum atendimento específico."
                                    />
                                </div>

                                <div className="site-form-termos">
                                    <div className="site-form-termos-linha">
                                        <Checkbox
                                            inputId="matTermos"
                                            name="termos"
                                            checked={termos}
                                            className={classeErro("termos")}
                                            onChange={(e) => { setTermos(e.checked); limparErro("termos"); }}
                                        />
                                        <label htmlFor="matTermos">
                                            Li e aceito os{" "}
                                            <Link href="/web/termos-processo-seletivo" className="ua-link-destaque">termos do processo seletivo</Link>
                                            {" "}e a{" "}
                                            <Link href="/web/privacidade" className="ua-link-destaque">política de privacidade</Link>.{" "}
                                            <span className="ua-obrigatorio" aria-hidden="true">*</span>
                                        </label>
                                    </div>
                                    {erroDe("termos")}
                                </div>

                                <Button id="btnEnviarMatricula" type="submit" label={enviando ? "Enviando..." : "Enviar inscrição"} loading={enviando} />

                                {mensagem && <Message severity="success" text={mensagem} />}
                                {erro && <Message severity="error" text={erro} />}
                            </form>
                        </div>
                    </section>
                </main>
            </div>
        </>
    );
}
