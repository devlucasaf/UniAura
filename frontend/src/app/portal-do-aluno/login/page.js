"use client";

import { useState } from "react";
import Link from "next/link";
import SiteChrome from "@/components/site/SiteChrome";
import { autenticar, dashboardDoPerfil } from "@/lib/auth";
import { notificar } from "@/lib/notificar";

export default function PortalAlunoLoginPage() {
    const [mensagem, setMensagem] = useState("");
    const [carregando, setCarregando] = useState(false);

    const aoEnviar = async (evento) => {
        evento.preventDefault();
        setMensagem("");

        const formulario = evento.target;
        const email = formulario.email.value.trim();
        const senha = formulario.senha.value;

        if (!email || !senha) {
            setMensagem("Informe e-mail e senha.");
            return;
        }

        setCarregando(true);
        try {
            const usuario = await autenticar(email, senha);
            notificar(`Bem-vindo, ${usuario.nome}!`, "success");
            window.location.href = dashboardDoPerfil(usuario.role);
        } catch (erro) {
            setMensagem(erro.message);
            notificar(erro.message, "error");
        } finally {
            setCarregando(false);
        }
    };

    return (
        <SiteChrome>
            <div className="grad-page">
                <main>
                    <section className="grad-section site-auth-section" id="grad-entrar">
                        <div className="grad-container site-auth-wrap">
                            <div className="site-auth-card">
                                <span className="grad-eyebrow">Portal do Aluno</span>
                                <h1>Acesse sua conta</h1>
                                <p className="muted">
                                    Entre com o e-mail e a senha cadastrados na sua matrícula para acompanhar notas,
                                    frequência, financeiro e a biblioteca.
                                </p>

                                <form id="formPortalLogin" className="site-form" noValidate onSubmit={aoEnviar}>
                                    <div className="field">
                                        <label htmlFor="palEmail">E-mail *</label>
                                        <input
                                            id="palEmail"
                                            name="email"
                                            type="email"
                                            required
                                            autoComplete="email"
                                            autoFocus
                                        />
                                    </div>

                                    <div className="field">
                                        <label htmlFor="palSenha">Senha *</label>
                                        <input
                                            id="palSenha"
                                            name="senha"
                                            type="password"
                                            required
                                            autoComplete="current-password"
                                        />
                                    </div>

                                    {mensagem && (
                                        <p id="palMensagem" className="site-form-erro" aria-live="polite">{mensagem}</p>
                                    )}

                                    <button id="btnPortalEntrar" className="grad-btn grad-btn-primary site-auth-btn" type="submit" disabled={carregando}>
                                        {carregando ? "Entrando..." : "Entrar"}
                                    </button>
                                </form>

                                <p className="site-auth-rodape">
                                    Ainda não é aluno da UniAura?{" "}
                                    <Link className="site-link-destaque" href="/portal-do-aluno/cadastro">
                                        Crie sua matrícula
                                    </Link>
                                </p>
                                <p className="site-auth-rodape">
                                    <Link className="site-link-voltar" href="/">
                                        <span className="site-seta-voltar" aria-hidden="true">←</span> Voltar ao site
                                    </Link>
                                </p>
                            </div>
                        </div>
                    </section>
                </main>
            </div>
        </SiteChrome>
    );
}
