"use client";

import Link             from "next/link";
import SiteChrome       from "@/components/web/SiteChrome";
import FormularioLogin  from "@/components/interno/FormularioLogin";

// --- LOGIN DO PORTAL DO ALUNO ---
export default function PortalAlunoLoginPage() {
    return (
        <SiteChrome>
            <div className="grad-page">
                <main>
                    <section className="grad-section site-auth-section" id="grad-entrar">
                        <div className="grad-container site-auth-wrap">
                            <div className="site-auth-card">
                                <span className="grad-eyebrow">Portal do Aluno</span>
                                <h1>Acesse sua conta</h1>

                                <p className="muted">Informe seu e-mail e sua senha para acompanhar notas, frequência, biblioteca e muito mais.</p>

                                <FormularioLogin />

                                <p className="site-auth-rodape">
                                    Ainda não é aluno da UniAura?{" "}
                                    <Link className="site-link-destaque" href="/portal-do-aluno/cadastro">
                                        Crie sua matrícula
                                    </Link>
                                </p>
                                <p className="site-auth-rodape">
                                    <Link className="site-link-voltar" href="/web/home">
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
