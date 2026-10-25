"use client";

import { Suspense }      from "react";
import Link              from "next/link";
import SiteChrome        from "@/components/web/SiteChrome";
import RecuperarSenha    from "@/components/interno/RecuperarSenha";

// --- PÁGINA DE RECUPERAÇÃO DE SENHA ---
export default function RecuperarSenhaPage() {
    return (
        <SiteChrome>
            <div className="grad-page">
                <main>
                    <section className="grad-section site-auth-section" id="grad-recuperar">
                        <div className="grad-container site-auth-wrap">
                            <div className="site-auth-card">
                                <span className="grad-eyebrow">Recuperar acesso</span>
                                <h1>Esqueci a senha</h1>

                                <p className="muted">
                                    Confirme seus dados de cadastro. Como os e-mails são fictícios, o código de
                                    autorização aparece aqui na tela, sem envio de mensagem.
                                </p>

                                <Suspense fallback={null}>
                                    <RecuperarSenha />
                                </Suspense>

                                <p className="site-auth-rodape">
                                    <Link className="site-link-voltar" href="/portal-do-aluno/login">
                                        <span className="site-seta-voltar" aria-hidden="true">←</span> Voltar ao login
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
