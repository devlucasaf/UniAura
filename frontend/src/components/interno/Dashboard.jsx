"use client";

import { Card }          from "primereact/card";
import { obterUsuario }  from "@/lib/auth";

// --- PAINEL INICIAL DE UM PERFIL ---
export default function Dashboard({ titulo }) {
    const usuario = obterUsuario();

    return (
        <Card title={titulo}>
            <p className="muted">Bem-vindo, {usuario?.nome || "usuário"}!</p>
            <p style={{ marginTop: "1rem" }}>
                Este é um painel inicial. O conteúdo específico deste perfil será adicionado aqui nas próximas etapas.
            </p>
        </Card>
    );
}
