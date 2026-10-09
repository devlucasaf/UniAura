"use client";

import { useEffect, useState }                                          from "react";
import { useRouter }                                                    from "next/navigation";
import { Button }                                                       from "primereact/button";
import { InputText }                                                    from "primereact/inputtext";
import CampoSenha                                                       from "@/components/web/CampoSenha";
import { autenticar, dashboardDoPerfil, estaAutenticado, obterUsuario } from "@/lib/auth";

// --- FORMULÁRIO DE LOGIN COM E-MAIL E SENHA ---
export default function FormularioLogin() {
    const router = useRouter();
    const [email,    setEmail]    = useState("");
    const [senha,    setSenha]    = useState("");
    const [erros,    setErros]    = useState({});
    const [erro,     setErro]     = useState("");
    const [entrando, setEntrando] = useState(false);

    // --- QUEM JÁ ESTÁ AUTENTICADO VAI DIRETO PARA O PRÓPRIO PAINEL ---
    useEffect(() => {
        if (estaAutenticado()) {
            router.replace(dashboardDoPerfil(obterUsuario()?.role));
        }
    }, []);

    const erroDe = (nome) => erros[nome] && <small className="ua-erro-campo" role="alert">{erros[nome]}</small>;

    // --- VALIDA OS CAMPOS OBRIGATÓRIOS ANTES DE ENVIAR ---
    const validar = () => {
        const novos = {};
        if (!email.trim()) {
            novos.email = "Informe seu e-mail.";
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.trim())) {
            novos.email = "Informe um e-mail válido.";
        }

        if (!senha) {
            novos.senha = "Informe sua senha.";
        }
        return novos;
    };

    // --- ENVIA AS CREDENCIAIS AO BACKEND E, SE VÁLIDAS, ENCAMINHA PARA O PAINEL DO PERFIL ---
    const entrar = async (evento) => {
        evento.preventDefault();
        setErro("");

        const novos = validar();
        setErros(novos);
        if (Object.keys(novos).length > 0) {
            return;
        }

        setEntrando(true);
        try {
            const usuario = await autenticar(email.trim(), senha);
            router.replace(dashboardDoPerfil(usuario?.role));
        } catch (excecao) {
            setErro(excecao?.message || "Não foi possível entrar. Tente novamente.");
            setEntrando(false);
        }
    };

    return (
        <form className="ua-form-login" noValidate onSubmit={entrar}>
            <div className="field">
                <label htmlFor="loginEmail">
                    E-mail <span className="ua-obrigatorio" aria-hidden="true">*</span>
                </label>
                <InputText
                    id="loginEmail"
                    type="email"
                    autoComplete="username"
                    value={email}
                    className={erros.email ? "p-invalid" : ""}
                    onChange={(e) => { setEmail(e.target.value); setErros((atual) => ({ ...atual, email: undefined })); }}
                />
                {erroDe("email")}
            </div>

            <div className="field">
                <label htmlFor="loginSenha">
                    Senha <span className="ua-obrigatorio" aria-hidden="true">*</span>
                </label>
                <CampoSenha
                    id="loginSenha"
                    name="senha"
                    autoComplete="current-password"
                    value={senha}
                    className={erros.senha ? "p-invalid" : ""}
                    onChange={(e) => { setSenha(e.target.value); setErros((atual) => ({ ...atual, senha: undefined })); }}
                />
                {erroDe("senha")}
            </div>

            {erro && <small className="ua-erro-campo ua-erro-login" role="alert">{erro}</small>}

            <Button
                type="submit"
                label={entrando ? "Entrando..." : "Entrar"}
                icon="pi pi-sign-in"
                size="large"
                className="ua-botao-entrar"
                loading={entrando}
            />
        </form>
    );
}
