"use client";

import Link             from "next/link";
import FormularioLogin from "./FormularioLogin";

// --- TELA DE LOGIN DE UMA ÁREA INTERNA ---
export default function TelaLoginPerfil({ area }) {
    return (
        <div className="auth-screen">
            <div className="auth-card">
                <h1>{area}</h1>
                <p className="subtitle">Informe seu e-mail e sua senha para acessar esta área.</p>

                <FormularioLogin />

                <p className="auth-back">
                    <Link href="/">← Voltar à central de acesso</Link>
                </p>
            </div>
        </div>
    );
}
