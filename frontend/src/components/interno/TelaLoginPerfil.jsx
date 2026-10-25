"use client";

import Link             from "next/link";
import FormularioLogin from "./FormularioLogin";
import SiteChrome      from "@/components/web/SiteChrome";

// --- TELA DE LOGIN DE UMA ÁREA INTERNA, COM O CABEÇALHO E O RODAPÉ DO SITE ---
export default function TelaLoginPerfil({ area, cadastroHref }) {
    return (
        <SiteChrome>
            <div className="auth-screen">
                <div className="auth-card">
                    <h1>{area}</h1>
                    <p className="subtitle">Informe seu e-mail e sua senha para acessar esta área.</p>

                    <FormularioLogin cadastroHref={cadastroHref} />

                    <p className="auth-back">
                        <Link href="/">
                            <span className="auth-back-seta" aria-hidden="true">←</span> Voltar à central de acesso
                        </Link>
                    </p>
                </div>
            </div>
        </SiteChrome>
    );
}
