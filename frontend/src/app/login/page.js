"use client";

import { useEffect }    from "react";
import Link             from "next/link";
import { useAuth }      from "@bernardo-dias/react-cloudsupport";
import { LoggedOut }    from "@bernardo-dias/react-cloudsupport/prime";

// --- LOGIN INTERNO DO SISTEMA: REDIRECIONA PARA O KEYCLOAK (USADO POR TODOS OS PERFIS) ---
export default function LoginPage() {
    const auth = useAuth();

    // --- REDIRECIONA AUTOMATICAMENTE PARA A TELA DE LOGIN DO KEYCLOAK ---
    useEffect(() => {
        if (!auth.isLoading && !auth.isAuthenticated && !auth.activeNavigator) {
            auth.signinRedirect();
        }
    }, [auth.isLoading, auth.isAuthenticated, auth.activeNavigator]);

    return (
        <div className="auth-screen">
            <div className="auth-card">
                <h1>ERP Acadêmico</h1>
                <p className="subtitle">Sistema de Gestão Escolar</p>

                <LoggedOut message="Sua sessão foi encerrada. Entre novamente para continuar." label="Entrar" />

                <p className="auth-back">
                    <Link href="/">← Voltar ao site</Link>
                </p>
            </div>
        </div>
    );
}
