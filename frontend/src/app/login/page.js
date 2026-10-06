"use client";

import TelaLoginPerfil from "@/components/interno/TelaLoginPerfil";

// --- LOGIN GENÉRICO DO SISTEMA (USADO QUANDO NÃO SE SABE O PERFIL, EX.: SESSÃO EXPIRADA) ---
export default function LoginPage() {
    return <TelaLoginPerfil area="ERP Acadêmico" />;
}
