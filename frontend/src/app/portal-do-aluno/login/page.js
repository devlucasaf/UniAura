"use client";

import { useEffect }    from "react";
import Link             from "next/link";
import SiteChrome       from "@/components/web/SiteChrome";
import { useAuth }      from "@bernardo-dias/react-cloudsupport";
import { LoggedOut }    from "@bernardo-dias/react-cloudsupport/prime";

// --- LOGIN DO PORTAL DO ALUNO: REDIRECIONA PARA O KEYCLOAK ---
export default function PortalAlunoLoginPage() {
    const auth = useAuth();

    // --- REDIRECIONA AUTOMATICAMENTE PARA A TELA DE LOGIN DO KEYCLOAK ---
    useEffect(() => {
        if (!auth.isLoading && !auth.isAuthenticated && !auth.activeNavigator) {
            auth.signinRedirect();
        }
    }, [auth.isLoading, auth.isAuthenticated, auth.activeNavigator]);

    return (
        <SiteChrome>
            <div className="grad-page">
                <main>
                    <section className="grad-section site-auth-section" id="grad-entrar">
                        <div className="grad-container site-auth-wrap">
                            <div className="site-auth-card">
                                <span className="grad-eyebrow">Portal do Aluno</span>
                                <h1>Acesse sua conta</h1>

                                <LoggedOut message="Sua sessão foi encerrada. Entre novamente para continuar." label="Entrar" />

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
