"use client";

import { useState } from "react";
import SiteChrome from "@/components/site/SiteChrome";
import Dropdown from "@/components/ui/Dropdown";
import { useEfeitosDePagina } from "@/hooks/useEfeitosDePagina";
import { mascararCpf, mascararTelefone } from "@/lib/mascaras";

const ALFABETO_PROTOCOLO = "23456789BCDFGHJKLMNPQRSTVWXZ";
const TAMANHO_SUFIXO = 8;

const CURSOS = [
    { value: "ciencia-da-computacao", label: "Ciência da Computação" },
    { value: "analise-e-desenvolvimento-de-sistemas", label: "Análise e Desenvolvimento de Sistemas" },
    { value: "engenharia-de-software", label: "Engenharia de Software" },
    { value: "ciencia-de-dados", label: "Ciência de Dados" },
    { value: "engenharia-mecatronica", label: "Engenharia Mecatrônica" }
];

const TURNOS = [
    { value: "MATUTINO", label: "Matutino" },
    { value: "VESPERTINO", label: "Vespertino" },
    { value: "NOTURNO", label: "Noturno" }
];

const FORMAS_INGRESSO = [
    { value: "VESTIBULAR", label: "Vestibular" },
    { value: "ENEM", label: "Nota do ENEM" },
    { value: "TRANSFERENCIA", label: "Transferência de outra instituição" },
    { value: "SEGUNDA_GRADUACAO", label: "Segunda graduação" }
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

function gerarProtocolo() {
    let sufixo = "";
    for (let i = 0; i < TAMANHO_SUFIXO; i++) {
        sufixo += ALFABETO_PROTOCOLO.charAt(Math.floor(Math.random() * ALFABETO_PROTOCOLO.length));
    }
    return `MAT-${new Date().getFullYear()}-${sufixo}`;
}

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

export default function MatriculasPage() {
    const raizRef = useEfeitosDePagina();
    const [cpf, setCpf] = useState("");
    const [telefone, setTelefone] = useState("");
    const [curso, setCurso] = useState("");
    const [turno, setTurno] = useState("");
    const [formaIngresso, setFormaIngresso] = useState("");
    const [camposInvalidos, setCamposInvalidos] = useState({});
    const [mensagem, setMensagem] = useState("");
    const [erro, setErro] = useState("");
    const [enviando, setEnviando] = useState(false);

    const aoEnviar = (evento) => {
        evento.preventDefault();
        const formulario = evento.target;
        setMensagem("");
        setErro("");

        if (!formulario.checkValidity()) {
            formulario.reportValidity();
            setErro("Revise os campos destacados antes de enviar.");
            return;
        }

        const invalidos = {
            curso: !curso,
            turno: !turno,
            formaIngresso: !formaIngresso
        };
        setCamposInvalidos(invalidos);
        if (Object.values(invalidos).some(Boolean)) {
            setErro("Revise os campos destacados antes de enviar.");
            return;
        }

        const cpfDigitos = formulario.cpf.value.replace(/\D/g, "");
        if (cpfDigitos.length !== 11) {
            setErro("Informe um CPF com 11 dígitos.");
            formulario.cpf.focus();
            return;
        }

        if (!idadeValida(formulario.dataNascimento.value)) {
            setErro("Informe uma data de nascimento válida.");
            formulario.dataNascimento.focus();
            return;
        }

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
            setCamposInvalidos({});
            setEnviando(false);
        }, 300);
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
                                    <a className="grad-btn grad-btn-primary" href="#grad-inscricao">Iniciar inscrição</a>
                                    <a className="grad-btn grad-btn-outline" href="#grad-documentos">Ver documentos exigidos</a>
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
                                        <span className="grad-janela-linha"><span className="muted">{"// Calendário do processo seletivo"}</span></span>
                                        <span className="grad-janela-linha"> </span>
                                        <span className="grad-janela-linha"><span className="blue">Inscrições</span>      <span className="green">05/01 a 28/02</span></span>
                                        <span className="grad-janela-linha"><span className="blue">Prova on-line</span>   <span className="green">07/03</span></span>
                                        <span className="grad-janela-linha"><span className="blue">Resultado</span>       <span className="green">14/03</span></span>
                                        <span className="grad-janela-linha"><span className="blue">Documentação</span>    <span className="green">15/03 a 22/03</span></span>
                                        <span className="grad-janela-linha"><span className="blue">Início das aulas</span> <span className="green">01/04</span></span>
                                        <span className="grad-janela-linha"> </span>
                                        <span className="grad-janela-linha"><span className="muted">{"// Vagas limitadas por turma"}</span><span className="grad-janela-cursor" aria-hidden="true"></span></span>
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
                                        <label htmlFor="matNome">Nome completo *</label>
                                        <input
                                            id="matNome"
                                            name="nome"
                                            type="text"
                                            required
                                            minLength={5}
                                            autoComplete="name"
                                        />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matEmail">E-mail *</label>
                                        <input id="matEmail" name="email" type="email" required autoComplete="email" />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matCpf">CPF *</label>
                                        <input
                                            id="matCpf"
                                            name="cpf"
                                            type="text"
                                            required
                                            inputMode="numeric"
                                            maxLength={14}
                                            placeholder="000.000.000-00"
                                            value={cpf}
                                            onChange={(e) => setCpf(mascararCpf(e.target.value))}
                                        />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matTelefone">Telefone *</label>
                                        <input
                                            id="matTelefone" name="telefone" type="tel" required maxLength={15}
                                            placeholder="(00) 00000-0000" autoComplete="tel" value={telefone}
                                            onChange={(e) => setTelefone(mascararTelefone(e.target.value))}
                                        />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matNascimento">Data de nascimento *</label>
                                        <input id="matNascimento" name="dataNascimento" type="date" required />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matCurso">Curso pretendido *</label>
                                        <Dropdown
                                            id="matCurso" name="curso" options={CURSOS} value={curso} onChange={setCurso}
                                            placeholder="Selecione o curso" invalid={camposInvalidos.curso}
                                        />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matTurno">Turno *</label>
                                        <Dropdown
                                            id="matTurno" name="turno" options={TURNOS} value={turno} onChange={setTurno}
                                            placeholder="Selecione o turno" invalid={camposInvalidos.turno}
                                        />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="matIngresso">Forma de ingresso *</label>
                                        <Dropdown
                                            id="matIngresso" name="formaIngresso" options={FORMAS_INGRESSO}
                                            value={formaIngresso} onChange={setFormaIngresso}
                                            placeholder="Selecione a forma de ingresso" invalid={camposInvalidos.formaIngresso}
                                        />
                                    </div>
                                </div>

                                <div className="field">
                                    <label htmlFor="matObservacoes">Observações</label>
                                    <textarea id="matObservacoes" name="observacoes" rows={4}
                                              placeholder="Conte se você precisa de algum atendimento específico."></textarea>
                                </div>

                                <label className="site-form-termos">
                                    <input id="matTermos" name="termos" type="checkbox" required />
                                    <span>Li e aceito os termos do processo seletivo e a política de privacidade. *</span>
                                </label>

                                <button id="btnEnviarMatricula" className="grad-btn grad-btn-primary" type="submit" disabled={enviando}>
                                    {enviando ? "Enviando..." : "Enviar inscrição"}
                                </button>

                                {mensagem && <p id="matMensagem" className="site-form-msg" aria-live="polite">{mensagem}</p>}
                                {erro && <p id="matErro" className="site-form-erro" aria-live="polite">{erro}</p>}
                            </form>
                        </div>
                    </section>
                </main>
            </div>
        </SiteChrome>
    );
}
