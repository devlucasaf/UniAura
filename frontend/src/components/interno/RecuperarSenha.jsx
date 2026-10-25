"use client";

import { useState }                     from "react";
import Link                             from "next/link";
import { useSearchParams }              from "next/navigation";
import { Button }                       from "primereact/button";
import { InputText }                    from "primereact/inputtext";
import CampoData                        from "@/components/web/CampoData";
import CampoSenha                       from "@/components/web/CampoSenha";
import { solicitarRecuperacaoDeSenha }  from "@/app/remotes/uniaura/autenticacao/solicitarRecuperacaoDeSenhaRemote";
import { redefinirSenha }               from "@/app/remotes/uniaura/autenticacao/redefinirSenhaRemote";
import { notificar }                    from "@/lib/notificar";

// --- APLICA A MÁSCARA 000.000.000-00 ---
const mascararCpf = (valor) => {
    const digitos = valor.replace(/\D/g, "").slice(0, 11);
    return digitos
        .replace(/^(\d{3})(\d)/, "$1.$2")
        .replace(/^(\d{3})\.(\d{3})(\d)/, "$1.$2.$3")
        .replace(/\.(\d{3})(\d)/, ".$1-$2");
};

// --- RECUPERAÇÃO DE SENHA SEM E-MAIL ---
export default function RecuperarSenha() {
    const parametros = useSearchParams();

    const [etapa,       setEtapa]       = useState(1);
    const [email,       setEmail]       = useState(parametros.get("email") ?? "");
    const [cpf,         setCpf]         = useState("");
    const [nascimento,  setNascimento]  = useState("");
    const [codigo,      setCodigo]      = useState("");
    const [novaSenha,   setNovaSenha]   = useState("");
    const [confirmar,   setConfirmar]   = useState("");
    const [pedido,      setPedido]      = useState(null);
    const [erros,       setErros]       = useState({});
    const [erro,        setErro]        = useState("");
    const [enviando,   setEnviando]     = useState(false);

    const limpar = (campo) => setErros((atual) => ({ ...atual, [campo]: undefined }));
    const erroDe = (nome) => erros[nome] && <small className="ua-erro-campo" role="alert">{erros[nome]}</small>;

    // --- PASSO 1: CONFERE A IDENTIDADE E GERA O CÓDIGO ---
    const solicitar = async (evento) => {
        evento.preventDefault();
        setErro("");

        const novos = {};
        if (!email.trim()) {
            novos.email = "Informe seu e-mail.";
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.trim())) {
            novos.email = "Informe um e-mail válido.";
        }

        if (cpf.replace(/\D/g, "").length !== 11) {
            novos.cpf = "Informe o CPF com 11 números.";
        }

        if (!nascimento) {
            novos.nascimento = "Informe a data de nascimento.";
        }

        setErros(novos);
        if (Object.keys(novos).length > 0) {
            return;
        }

        setEnviando(true);
        try {
            const resposta = await solicitarRecuperacaoDeSenha({ 
                    email: email.trim(), 
                    cpf, 
                    dataNascimento: nascimento 
                });
            setPedido(resposta);
            setCodigo("");
            setEtapa(2);
        } catch (excecao) {
            setErro(excecao?.message || "Não foi possível validar os dados informados.");
        } finally {
            setEnviando(false);
        }
    };

    // --- PASSO 2: O CÓDIGO AUTORIZA A TROCA DA SENHA ---
    const redefinir = async (evento) => {
        evento.preventDefault();
        setErro("");

        const novos = {};
        if (!/^\d{6}$/.test(codigo)) {
            novos.codigo = "Informe o código de 6 números.";
        }

        if (novaSenha.length < 6) {
            novos.novaSenha = "A nova senha deve ter pelo menos 6 caracteres.";
        }

        if (confirmar !== novaSenha) {
            novos.confirmar = "As senhas não conferem.";
        }

        setErros(novos);
        if (Object.keys(novos).length > 0) {
            return;
        }

        setEnviando(true);
        try {
            await redefinirSenha({ 
                    email: email.trim(), 
                    codigo, 
                    novaSenha 
                });
            notificar("Senha redefinida com sucesso.", "success");
            setEtapa(3);
        } catch (excecao) {
            setErro(excecao?.message || "Não foi possível redefinir a senha.");
        } finally {
            setEnviando(false);
        }
    };

    if (etapa === 3) {
        return (
            <div className="ua-recuperacao-sucesso">
                <i className="pi pi-check-circle" aria-hidden="true" />
                <p>Pronto! Sua senha foi alterada. Use a nova senha para entrar.</p>
                <Link href="/portal-do-aluno/login">
                    <Button
                        type="button"
                        label="Ir para o login"
                        icon="pi pi-sign-in"
                        size="large"
                        className="ua-botao-entrar"
                    />
                </Link>
            </div>
        );
    }

    if (etapa === 2) {
        return (
            <form className="ua-form-login" noValidate onSubmit={redefinir}>
                {/* --- CAIXA DE E-MAIL SIMULADA --- */}
                {pedido?.codigoSimulado && (
                    <div className="ua-email-simulado" role="status">
                        <span className="ua-email-simulado__origem">
                            <i className="pi pi-envelope" aria-hidden="true" /> E-mail simulado · para {email.trim()}
                        </span>
                        <p>Seu código de autorização para redefinir a senha é:</p>
                        <strong className="ua-email-simulado__codigo">{pedido.codigoSimulado}</strong>
                        <small>Válido por {pedido.validadeMinutos} minutos. Como os e-mails da UniAura são fictícios, nada foi enviado.</small>
                    </div>
                )}

                <div className="field">
                    <label htmlFor="recCodigo">
                        Código de autorização <span className="ua-obrigatorio" aria-hidden="true">*</span>
                    </label>
                    <InputText
                        id="recCodigo"
                        inputMode="numeric"
                        autoComplete="one-time-code"
                        maxLength={6}
                        value={codigo}
                        className={erros.codigo ? "p-invalid" : ""}
                        onChange={(evento) => { setCodigo(evento.target.value.replace(/\D/g, "")); limpar("codigo"); }}
                    />
                    {erroDe("codigo")}
                </div>

                <div className="field">
                    <label htmlFor="recNovaSenha">
                        Nova senha <span className="ua-obrigatorio" aria-hidden="true">*</span>
                    </label>
                    <CampoSenha
                        id="recNovaSenha"
                        name="novaSenha"
                        autoComplete="new-password"
                        value={novaSenha}
                        className={erros.novaSenha ? "p-invalid" : ""}
                        onChange={(evento) => { setNovaSenha(evento.target.value); limpar("novaSenha"); }}
                    />
                    {erroDe("novaSenha")}
                </div>

                <div className="field">
                    <label htmlFor="recConfirmar">
                        Confirmar nova senha <span className="ua-obrigatorio" aria-hidden="true">*</span>
                    </label>
                    <CampoSenha
                        id="recConfirmar"
                        name="confirmar"
                        autoComplete="new-password"
                        value={confirmar}
                        className={erros.confirmar ? "p-invalid" : ""}
                        onChange={(evento) => { setConfirmar(evento.target.value); limpar("confirmar"); }}
                    />
                    {erroDe("confirmar")}
                </div>

                {erro && <small className="ua-erro-campo ua-erro-login" role="alert">{erro}</small>}

                <Button
                    type="submit"
                    label={enviando ? "Salvando..." : "Redefinir senha"}
                    icon="pi pi-key"
                    size="large"
                    className="ua-botao-entrar"
                    loading={enviando}
                />
                <div className="ua-login-extras">
                    <Button
                        type="button"
                        label="Voltar"
                        icon="pi pi-arrow-left"
                        outlined
                        className="ua-login-extra"
                        onClick={() => { setEtapa(1); setErro(""); setErros({}); }}
                    />
                </div>
            </form>
        );
    }

    return (
        <form className="ua-form-login" noValidate onSubmit={solicitar}>
            <div className="field">
                <label htmlFor="recEmail">
                    E-mail <span className="ua-obrigatorio" aria-hidden="true">*</span>
                </label>
                <InputText
                    id="recEmail"
                    type="email"
                    autoComplete="username"
                    value={email}
                    className={erros.email ? "p-invalid" : ""}
                    onChange={(evento) => { setEmail(evento.target.value); limpar("email"); }}
                />
                {erroDe("email")}
            </div>

            <div className="field">
                <label htmlFor="recCpf">
                    CPF <span className="ua-obrigatorio" aria-hidden="true">*</span>
                </label>
                <InputText
                    id="recCpf"
                    inputMode="numeric"
                    placeholder="000.000.000-00"
                    value={cpf}
                    className={erros.cpf ? "p-invalid" : ""}
                    onChange={(evento) => { setCpf(mascararCpf(evento.target.value)); limpar("cpf"); }}
                />
                {erroDe("cpf")}
            </div>

            <div className="field">
                <label htmlFor="recNascimento">
                    Data de nascimento <span className="ua-obrigatorio" aria-hidden="true">*</span>
                </label>
                <CampoData
                    id="recNascimento"
                    name="nascimento"
                    required
                    invalido={Boolean(erros.nascimento)}
                    onChange={(valor) => { setNascimento(valor); limpar("nascimento"); }}
                />
                {erroDe("nascimento")}
            </div>

            {erro && <small className="ua-erro-campo ua-erro-login" role="alert">{erro}</small>}

            <Button
                type="submit"
                label={enviando ? "Verificando..." : "Gerar código"}
                icon="pi pi-shield"
                size="large"
                className="ua-botao-entrar"
                loading={enviando}
            />
        </form>
    );
}
