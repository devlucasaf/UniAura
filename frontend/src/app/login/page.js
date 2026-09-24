"use client";

import { useState } from "react";
import Link from "next/link";
import { autenticar, dashboardDoPerfil } from "@/lib/auth";
import { notificar } from "@/lib/notificar";

// --- LOGIN INTERNO DO SISTEMA: USADO POR TODOS OS PERFIS (SECRETARIA, PROFESSOR, ADMIN ETC.) ---
export default function LoginPage() {
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
        <div className="auth-screen">
            <div className="auth-card">
                <h1>ERP Acadêmico</h1>
                <p className="subtitle">Sistema de Gestão Escolar</p>

                <form id="formularioLogin" noValidate onSubmit={aoEnviar}>
                    <div className="field">
                        <label htmlFor="email">E-mail</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="voce@faculdadeaura.com"
                            required
                            autoFocus
                            autoComplete="email"
                        />
                    </div>

                    <div className="field">
                        <label htmlFor="senha">Senha</label>
                        <input
                            type="password"
                            id="senha"
                            name="senha"
                            placeholder="••••••••"
                            required
                            autoComplete="current-password"
                        />
                    </div>

                    {mensagem && <p className="msg-error">{mensagem}</p>}

                    <button type="submit" className="btn btn-block" id="btnEntrar" disabled={carregando}>
                        {carregando ? "Entrando..." : "Entrar"}
                    </button>
                </form>

                <p className="auth-back">
                    <Link href="/">← Voltar ao site</Link>
                </p>
            </div>
        </div>
    );
}
