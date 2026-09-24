"use client";

import { obterUsuario } from "@/lib/auth";

// --- PAINEL INICIAL (PLACEHOLDER) DE UM PERFIL ---
export default function Dashboard({ titulo }) {
    const usuario = obterUsuario();

    return (
        <section className="card">
            <h1>{titulo}</h1>
            <p className="muted">Bem-vindo, {usuario?.nome || "usuário"}!</p>
            <p style={{ marginTop: "1rem" }}>
                Este é um painel inicial. O conteúdo específico deste perfil será adicionado aqui nas próximas etapas.
            </p>
        </section>
    );
}
